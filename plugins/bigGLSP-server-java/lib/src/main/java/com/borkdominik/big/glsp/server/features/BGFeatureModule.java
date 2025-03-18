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
package com.borkdominik.big.glsp.server.features;

import com.borkdominik.big.glsp.server.features.autocomplete.BGAutocompleteManifest;
import com.borkdominik.big.glsp.server.features.outline.BGOutlineManifest;
import com.borkdominik.big.glsp.server.features.property_palette.BGPropertyPaletteManifest;
import com.google.inject.AbstractModule;

public class BGFeatureModule extends AbstractModule {

   @Override
   protected void configure() {
      super.configure();

      install(new BGOutlineManifest());
      install(new BGPropertyPaletteManifest());
      install(new BGAutocompleteManifest());
   }
}
