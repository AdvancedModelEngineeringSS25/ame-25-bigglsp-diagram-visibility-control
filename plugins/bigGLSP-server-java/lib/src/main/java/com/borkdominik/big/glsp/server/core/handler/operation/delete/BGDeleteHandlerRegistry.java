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
package com.borkdominik.big.glsp.server.core.handler.operation.delete;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.borkdominik.big.glsp.server.lib.registry.BGTypeRegistry;
import com.google.inject.Inject;

public class BGDeleteHandlerRegistry
   extends BGTypeRegistry<BGDeleteHandler> {

   @Inject
   public BGDeleteHandlerRegistry(
      final Map<Enumerator, BGDeleteHandler> defaultHandlers,
      final Map<Enumerator, Set<BGDeleteHandler>> handlers) {
      defaultHandlers.entrySet().forEach(e -> {
         var value = e.getValue();
         e.getValue().getHandledElementTypes().forEach(type -> {
            defaultValues.put(BGRegistryKey.of(e.getKey(), type), value);
         });
      });

      handlers.entrySet().forEach(e -> {
         var representation = e.getKey();

         e.getValue().forEach(handler -> {
            handler.getHandledElementTypes().forEach(type -> {
               register(BGRegistryKey.of(representation, type), handler);
            });
         });
      });

      // printContent();
   }
}
