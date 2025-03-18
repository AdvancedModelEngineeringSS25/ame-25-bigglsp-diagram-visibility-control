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
package com.borkdominik.big.glsp.server.elements.handler.operations.integrations;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;
import com.borkdominik.big.glsp.server.core.commands.emf.notation.BGEMFCreateEdgeNotationCommand;
import com.borkdominik.big.glsp.server.core.commands.semantic.BGCreateSemanticCommand;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.handler.operations.BGEdgeOperationHandler;
import com.google.inject.Inject;

public abstract class BGEMFEdgeOperationHandler<TElement extends EObject, TSource extends EObject, TTarget extends EObject>
   extends BGEdgeOperationHandler<TElement, TSource, TTarget> {

   @Inject
   protected BGEMFModelElementIndex elementIndex;
   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected BGEMFCommandContext commandContext;

   public BGEMFEdgeOperationHandler(final Enumerator representation, final Set<BGTypeProvider> elementTypes) {
      super(representation, elementTypes);
   }

   public BGEMFEdgeOperationHandler(final Enumerator representation, final BGTypeProvider elementType) {
      super(representation, elementType);
   }

   @Override
   protected Optional<Command> doHandleCreateEdge(final CreateEdgeOperation operation, final TSource source,
      final TTarget target) {
      var semanticCommand = this.createSemanticCommand(operation, source, target);
      var notationCommand = this.createNotationCommand(operation, source, target, semanticCommand);

      var compoundCommand = new CompoundCommand();
      compoundCommand.append(semanticCommand);
      compoundCommand.append(notationCommand);
      return Optional.of(compoundCommand);
   }

   protected abstract BGCreateSemanticCommand<TElement> createSemanticCommand(
      final CreateEdgeOperation operation, final TSource source, final TTarget target);

   protected BGEMFCreateEdgeNotationCommand createNotationCommand(final CreateEdgeOperation operation,
      final TSource source, final TTarget target,
      final BGCreateSemanticCommand<TElement> semanticCommand) {

      var notationOptions = new BGEMFCreateEdgeNotationCommand.Options(idGenerator, semanticCommand::getElement);

      return new BGEMFCreateEdgeNotationCommand(commandContext, notationOptions);
   }
}
