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
package com.borkdominik.big.glsp.server.core.features.tool_palette;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.glsp.server.features.toolpalette.PaletteItem;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;

import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.google.inject.Inject;

public class BGToolPaletteItemProvider
   implements ToolPaletteItemProvider {

   private static Logger LOGGER = LogManager.getLogger(BGToolPaletteItemProvider.class.getSimpleName());

   @Inject
   protected Map<Enumerator, Set<BGToolPaletteProvider>> palettes;
   @Inject
   protected BGModelRepresentation modelStateRepresentation;

   @Override
   public List<PaletteItem> getItems(final Map<String, String> args) {
      return modelStateRepresentation.get().map(representation -> {
         var items = new ArrayList<PaletteItem>();

         if (palettes.containsKey(representation)) {
            palettes.get(representation).stream()
               .forEachOrdered(contribution -> {
                  items.addAll(contribution.getItems(args));
               });
         } else {
            throw new IllegalArgumentException(
               String.format("Enumerator %s did not provide any tool palette items", representation));
         }

         return items;
      }).orElseGet(() -> {
         LOGGER.warn("Could not read representation from modelstate.");
         return new ArrayList<>();
      });
   }
}
