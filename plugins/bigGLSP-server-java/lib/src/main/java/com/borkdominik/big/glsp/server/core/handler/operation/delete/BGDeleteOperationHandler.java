/********************************************************************************
 * Copyright (c) 2021 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.handler.operation.delete;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.glsp.server.operations.DeleteOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.BGBasicOperationHandler;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.google.inject.Inject;

public class BGDeleteOperationHandler extends BGBasicOperationHandler<DeleteOperation> {
   private static Logger LOGGER = LogManager.getLogger(BGDeleteOperationHandler.class.getSimpleName());

   @Inject
   protected BGDeleteHandlerRegistry registry;
   @Inject
   protected BGModelState modelState;

   @Override
   public Optional<Command> createCommand(final DeleteOperation operation) {
      var representation = modelState.representation().getUnsafe();

      var command = new CompoundCommand();
      operation.getElementIds().forEach(elementId -> {
         var semanticElement = modelState.getElementIndex().getOrThrow(elementId);

         registry.retrieveOrDefault(representation, semanticElement.getClass())
            .handleDelete(operation, semanticElement)
            .ifPresent(command::append);
      });

      return Optional.of(command);
   }
}
