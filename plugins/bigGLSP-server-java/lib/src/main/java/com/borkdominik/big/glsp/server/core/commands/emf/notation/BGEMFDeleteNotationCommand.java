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
package com.borkdominik.big.glsp.server.core.commands.emf.notation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;

public class BGEMFDeleteNotationCommand extends BGEMFNotationCommand {

   protected EObject element;

   public BGEMFDeleteNotationCommand(final BGEMFCommandContext context, final EObject element) {
      super(context);
      this.element = element;
   }

   @Override
   protected void doExecute() {
      var notation = this.context.modelState().getElementIndex().getNotation(element);
      notation.ifPresent(n -> EcoreUtil.delete(n, true));
   }
}
