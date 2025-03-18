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
package com.borkdominik.big.glsp.server.features.autocomplete;

import com.borkdominik.big.glsp.server.core.handler.action.BGActionFeatureContribution;
import com.borkdominik.big.glsp.server.core.manifest.BGFeatureManifest;
import com.borkdominik.big.glsp.server.features.autocomplete.handler.BGAutocompleteContextActionsProvider;
import com.borkdominik.big.glsp.server.features.autocomplete.provider.BGAutocompleteEntriesRegistry;
import com.google.inject.Singleton;

public class BGAutocompleteManifest extends BGFeatureManifest {

   @Override
   protected void configure() {
      super.configure();

      bind(BGAutocompleteEntriesRegistry.class).in(Singleton.class);

      install(new BGActionFeatureContribution(BGActionFeatureContribution.Options.builder()
         .contextActionsProviders((contribution) -> {
            contribution.addBinding().to(BGAutocompleteContextActionsProvider.class).in(Singleton.class);
         })
         .build()));

      install(new BGAutocompleteContribution());
   }
}
