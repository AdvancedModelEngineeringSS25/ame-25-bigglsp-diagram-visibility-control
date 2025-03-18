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
import com.google.inject.TypeLiteral;

public class BGDiagramEdgeConfigurationContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Set<TypeLiteral<? extends BGDiagramConfigurationProvider.Edge>> concretes;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Set<TypeLiteral<? extends BGDiagramConfigurationProvider.Edge>> concretes) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.concretes = concretes;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Set<TypeLiteral<? extends BGDiagramConfigurationProvider.Edge>> concretes;

         OptionsBuilder() {
         }

         public BGDiagramEdgeConfigurationContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGDiagramEdgeConfigurationContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGDiagramEdgeConfigurationContribution.Options.OptionsBuilder concretes(final Set<TypeLiteral<? extends BGDiagramConfigurationProvider.Edge>> concretes) {
            this.concretes = concretes;
            return this;
         }

         public BGDiagramEdgeConfigurationContribution.Options build() {
            return new BGDiagramEdgeConfigurationContribution.Options(this.manifest, this.elementTypes, this.concretes);
         }

         @Override
         public java.lang.String toString() {
            return "BGDiagramEdgeConfigurationContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", concretes=" + this.concretes + ")";
         }
      }

      public static BGDiagramEdgeConfigurationContribution.Options.OptionsBuilder builder() {
         return new BGDiagramEdgeConfigurationContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Set<TypeLiteral<? extends BGDiagramConfigurationProvider.Edge>> getConcretes() {
         return this.concretes;
      }
   }

   protected final Options options;

   public BGDiagramEdgeConfigurationContribution() {
      this(Options.builder().build());
   }

   public BGDiagramEdgeConfigurationContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGDiagramConfigurationProvider.Edge>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGDiagramConfigurationProvider.Edge.class)).concreteTypes(options.concretes).build()));
   }
}
