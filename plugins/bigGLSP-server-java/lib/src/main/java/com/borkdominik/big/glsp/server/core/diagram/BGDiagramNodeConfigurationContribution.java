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
package com.borkdominik.big.glsp.server.core.diagram;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGDiagramNodeConfigurationContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Set<Class<? extends BGDiagramConfigurationProvider.Node>> concretes;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Set<Class<? extends BGDiagramConfigurationProvider.Node>> concretes) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.concretes = concretes;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Set<Class<? extends BGDiagramConfigurationProvider.Node>> concretes;

         OptionsBuilder() {
         }

         public BGDiagramNodeConfigurationContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGDiagramNodeConfigurationContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGDiagramNodeConfigurationContribution.Options.OptionsBuilder concretes(final Set<Class<? extends BGDiagramConfigurationProvider.Node>> concretes) {
            this.concretes = concretes;
            return this;
         }

         public BGDiagramNodeConfigurationContribution.Options build() {
            return new BGDiagramNodeConfigurationContribution.Options(this.manifest, this.elementTypes, this.concretes);
         }

         @Override
         public java.lang.String toString() {
            return "BGDiagramNodeConfigurationContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", concretes=" + this.concretes + ")";
         }
      }

      public static BGDiagramNodeConfigurationContribution.Options.OptionsBuilder builder() {
         return new BGDiagramNodeConfigurationContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Set<Class<? extends BGDiagramConfigurationProvider.Node>> getConcretes() {
         return this.concretes;
      }
   }

   protected final Options options;

   public BGDiagramNodeConfigurationContribution() {
      this(Options.builder().build());
   }

   public BGDiagramNodeConfigurationContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGDiagramConfigurationProvider.Node>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGDiagramConfigurationProvider.Node.class)).concreteTypes(TypeLiteralUtils.ofs(options.concretes)).build()));
   }
}
