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
package com.borkdominik.big.glsp.server.core.handler.operation.change_routing_points;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;

import com.borkdominik.big.glsp.server.core.commands.BGRecordingRunnableCommand;
import com.borkdominik.big.glsp.server.core.handler.operation.BGEMFOperationHandler;

public class BGEMFChangeRoutingPointsOperationHandler extends BGEMFOperationHandler<ChangeRoutingPointsOperation> {

   @Override
   public Optional<Command> createCommand(final ChangeRoutingPointsOperation operation) {
      return Optional.of(new BGRecordingRunnableCommand(modelState.getNotationModel(),
         () -> this.executeChangeRoutingPoints(operation)));
   }

   protected void executeChangeRoutingPoints(final ChangeRoutingPointsOperation operation) {
      if (operation.getNewRoutingPoints() == null) {
         throw new IllegalArgumentException("Incomplete change routingPoints  action");
      }

      operation.getNewRoutingPoints().forEach(routingPoints -> {
         var element = this.modelState.getElementIndex().getNotationOrThrow(routingPoints.getElementId(), Edge.class);
         element.getBendPoints().clear();
         element.getBendPoints().addAll(routingPoints.getNewRoutingPoints());
      });
   }
}
