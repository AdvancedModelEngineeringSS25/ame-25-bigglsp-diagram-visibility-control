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

public abstract class BGCreateEdgeSemanticCommand<TElement extends EObject, TSource extends EObject, TTarget extends EObject, TArgument>
   extends BGCreateSemanticCommand<TElement> {

   protected TSource source;
   protected TTarget target;
   protected TArgument argument;

   public BGCreateEdgeSemanticCommand(final BGCommandContext context, final EObject root, final TSource source,
      final TTarget target,
      final TArgument argument) {
      super(context, root);
      this.source = source;
      this.target = target;
      this.argument = argument;
   }

   @Override
   protected void doExecute() {
      this.element = createElement(source, target, argument);
   }

   protected abstract TElement createElement(final TSource source, TTarget target, TArgument argument);
}
