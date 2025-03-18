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
package com.borkdominik.big.glsp.server.core.handler.operation.create;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGCreateOperationContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Class<? extends BGCreateNodeHandler> createNodeConcrete;
      protected Class<? extends BGCreateEdgeHandler> createEdgeConcrete;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Class<? extends BGCreateNodeHandler> createNodeConcrete, final Class<? extends BGCreateEdgeHandler> createEdgeConcrete) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.createNodeConcrete = createNodeConcrete;
         this.createEdgeConcrete = createEdgeConcrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Class<? extends BGCreateNodeHandler> createNodeConcrete;
         private Class<? extends BGCreateEdgeHandler> createEdgeConcrete;

         OptionsBuilder() {
         }

         public BGCreateOperationContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGCreateOperationContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGCreateOperationContribution.Options.OptionsBuilder createNodeConcrete(final Class<? extends BGCreateNodeHandler> createNodeConcrete) {
            this.createNodeConcrete = createNodeConcrete;
            return this;
         }

         public BGCreateOperationContribution.Options.OptionsBuilder createEdgeConcrete(final Class<? extends BGCreateEdgeHandler> createEdgeConcrete) {
            this.createEdgeConcrete = createEdgeConcrete;
            return this;
         }

         public BGCreateOperationContribution.Options build() {
            return new BGCreateOperationContribution.Options(this.manifest, this.elementTypes, this.createNodeConcrete, this.createEdgeConcrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGCreateOperationContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", createNodeConcrete=" + this.createNodeConcrete + ", createEdgeConcrete=" + this.createEdgeConcrete + ")";
         }
      }

      public static BGCreateOperationContribution.Options.OptionsBuilder builder() {
         return new BGCreateOperationContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Class<? extends BGCreateNodeHandler> getCreateNodeConcrete() {
         return this.createNodeConcrete;
      }

      public Class<? extends BGCreateEdgeHandler> getCreateEdgeConcrete() {
         return this.createEdgeConcrete;
      }
   }

   protected final Options options;

   public BGCreateOperationContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      if (options.createNodeConcrete != null) {
         install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGCreateNodeHandler>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGCreateNodeHandler.class)).concreteTypes(Set.of(TypeLiteralUtils.of(options.createNodeConcrete))).build()));
      }
      if (options.createEdgeConcrete != null) {
         install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGCreateEdgeHandler>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(TypeLiteralUtils.of(BGCreateEdgeHandler.class)).concreteTypes(Set.of(TypeLiteralUtils.of(options.createEdgeConcrete))).build()));
      }
   }
}
