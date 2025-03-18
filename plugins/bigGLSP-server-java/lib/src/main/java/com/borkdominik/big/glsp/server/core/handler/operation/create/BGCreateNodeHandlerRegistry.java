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
package com.borkdominik.big.glsp.server.core.handler.operation.create;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.borkdominik.big.glsp.server.lib.registry.BGTypeRegistry;
import com.google.inject.Inject;

public class BGCreateNodeHandlerRegistry
   extends BGTypeRegistry<BGCreateNodeHandler> {

   @Inject
   protected BGModelState modelState;

   @Inject
   public BGCreateNodeHandlerRegistry(final Map<Enumerator, Set<BGCreateNodeHandler>> handlers) {
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
