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
package com.borkdominik.big.glsp.server.features.property_palette;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRItemContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyPaletteProvider;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGPropertyPaletteContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Set<Class<? extends BGPropertyProvider>> propertyProviders;
      protected Class<? extends BGPropertyPaletteProvider> defaultPaletteProvider;
      protected Class<? extends BGPropertyPaletteProvider> paletteProvider;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Set<Class<? extends BGPropertyProvider>> propertyProviders, final Class<? extends BGPropertyPaletteProvider> defaultPaletteProvider, final Class<? extends BGPropertyPaletteProvider> paletteProvider) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.propertyProviders = propertyProviders;
         this.defaultPaletteProvider = defaultPaletteProvider;
         this.paletteProvider = paletteProvider;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Set<Class<? extends BGPropertyProvider>> propertyProviders;
         private Class<? extends BGPropertyPaletteProvider> defaultPaletteProvider;
         private Class<? extends BGPropertyPaletteProvider> paletteProvider;

         OptionsBuilder() {
         }

         public BGPropertyPaletteContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGPropertyPaletteContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGPropertyPaletteContribution.Options.OptionsBuilder propertyProviders(final Set<Class<? extends BGPropertyProvider>> propertyProviders) {
            this.propertyProviders = propertyProviders;
            return this;
         }

         public BGPropertyPaletteContribution.Options.OptionsBuilder defaultPaletteProvider(final Class<? extends BGPropertyPaletteProvider> defaultPaletteProvider) {
            this.defaultPaletteProvider = defaultPaletteProvider;
            return this;
         }

         public BGPropertyPaletteContribution.Options.OptionsBuilder paletteProvider(final Class<? extends BGPropertyPaletteProvider> paletteProvider) {
            this.paletteProvider = paletteProvider;
            return this;
         }

         public BGPropertyPaletteContribution.Options build() {
            return new BGPropertyPaletteContribution.Options(this.manifest, this.elementTypes, this.propertyProviders, this.defaultPaletteProvider, this.paletteProvider);
         }

         @Override
         public java.lang.String toString() {
            return "BGPropertyPaletteContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", propertyProviders=" + this.propertyProviders + ", defaultPaletteProvider=" + this.defaultPaletteProvider + ", paletteProvider=" + this.paletteProvider + ")";
         }
      }

      public static BGPropertyPaletteContribution.Options.OptionsBuilder builder() {
         return new BGPropertyPaletteContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Set<Class<? extends BGPropertyProvider>> getPropertyProviders() {
         return this.propertyProviders;
      }

      public Class<? extends BGPropertyPaletteProvider> getDefaultPaletteProvider() {
         return this.defaultPaletteProvider;
      }

      public Class<? extends BGPropertyPaletteProvider> getPaletteProvider() {
         return this.paletteProvider;
      }
   }

   protected final Options options;

   public BGPropertyPaletteContribution() {
      this(Options.builder().build());
   }

   public BGPropertyPaletteContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRItemContributionModule<>(BGRItemContributionModule.Options.<BGPropertyPaletteProvider>BGRItemContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(BGPropertyPaletteProvider.class)).concreteType(TypeLiteralUtils.of(this.options.defaultPaletteProvider)).build()));
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGPropertyPaletteProvider>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGPropertyPaletteProvider.class)).concreteTypes(TypeLiteralUtils.ofs(options.paletteProvider)).build()));
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGPropertyProvider>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGPropertyProvider.class)).concreteTypes(TypeLiteralUtils.ofs(options.propertyProviders)).build()));
   }
}
