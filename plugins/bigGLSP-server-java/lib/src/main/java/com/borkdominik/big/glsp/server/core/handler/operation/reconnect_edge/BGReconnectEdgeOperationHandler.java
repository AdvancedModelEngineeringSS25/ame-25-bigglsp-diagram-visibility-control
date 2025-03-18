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
package com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.BGBasicOperationHandler;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.google.inject.Inject;

public class BGReconnectEdgeOperationHandler extends BGBasicOperationHandler<ReconnectEdgeOperation> {
   private static Logger LOGGER = LogManager.getLogger(BGReconnectEdgeOperationHandler.class.getSimpleName());

   @Inject
   protected BGReconnectEdgeHandlerRegistry registry;
   @Inject
   protected BGModelState modelState;

   @Override
   public Optional<Command> createCommand(final ReconnectEdgeOperation operation) {
      if (operation.getEdgeElementId() == null || operation.getSourceElementId() == null
         || operation.getTargetElementId() == null) {
         throw new IllegalArgumentException("Incomplete reconnect connection action");
      }

      var modelId = modelState.getRoot().getId();
      if (operation.getSourceElementId().equals(modelId) || operation.getTargetElementId().equals(modelId)) {
         // client tool failure, do nothing
         LOGGER.debug("SourceId or targetId was equal to modelId");
         return Optional.empty();
      }

      var representation = modelState.representation().getUnsafe();
      var elementId = operation.getEdgeElementId();
      var semanticElement = modelState.getElementIndex().getOrThrow(elementId);

      return registry.retrieveOrDefault(representation, semanticElement.getClass())
         .handleReconnect(operation, semanticElement);
   }

}
