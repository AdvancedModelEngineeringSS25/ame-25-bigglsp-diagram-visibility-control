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
package com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.implementations;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.gmodel.GModelReconnectEdgeOperationHandler;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeHandler;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.core.model.BGTypeProviderAll;
import com.google.inject.Inject;

public class BGGModelDefaultReconnectEdgeHandler implements BGReconnectEdgeHandler {

   @Inject
   protected GModelReconnectEdgeOperationHandler reconnectHandler;

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return Set.of(BGTypeProviderAll.instance); }

   @Override
   public Optional<Command> handleReconnect(final ReconnectEdgeOperation operation, final EObject object) {
      return reconnectHandler.execute(operation);
   }
}
