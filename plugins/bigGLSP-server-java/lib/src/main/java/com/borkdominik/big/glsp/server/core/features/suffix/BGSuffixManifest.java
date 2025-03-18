/********************************************************************************
 * Copyright (c) 2024 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.features.suffix;

import com.borkdominik.big.glsp.server.core.manifest.BGFeatureManifest;
import com.google.inject.Singleton;

public class BGSuffixManifest extends BGFeatureManifest {

   @Override
   protected void configure() {
      super.configure();

      bind(BGSuffix.class).in(Singleton.class);
      bind(BGIdCountContextGenerator.class).in(Singleton.class);

      install(new BGSuffixContribution(BGSuffixContribution.Options.builder()
         .suffix((contribution) -> {
            contribution.addBinding(BGNameLabelSuffix.SUFFIX).to(BGNameLabelSuffix.class);
         })
         .build()));
   }
}
