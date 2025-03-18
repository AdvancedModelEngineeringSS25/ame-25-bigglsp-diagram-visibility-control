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

import org.eclipse.emf.ecore.EObject;

public class BGRecordingRunnableCommand extends BGRecordingCommand {

   protected Runnable runnable;

   public BGRecordingRunnableCommand(final EObject root, final Runnable run) {
      super(root);
      this.runnable = run;
   }

   @Override
   protected void doExecute() {
      runnable.run();
   }

}
