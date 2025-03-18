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
package com.borkdominik.big.glsp.server.elements.manifest.integrations;

import java.util.Set;

import com.borkdominik.big.glsp.server.core.gmodel.BGEMFGModelFeatureContribution;
import com.borkdominik.big.glsp.server.core.gmodel.BGEMFGModelMapper;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.manifest.BGEdgeElementManifest;

public abstract class BGEMFEdgeElementManifest extends BGEdgeElementManifest {

   public BGEMFEdgeElementManifest(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes) {
      super(manifest, elementTypes);
   }

   protected void bindGModelMapper(final Class<? extends BGEMFGModelMapper<?, ?>> handler) {
      install(new BGEMFGModelFeatureContribution(BGEMFGModelFeatureContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .concrete(handler)
         .build()));
   }
}
