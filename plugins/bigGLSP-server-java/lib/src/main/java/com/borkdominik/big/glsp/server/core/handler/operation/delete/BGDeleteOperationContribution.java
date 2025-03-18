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
package com.borkdominik.big.glsp.server.core.handler.operation.delete;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRItemContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGDeleteOperationContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Class<? extends BGDeleteHandler> defaultHandler;
      protected Class<? extends BGDeleteHandler> concrete;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Class<? extends BGDeleteHandler> defaultHandler, final Class<? extends BGDeleteHandler> concrete) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.defaultHandler = defaultHandler;
         this.concrete = concrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Class<? extends BGDeleteHandler> defaultHandler;
         private Class<? extends BGDeleteHandler> concrete;

         OptionsBuilder() {
         }

         public BGDeleteOperationContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGDeleteOperationContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGDeleteOperationContribution.Options.OptionsBuilder defaultHandler(final Class<? extends BGDeleteHandler> defaultHandler) {
            this.defaultHandler = defaultHandler;
            return this;
         }

         public BGDeleteOperationContribution.Options.OptionsBuilder concrete(final Class<? extends BGDeleteHandler> concrete) {
            this.concrete = concrete;
            return this;
         }

         public BGDeleteOperationContribution.Options build() {
            return new BGDeleteOperationContribution.Options(this.manifest, this.elementTypes, this.defaultHandler, this.concrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGDeleteOperationContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", defaultHandler=" + this.defaultHandler + ", concrete=" + this.concrete + ")";
         }
      }

      public static BGDeleteOperationContribution.Options.OptionsBuilder builder() {
         return new BGDeleteOperationContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Class<? extends BGDeleteHandler> getDefaultHandler() {
         return this.defaultHandler;
      }

      public Class<? extends BGDeleteHandler> getConcrete() {
         return this.concrete;
      }
   }

   protected final Options options;

   public BGDeleteOperationContribution() {
      this(Options.builder().build());
   }

   public BGDeleteOperationContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRItemContributionModule<>(BGRItemContributionModule.Options.<BGDeleteHandler>BGRItemContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(BGDeleteHandler.class)).concreteType(TypeLiteralUtils.of(this.options.defaultHandler)).build()));
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGDeleteHandler>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGDeleteHandler.class)).concreteTypes(TypeLiteralUtils.ofs(options.concrete)).build()));
   }
}
