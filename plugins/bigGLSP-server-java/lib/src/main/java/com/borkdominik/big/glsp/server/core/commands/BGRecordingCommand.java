/********************************************************************************
 * Copyright (c) 2024 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.commands;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;

public abstract class BGRecordingCommand extends AbstractCommand {

   protected final EObject root;
   protected ChangeDescription change;

   public BGRecordingCommand(final EObject root) {
      this.root = root;
   }

   @Override
   protected boolean prepare() {
      return change == null;
   }

   @Override
   public void execute() {
      var recorder = new ChangeRecorder(root);
      try {
         doExecute();
      } finally {
         change = recorder.endRecording();
         recorder.dispose();
      }
   }

   protected abstract void doExecute();

   @Override
   public boolean canUndo() {
      return change != null;
   }

   @Override
   public void undo() {
      applyChanges();
   }

   @Override
   public void redo() {
      applyChanges();
   }

   protected void applyChanges() {
      var recorder = new ChangeRecorder(root);
      try {
         change.apply();
      } finally {
         change = recorder.endRecording();
         recorder.dispose();
      }
   }

}
