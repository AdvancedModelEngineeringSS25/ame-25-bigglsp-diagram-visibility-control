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
package com.borkdominik.big.glsp.server.core.features.direct_editing;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.features.direct_editing.label_edit.BGLabelEditHandler;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRItemContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGDirectEditContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Class<? extends BGDirectEditHandler> defaultDirectEditHandler;
      protected Class<? extends BGDirectEditHandler> directEditHandler;
      protected Set<Class<? extends BGLabelEditHandler>> labelEditHandlers;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Class<? extends BGDirectEditHandler> defaultDirectEditHandler, final Class<? extends BGDirectEditHandler> directEditHandler, final Set<Class<? extends BGLabelEditHandler>> labelEditHandlers) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.defaultDirectEditHandler = defaultDirectEditHandler;
         this.directEditHandler = directEditHandler;
         this.labelEditHandlers = labelEditHandlers;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Class<? extends BGDirectEditHandler> defaultDirectEditHandler;
         private Class<? extends BGDirectEditHandler> directEditHandler;
         private Set<Class<? extends BGLabelEditHandler>> labelEditHandlers;

         OptionsBuilder() {
         }

         public BGDirectEditContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGDirectEditContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGDirectEditContribution.Options.OptionsBuilder defaultDirectEditHandler(final Class<? extends BGDirectEditHandler> defaultDirectEditHandler) {
            this.defaultDirectEditHandler = defaultDirectEditHandler;
            return this;
         }

         public BGDirectEditContribution.Options.OptionsBuilder directEditHandler(final Class<? extends BGDirectEditHandler> directEditHandler) {
            this.directEditHandler = directEditHandler;
            return this;
         }

         public BGDirectEditContribution.Options.OptionsBuilder labelEditHandlers(final Set<Class<? extends BGLabelEditHandler>> labelEditHandlers) {
            this.labelEditHandlers = labelEditHandlers;
            return this;
         }

         public BGDirectEditContribution.Options build() {
            return new BGDirectEditContribution.Options(this.manifest, this.elementTypes, this.defaultDirectEditHandler, this.directEditHandler, this.labelEditHandlers);
         }

         @Override
         public java.lang.String toString() {
            return "BGDirectEditContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", defaultDirectEditHandler=" + this.defaultDirectEditHandler + ", directEditHandler=" + this.directEditHandler + ", labelEditHandlers=" + this.labelEditHandlers + ")";
         }
      }

      public static BGDirectEditContribution.Options.OptionsBuilder builder() {
         return new BGDirectEditContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Class<? extends BGDirectEditHandler> getDefaultDirectEditHandler() {
         return this.defaultDirectEditHandler;
      }

      public Class<? extends BGDirectEditHandler> getDirectEditHandler() {
         return this.directEditHandler;
      }

      public Set<Class<? extends BGLabelEditHandler>> getLabelEditHandlers() {
         return this.labelEditHandlers;
      }
   }

   protected final Options options;

   public BGDirectEditContribution() {
      this(Options.builder().build());
   }

   public BGDirectEditContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRItemContributionModule<>(BGRItemContributionModule.Options.<BGDirectEditHandler>BGRItemContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(BGDirectEditHandler.class)).concreteType(TypeLiteralUtils.of(this.options.defaultDirectEditHandler)).build()));
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGDirectEditHandler>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGDirectEditHandler.class)).concreteTypes(TypeLiteralUtils.ofs(options.directEditHandler)).build()));
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGLabelEditHandler>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGLabelEditHandler.class)).concreteTypes(TypeLiteralUtils.ofs(options.labelEditHandlers)).build()));
   }
}
