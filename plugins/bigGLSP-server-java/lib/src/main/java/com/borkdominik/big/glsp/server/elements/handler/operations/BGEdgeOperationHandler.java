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
import org.eclipse.glsp.server.operations.CreateEdgeOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateEdgeHandler;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public abstract class BGEdgeOperationHandler<TElement extends EObject, TSource extends EObject, TTarget extends EObject>
   extends BGElementOperationHandler implements BGCreateEdgeHandler {

   @Inject
   protected TypeLiteral<TElement> elementType;
   @Inject
   protected TypeLiteral<TSource> sourceType;
   @Inject
   protected TypeLiteral<TTarget> targetType;

   protected final Gson gson;

   public BGEdgeOperationHandler(final Enumerator representation, final BGTypeProvider elementType) {
      this(representation, Set.of(elementType));
   }

   public BGEdgeOperationHandler(final Enumerator representation, final Set<BGTypeProvider> elementTypes) {
      super(representation, elementTypes);
      this.gson = new Gson();
   }

   @Override
   public Optional<Command> handleCreateEdge(final CreateEdgeOperation operation) {
      var sourceId = operation.getSourceElementId();
      var targetId = operation.getTargetElementId();

      var source = modelState.getElementIndex().getOrThrow(sourceId, TypeLiteralUtils.of(sourceType));
      var target = modelState.getElementIndex().getOrThrow(targetId, TypeLiteralUtils.of(targetType));

      return doHandleCreateEdge(operation, source, target);
   }

   protected abstract Optional<Command> doHandleCreateEdge(final CreateEdgeOperation operation, final TSource source,
      final TTarget target);

}
