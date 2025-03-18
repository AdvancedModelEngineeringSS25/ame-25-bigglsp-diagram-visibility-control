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
package com.borkdominik.big.glsp.server.features.outline.handler;

import java.util.Map;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.features.outline.generator.BGOutlineGenerator;
import com.borkdominik.big.glsp.server.lib.registry.BGBaseRegistry;
import com.google.inject.Inject;

public class BGOutlineGeneratorRegistry
   extends BGBaseRegistry<Enumerator, BGOutlineGenerator> {

   @Inject
   public BGOutlineGeneratorRegistry(
      final Map<Enumerator, BGOutlineGenerator> handlers) {
      handlers.entrySet().forEach(e -> {
         var representation = e.getKey();
         var generator = e.getValue();

         register(representation, generator);
      });

      // printContent();
   }

   @Override
   protected String deriveKey(final Enumerator key) {
      return key.getName();
   }
}
