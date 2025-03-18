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
package com.borkdominik.big.glsp.server.core.gmodel;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;

import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.borkdominik.big.glsp.server.lib.registry.BGTypeRegistry;
import com.google.inject.Inject;

public class BGEMFGModelMapperRegistry
   extends BGTypeRegistry<BGEMFGModelMapper<EObject, GModelElement>> {

   @Inject
   public BGEMFGModelMapperRegistry(
      final Map<Enumerator, Set<BGEMFGModelMapper<? extends EObject, ? extends GModelElement>>> handlers) {
      handlers.entrySet().forEach(e -> {
         var representation = e.getKey();

         e.getValue().forEach(mapper -> {
            var keys = mapper.getHandledElementTypes();

            keys.forEach(key -> {
               register(BGRegistryKey.of(representation, key), (BGEMFGModelMapper<EObject, GModelElement>) mapper);
            });
         });
      });

      // printContent();
   }
}
