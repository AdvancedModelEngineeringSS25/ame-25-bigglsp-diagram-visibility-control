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
package com.borkdominik.big.glsp.server.core.handler.operation.create;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.operations.CreateNodeOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.BGBasicOperationHandler;
import com.google.inject.Inject;

public class BGCreateNodeOperationHandler extends BGBasicOperationHandler<CreateNodeOperation> {
   @Inject
   protected BGCreateNodeHandlerRegistry registry;

   @Override
   public Optional<Command> createCommand(final CreateNodeOperation operation) {
      var representation = modelStateRepresentation.getUnsafe();
      var handler = registry.retrieve(representation, operation.getElementTypeId());

      return handler.handleCreateNode(operation);
   }
}
