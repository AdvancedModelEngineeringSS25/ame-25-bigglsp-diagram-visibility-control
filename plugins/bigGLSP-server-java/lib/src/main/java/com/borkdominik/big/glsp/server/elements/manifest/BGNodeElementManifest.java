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
package com.borkdominik.big.glsp.server.elements.manifest;

import java.util.Set;

import com.borkdominik.big.glsp.server.core.diagram.BGDiagramNodeConfigurationContribution;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateOperationContribution;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.configuration.BGConfigurationFeatureContribution;
import com.borkdominik.big.glsp.server.elements.configuration.BGNodeConfiguration;
import com.borkdominik.big.glsp.server.elements.handler.operations.BGNodeOperationHandler;

public abstract class BGNodeElementManifest extends BGElementManifest {

   public BGNodeElementManifest(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes) {
      super(manifest, elementTypes);
   }

   protected void bindConfiguration(final Class<? extends BGNodeConfiguration> configuration) {
      install(new BGDiagramNodeConfigurationContribution(BGDiagramNodeConfigurationContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .concretes(Set.of(configuration))
         .build()));
      install(new BGConfigurationFeatureContribution(BGConfigurationFeatureContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .nodeConfiguration(configuration)
         .build()));
   }

   protected void bindCreateHandler(final Class<? extends BGNodeOperationHandler<?, ?>> handler) {
      install(new BGCreateOperationContribution(BGCreateOperationContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .createNodeConcrete(handler)
         .build()));
   }
}
