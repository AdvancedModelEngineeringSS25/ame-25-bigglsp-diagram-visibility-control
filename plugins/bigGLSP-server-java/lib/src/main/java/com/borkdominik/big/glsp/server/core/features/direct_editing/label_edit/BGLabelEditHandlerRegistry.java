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
package com.borkdominik.big.glsp.server.core.features.direct_editing.label_edit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.borkdominik.big.glsp.server.lib.registry.BGTypeRegistry;
import com.google.inject.Inject;

public class BGLabelEditHandlerRegistry
   extends BGTypeRegistry<Set<BGLabelEditHandler>> {

   @Inject
   public BGLabelEditHandlerRegistry(
      final Map<Enumerator, Set<BGLabelEditHandler>> mappers) {
      mappers.entrySet().forEach(e -> {
         var representation = e.getKey();
         var handlers = e.getValue();

         var map = new HashMap<BGTypeProvider, Set<BGLabelEditHandler>>();
         for (var handler : handlers) {
            for (var key : handler.getHandledElementTypes()) {
               map.putIfAbsent(key, new HashSet<>());
               map.get(key).add(handler);
            }
         }

         map.forEach((key, values) -> {
            register(BGRegistryKey.of(representation, key), values);
         });
      });
   }
}
