/********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.elements.handler.operations;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.operations.CreateNodeOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateNodeHandler;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public abstract class BGNodeOperationHandler<TElement extends EObject, TParent extends EObject>
      extends BGElementOperationHandler implements
      BGCreateNodeHandler {

   protected final Gson gson;

   @Inject
   protected TypeLiteral<TElement> elementType;
   @Inject
   protected TypeLiteral<TParent> parentType;

   public BGNodeOperationHandler(final Enumerator representation, final BGTypeProvider elementType) {
      this(representation, Set.of(elementType));
   }

   public BGNodeOperationHandler(final Enumerator representation, final Set<BGTypeProvider> elementTypes) {
      super(representation, elementTypes);
      this.gson = new Gson();
   }

   @Override
   public Optional<Command> handleCreateNode(final CreateNodeOperation operation) {
      var containerId = operation.getContainerId();
      if (containerId == null) {
         containerId = this.modelState.getIndex().getRoot().getId();
      }
      var container = modelState.getElementIndex().getOrThrow(containerId, TypeLiteralUtils.of(parentType));

      return doHandleCreateNode(operation, container);
   }

   protected abstract Optional<Command> doHandleCreateNode(final CreateNodeOperation operation, final TParent parent);
}
