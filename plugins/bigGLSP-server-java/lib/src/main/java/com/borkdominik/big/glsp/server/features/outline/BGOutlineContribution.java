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
package com.borkdominik.big.glsp.server.features.outline;

import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRItemContributionModule;
import com.borkdominik.big.glsp.server.features.outline.generator.BGOutlineGenerator;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGOutlineContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Class<? extends BGOutlineGenerator> concrete;

      Options(final BGRepresentationManifest manifest, final Class<? extends BGOutlineGenerator> concrete) {
         this.manifest = manifest;
         this.concrete = concrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Class<? extends BGOutlineGenerator> concrete;

         OptionsBuilder() {
         }

         public BGOutlineContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGOutlineContribution.Options.OptionsBuilder concrete(final Class<? extends BGOutlineGenerator> concrete) {
            this.concrete = concrete;
            return this;
         }

         public BGOutlineContribution.Options build() {
            return new BGOutlineContribution.Options(this.manifest, this.concrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGOutlineContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", concrete=" + this.concrete + ")";
         }
      }

      public static BGOutlineContribution.Options.OptionsBuilder builder() {
         return new BGOutlineContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Class<? extends BGOutlineGenerator> getConcrete() {
         return this.concrete;
      }
   }

   protected final Options options;

   public BGOutlineContribution() {
      this(Options.builder().build());
   }

   public BGOutlineContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRItemContributionModule<>(BGRItemContributionModule.Options.<BGOutlineGenerator>BGRItemContributionModuleBuilder().contributionType(TypeLiteralUtils.of(BGOutlineGenerator.class)).concreteType(TypeLiteralUtils.of(this.options.concrete)).build()));
   }
}
