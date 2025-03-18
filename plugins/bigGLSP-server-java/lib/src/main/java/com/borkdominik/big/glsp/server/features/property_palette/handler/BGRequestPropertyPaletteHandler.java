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
package com.borkdominik.big.glsp.server.features.property_palette.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;

import com.borkdominik.big.glsp.server.core.handler.action.BGActionHandler;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyPaletteProviderRegistry;
import com.google.inject.Inject;

public class BGRequestPropertyPaletteHandler extends BGActionHandler<BGRequestPropertyPaletteAction> {

   private static final Logger LOG = LogManager.getLogger(BGRequestPropertyPaletteHandler.class);

   @Inject
   protected BGPropertyPaletteProviderRegistry registry;

   @Override
   protected List<Action> executeAction(final BGRequestPropertyPaletteAction action) {
      return modelState.representation().get().map(representation -> {
         var elementId = action.getElementId();

         if (elementId == null) {
            return List.<Action> of(new BGSetPropertyPaletteAction());
         }

         var semanticElementOpt = modelState.getElementIndex().get(elementId);
         if (semanticElementOpt.isEmpty()) {
            return List.<Action> of(new BGSetPropertyPaletteAction());
         }
         var semanticElement = semanticElementOpt.get();

         var providerOpt = registry.getOrDefault(representation, semanticElement.getClass());
         if (providerOpt.isEmpty()) {
            return List.<Action> of(new BGSetPropertyPaletteAction());
         }
         var provider = providerOpt.get();
         var propertyPalette = provider.provide(semanticElement);

         return List.<Action> of(new BGSetPropertyPaletteAction(propertyPalette));
      }).orElse(List.of(new BGSetPropertyPaletteAction()));
   }
}
