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
package com.borkdominik.big.glsp.server.elements.configuration;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGConfigurationFeatureContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Class<? extends BGEdgeConfiguration> edgeConfiguration;
      protected Class<? extends BGNodeConfiguration> nodeConfiguration;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Class<? extends BGEdgeConfiguration> edgeConfiguration, final Class<? extends BGNodeConfiguration> nodeConfiguration) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.edgeConfiguration = edgeConfiguration;
         this.nodeConfiguration = nodeConfiguration;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Class<? extends BGEdgeConfiguration> edgeConfiguration;
         private Class<? extends BGNodeConfiguration> nodeConfiguration;

         OptionsBuilder() {
         }

         public BGConfigurationFeatureContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGConfigurationFeatureContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGConfigurationFeatureContribution.Options.OptionsBuilder edgeConfiguration(final Class<? extends BGEdgeConfiguration> edgeConfiguration) {
            this.edgeConfiguration = edgeConfiguration;
            return this;
         }

         public BGConfigurationFeatureContribution.Options.OptionsBuilder nodeConfiguration(final Class<? extends BGNodeConfiguration> nodeConfiguration) {
            this.nodeConfiguration = nodeConfiguration;
            return this;
         }

         public BGConfigurationFeatureContribution.Options build() {
            return new BGConfigurationFeatureContribution.Options(this.manifest, this.elementTypes, this.edgeConfiguration, this.nodeConfiguration);
         }

         @Override
         public java.lang.String toString() {
            return "BGConfigurationFeatureContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", edgeConfiguration=" + this.edgeConfiguration + ", nodeConfiguration=" + this.nodeConfiguration + ")";
         }
      }

      public static BGConfigurationFeatureContribution.Options.OptionsBuilder builder() {
         return new BGConfigurationFeatureContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Class<? extends BGEdgeConfiguration> getEdgeConfiguration() {
         return this.edgeConfiguration;
      }

      public Class<? extends BGNodeConfiguration> getNodeConfiguration() {
         return this.nodeConfiguration;
      }
   }

   protected final Options options;

   public BGConfigurationFeatureContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      if (options.nodeConfiguration != null) {
         install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGNodeConfiguration>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGNodeConfiguration.class)).concreteTypes(Set.of(TypeLiteralUtils.of(options.nodeConfiguration))).build()));
      }
      if (options.edgeConfiguration != null) {
         install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGEdgeConfiguration>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGEdgeConfiguration.class)).concreteTypes(Set.of(TypeLiteralUtils.of(options.edgeConfiguration))).build()));
      }
   }
}
