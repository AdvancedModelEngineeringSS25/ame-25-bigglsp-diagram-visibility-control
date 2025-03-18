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

import com.borkdominik.big.glsp.server.core.commands.BGCommandContext;

public abstract class BGUpdateElementSemanticCommand<TElement extends EObject, TArgument>
   extends BGSemanticCommand {

   protected TElement element;
   protected TArgument argument;

   public BGUpdateElementSemanticCommand(final BGCommandContext context, final EObject root, final TElement element,
      final TArgument argument) {
      super(context, root);

      this.element = element;
      this.argument = argument;
   }

   @Override
   protected void doExecute() {
      updateElement(element, argument);
   }

   protected abstract void updateElement(final TElement element, TArgument argument);
}
