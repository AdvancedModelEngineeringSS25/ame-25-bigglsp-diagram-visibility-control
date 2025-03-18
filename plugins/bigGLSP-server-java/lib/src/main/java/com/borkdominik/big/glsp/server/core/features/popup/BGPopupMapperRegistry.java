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
package com.borkdominik.big.glsp.server.core.features.popup;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

import com.borkdominik.big.glsp.server.lib.registry.BGClassRegistry;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.google.inject.Inject;

public class BGPopupMapperRegistry
   extends BGClassRegistry<Class<? extends EObject>, BGPopupMapper<EObject>> {

   @Inject
   public BGPopupMapperRegistry(
      final Map<Enumerator, Set<BGPopupMapper<? extends EObject>>> handlers) {
      handlers.entrySet().forEach(e -> {
         var representation = e.getKey();

         e.getValue().forEach(handler -> {
            register(BGRegistryKey.of(representation, handler.getElementType()), (BGPopupMapper<EObject>) handler);
         });
      });

      // printContent();
   }
}
