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
package com.borkdominik.big.glsp.server.core.commands.semantic;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.borkdominik.big.glsp.server.core.commands.BGCommandContext;

public class BGDeleteElementSemanticCommand
   extends BGSemanticCommand {

   protected EObject element;

   public BGDeleteElementSemanticCommand(final BGCommandContext context, final EObject root, final EObject element) {
      super(context, root);

      this.element = element;
   }

   @Override
   protected void doExecute() {
      EcoreUtil.delete(element, true);
   }
}
