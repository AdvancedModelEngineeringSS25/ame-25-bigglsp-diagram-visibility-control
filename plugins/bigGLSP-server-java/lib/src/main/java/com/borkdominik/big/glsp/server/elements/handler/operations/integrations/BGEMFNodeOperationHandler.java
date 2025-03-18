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
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.utils.LayoutUtil;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;
import com.borkdominik.big.glsp.server.core.commands.emf.notation.BGEMFCreateShapeNotationCommand;
import com.borkdominik.big.glsp.server.core.commands.semantic.BGCreateSemanticCommand;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.handler.operations.BGNodeOperationHandler;
import com.google.inject.Inject;

public abstract class BGEMFNodeOperationHandler<TElement extends EObject, TParent extends EObject>
   extends BGNodeOperationHandler<TElement, TParent> {

   @Inject
   protected BGEMFModelElementIndex elementIndex;
   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected BGEMFCommandContext commandContext;

   public BGEMFNodeOperationHandler(final Enumerator representation, final Set<BGTypeProvider> elementTypes) {
      super(representation, elementTypes);
   }

   public BGEMFNodeOperationHandler(final Enumerator representation, final BGTypeProvider elementType) {
      super(representation, elementType);
   }

   @Override
   protected Optional<Command> doHandleCreateNode(final CreateNodeOperation operation, final TParent parent) {
      var semanticCommand = this.createSemanticCommand(operation, parent);
      var notationCommand = this.createNotationCommand(operation, parent, semanticCommand);

      var compoundCommand = new CompoundCommand();
      compoundCommand.append(semanticCommand);
      compoundCommand.append(notationCommand);
      return Optional.of(compoundCommand);
   }

   protected abstract BGCreateSemanticCommand<TElement> createSemanticCommand(
      final CreateNodeOperation operation, final TParent parent);

   protected BGEMFCreateShapeNotationCommand createNotationCommand(final CreateNodeOperation operation,
      final TParent parent,
      final BGCreateSemanticCommand<TElement> semanticCommand) {
      var container = this.elementIndex.getGModel(operation.getContainerId()).orElseGet(modelState::getRoot);

      var absoluteLocation = operation.getLocation();
      var relativeLocation = absoluteLocation
         .map(location -> LayoutUtil.getRelativeLocation(location, container));

      var notationOptions = new BGEMFCreateShapeNotationCommand.Options(idGenerator, semanticCommand::getElement,
         Optional.empty(), relativeLocation);

      return new BGEMFCreateShapeNotationCommand(commandContext, notationOptions);
   }

}
