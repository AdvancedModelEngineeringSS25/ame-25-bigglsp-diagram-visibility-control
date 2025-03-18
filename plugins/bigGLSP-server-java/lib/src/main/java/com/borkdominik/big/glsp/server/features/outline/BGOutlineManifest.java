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
package com.borkdominik.big.glsp.server.features.outline;

import com.borkdominik.big.glsp.server.core.handler.action.BGActionFeatureContribution;
import com.borkdominik.big.glsp.server.core.manifest.BGFeatureManifest;
import com.borkdominik.big.glsp.server.features.outline.generator.BGDefaultOutlineGenerator;
import com.borkdominik.big.glsp.server.features.outline.generator.BGOutlineGenerator;
import com.borkdominik.big.glsp.server.features.outline.handler.BGOutlineGeneratorRegistry;
import com.borkdominik.big.glsp.server.features.outline.handler.BGRequestOutlineHandler;
import com.borkdominik.big.glsp.server.features.outline.handler.BGSetOutlineAction;
import com.google.inject.Singleton;
import com.google.inject.multibindings.OptionalBinder;

public class BGOutlineManifest extends BGFeatureManifest {

   @Override
   protected void configure() {
      super.configure();

      bind(BGOutlineGeneratorRegistry.class).in(Singleton.class);
      OptionalBinder.newOptionalBinder(binder(), BGOutlineGenerator.class)
         .setDefault()
         .to(BGDefaultOutlineGenerator.class)
         .in(Singleton.class);

      install(new BGActionFeatureContribution(BGActionFeatureContribution.Options.builder()
         .clientActions((contribution) -> {
            contribution.addBinding().to(BGSetOutlineAction.class);
         })
         .handlers((contribution) -> {
            contribution.addBinding().to(BGRequestOutlineHandler.class);
         })
         .build()));

      install(new BGOutlineContribution());
   }
}
