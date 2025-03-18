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

import com.borkdominik.big.glsp.server.core.diagram.BGDiagramEdgeConfigurationContribution;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateOperationContribution;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeOperationContribution;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeHandler;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.configuration.BGConfigurationFeatureContribution;
import com.borkdominik.big.glsp.server.elements.configuration.BGEdgeConfiguration;
import com.borkdominik.big.glsp.server.elements.handler.operations.BGEdgeOperationHandler;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public abstract class BGEdgeElementManifest extends BGElementManifest {

   public BGEdgeElementManifest(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes) {
      super(manifest, elementTypes);
   }

   protected void bindConfiguration(final Class<? extends BGEdgeConfiguration> configuration) {
      install(new BGDiagramEdgeConfigurationContribution(BGDiagramEdgeConfigurationContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .concretes(Set.of(TypeLiteralUtils.of(configuration)))
         .build()));
      install(new BGConfigurationFeatureContribution(BGConfigurationFeatureContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .edgeConfiguration(configuration)
         .build()));
   }

   protected void bindCreateHandler(final Class<? extends BGEdgeOperationHandler<?, ?, ?>> handler) {
      install(new BGCreateOperationContribution(BGCreateOperationContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .createEdgeConcrete(handler)
         .build()));
   }

   protected void bindReconnectHandler(final Class<? extends BGReconnectEdgeHandler> handler) {
      install(new BGReconnectEdgeOperationContribution(BGReconnectEdgeOperationContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .concrete(handler)
         .build()));
   }
}
