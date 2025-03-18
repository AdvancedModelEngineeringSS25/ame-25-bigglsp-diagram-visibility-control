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
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRFactorySetContributionModule;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.TypeLiteral;

public class BGDiagramConfigurationContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<TypeLiteral<? extends BGDiagramConfigurationProvider>> concretes;

      Options(final BGRepresentationManifest manifest, final Set<TypeLiteral<? extends BGDiagramConfigurationProvider>> concretes) {
         this.manifest = manifest;
         this.concretes = concretes;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<TypeLiteral<? extends BGDiagramConfigurationProvider>> concretes;

         OptionsBuilder() {
         }

         public BGDiagramConfigurationContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGDiagramConfigurationContribution.Options.OptionsBuilder concretes(final Set<TypeLiteral<? extends BGDiagramConfigurationProvider>> concretes) {
            this.concretes = concretes;
            return this;
         }

         public BGDiagramConfigurationContribution.Options build() {
            return new BGDiagramConfigurationContribution.Options(this.manifest, this.concretes);
         }

         @Override
         public java.lang.String toString() {
            return "BGDiagramConfigurationContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", concretes=" + this.concretes + ")";
         }
      }

      public static BGDiagramConfigurationContribution.Options.OptionsBuilder builder() {
         return new BGDiagramConfigurationContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<TypeLiteral<? extends BGDiagramConfigurationProvider>> getConcretes() {
         return this.concretes;
      }
   }

   protected final Options options;

   public BGDiagramConfigurationContribution() {
      this(Options.builder().build());
   }

   public BGDiagramConfigurationContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRFactorySetContributionModule<>(BGRFactorySetContributionModule.Options.<BGDiagramConfigurationProvider>BGRFactorySetContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(BGDiagramConfigurationProvider.class)).concreteTypes(options.concretes).build()));
   }
}
