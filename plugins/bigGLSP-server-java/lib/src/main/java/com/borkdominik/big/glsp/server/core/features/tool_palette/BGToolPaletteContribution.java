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
package com.borkdominik.big.glsp.server.core.features.tool_palette;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRFactorySetContributionModule;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGToolPaletteContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Class<? extends BGToolPaletteProvider> concrete;

      Options(final BGRepresentationManifest manifest, final Class<? extends BGToolPaletteProvider> concrete) {
         this.manifest = manifest;
         this.concrete = concrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Class<? extends BGToolPaletteProvider> concrete;

         OptionsBuilder() {
         }

         public BGToolPaletteContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGToolPaletteContribution.Options.OptionsBuilder concrete(final Class<? extends BGToolPaletteProvider> concrete) {
            this.concrete = concrete;
            return this;
         }

         public BGToolPaletteContribution.Options build() {
            return new BGToolPaletteContribution.Options(this.manifest, this.concrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGToolPaletteContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", concrete=" + this.concrete + ")";
         }
      }

      public static BGToolPaletteContribution.Options.OptionsBuilder builder() {
         return new BGToolPaletteContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Class<? extends BGToolPaletteProvider> getConcrete() {
         return this.concrete;
      }
   }

   protected final Options options;

   public BGToolPaletteContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRFactorySetContributionModule<>(BGRFactorySetContributionModule.Options.<BGToolPaletteProvider>BGRFactorySetContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(BGToolPaletteProvider.class)).concreteTypes(Set.of(TypeLiteralUtils.of(options.concrete))).build()));
   }
}
