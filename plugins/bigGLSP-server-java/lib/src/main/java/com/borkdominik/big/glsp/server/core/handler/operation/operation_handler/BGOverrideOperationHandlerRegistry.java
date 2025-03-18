/********************************************************************************
 * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.handler.operation.operation_handler;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.borkdominik.big.glsp.server.lib.registry.BGClassRegistry;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.google.inject.Inject;

public class BGOverrideOperationHandlerRegistry
   extends BGClassRegistry<Class<? extends Operation>, OperationHandler<?>> {

   @Inject
   public BGOverrideOperationHandlerRegistry(
      final Map<Enumerator, Set<OperationHandler<?>>> handlers) {
      handlers.entrySet().forEach(e -> {
         var representation = e.getKey();

         e.getValue().forEach(handler -> {
            register(BGRegistryKey.of(representation, handler.getHandledOperationType()), handler);
         });
      });

      // printContent();
   }
}
