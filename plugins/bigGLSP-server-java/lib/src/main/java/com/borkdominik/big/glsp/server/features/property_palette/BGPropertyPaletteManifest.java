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
package com.borkdominik.big.glsp.server.features.property_palette;

import com.borkdominik.big.glsp.server.core.handler.action.BGActionFeatureContribution;
import com.borkdominik.big.glsp.server.core.manifest.BGFeatureManifest;
import com.borkdominik.big.glsp.server.features.property_palette.handler.BGRequestPropertyPaletteHandler;
import com.borkdominik.big.glsp.server.features.property_palette.handler.BGSetPropertyPaletteAction;
import com.borkdominik.big.glsp.server.features.property_palette.handler.BGUpdateElementPropertyHandler;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyPaletteProviderRegistry;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyProviderRegistry;
import com.google.inject.Singleton;

public class BGPropertyPaletteManifest extends BGFeatureManifest {

   @Override
   protected void configure() {
      super.configure();

      bind(BGPropertyPaletteProviderRegistry.class).in(Singleton.class);
      bind(BGPropertyProviderRegistry.class).in(Singleton.class);

      install(new BGActionFeatureContribution(BGActionFeatureContribution.Options.builder()
         .clientActions((contribution) -> {
            contribution.addBinding().to(BGSetPropertyPaletteAction.class);
         })
         .handlers((contribution) -> {
            contribution.addBinding().to(BGRequestPropertyPaletteHandler.class);
            contribution.addBinding().to(BGUpdateElementPropertyHandler.class);
         })
         .build()));

      install(new BGPropertyPaletteContribution());
   }

}
