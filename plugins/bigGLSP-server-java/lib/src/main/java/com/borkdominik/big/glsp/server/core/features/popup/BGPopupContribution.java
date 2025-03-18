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
package com.borkdominik.big.glsp.server.core.features.popup;

import org.eclipse.emf.ecore.EObject;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRSetContributionModule;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGPopupContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Class<? extends BGPopupMapper<? extends EObject>> concrete;

      Options(final BGRepresentationManifest manifest, final Class<? extends BGPopupMapper<? extends EObject>> concrete) {
         this.manifest = manifest;
         this.concrete = concrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Class<? extends BGPopupMapper<? extends EObject>> concrete;

         OptionsBuilder() {
         }

         public BGPopupContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGPopupContribution.Options.OptionsBuilder concrete(final Class<? extends BGPopupMapper<? extends EObject>> concrete) {
            this.concrete = concrete;
            return this;
         }

         public BGPopupContribution.Options build() {
            return new BGPopupContribution.Options(this.manifest, this.concrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGPopupContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", concrete=" + this.concrete + ")";
         }
      }

      public static BGPopupContribution.Options.OptionsBuilder builder() {
         return new BGPopupContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Class<? extends BGPopupMapper<? extends EObject>> getConcrete() {
         return this.concrete;
      }
   }

   protected final Options options;

   public BGPopupContribution() {
      this(Options.builder().build());
   }

   public BGPopupContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRSetContributionModule<>(BGRSetContributionModule.Options.<BGPopupMapper<? extends EObject>>BGRSetContributionModuleBuilder().contributionType(TypeLiteralUtils.subtypeOf(BGPopupMapper.class, EObject.class)).consumer(contribution -> contribution.addBinding().to(options.concrete)).build()));
   }
}
