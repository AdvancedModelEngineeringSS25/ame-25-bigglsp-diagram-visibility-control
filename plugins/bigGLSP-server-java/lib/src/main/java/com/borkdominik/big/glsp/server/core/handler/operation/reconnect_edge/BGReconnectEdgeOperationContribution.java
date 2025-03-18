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
package com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRItemContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGReconnectEdgeOperationContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Class<? extends BGReconnectEdgeHandler> defaultHandler;
      protected Class<? extends BGReconnectEdgeHandler> concrete;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Class<? extends BGReconnectEdgeHandler> defaultHandler, final Class<? extends BGReconnectEdgeHandler> concrete) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.defaultHandler = defaultHandler;
         this.concrete = concrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Class<? extends BGReconnectEdgeHandler> defaultHandler;
         private Class<? extends BGReconnectEdgeHandler> concrete;

         OptionsBuilder() {
         }

         public BGReconnectEdgeOperationContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGReconnectEdgeOperationContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGReconnectEdgeOperationContribution.Options.OptionsBuilder defaultHandler(final Class<? extends BGReconnectEdgeHandler> defaultHandler) {
            this.defaultHandler = defaultHandler;
            return this;
         }

         public BGReconnectEdgeOperationContribution.Options.OptionsBuilder concrete(final Class<? extends BGReconnectEdgeHandler> concrete) {
            this.concrete = concrete;
            return this;
         }

         public BGReconnectEdgeOperationContribution.Options build() {
            return new BGReconnectEdgeOperationContribution.Options(this.manifest, this.elementTypes, this.defaultHandler, this.concrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGReconnectEdgeOperationContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", defaultHandler=" + this.defaultHandler + ", concrete=" + this.concrete + ")";
         }
      }

      public static BGReconnectEdgeOperationContribution.Options.OptionsBuilder builder() {
         return new BGReconnectEdgeOperationContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Class<? extends BGReconnectEdgeHandler> getDefaultHandler() {
         return this.defaultHandler;
      }

      public Class<? extends BGReconnectEdgeHandler> getConcrete() {
         return this.concrete;
      }
   }

   protected final Options options;

   public BGReconnectEdgeOperationContribution() {
      this(Options.builder().build());
   }

   public BGReconnectEdgeOperationContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRItemContributionModule<>(BGRItemContributionModule.Options.<BGReconnectEdgeHandler>BGRItemContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(BGReconnectEdgeHandler.class)).concreteType(TypeLiteralUtils.of(this.options.defaultHandler)).build()));
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGReconnectEdgeHandler>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGReconnectEdgeHandler.class)).concreteTypes(TypeLiteralUtils.ofs(options.concrete)).build()));
   }
}
