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
package com.borkdominik.big.glsp.server.core.gmodel;

import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRElementFactorySetContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.TypeLiteral;

public class BGEMFGModelFeatureContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<BGTypeProvider> elementTypes;
      protected Class<? extends BGEMFGModelMapper<? extends EObject, ? extends GModelElement>> concrete;

      Options(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes, final Class<? extends BGEMFGModelMapper<? extends EObject, ? extends GModelElement>> concrete) {
         this.manifest = manifest;
         this.elementTypes = elementTypes;
         this.concrete = concrete;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<BGTypeProvider> elementTypes;
         private Class<? extends BGEMFGModelMapper<? extends EObject, ? extends GModelElement>> concrete;

         OptionsBuilder() {
         }

         public BGEMFGModelFeatureContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGEMFGModelFeatureContribution.Options.OptionsBuilder elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return this;
         }

         public BGEMFGModelFeatureContribution.Options.OptionsBuilder concrete(final Class<? extends BGEMFGModelMapper<? extends EObject, ? extends GModelElement>> concrete) {
            this.concrete = concrete;
            return this;
         }

         public BGEMFGModelFeatureContribution.Options build() {
            return new BGEMFGModelFeatureContribution.Options(this.manifest, this.elementTypes, this.concrete);
         }

         @Override
         public java.lang.String toString() {
            return "BGEMFGModelFeatureContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", elementTypes=" + this.elementTypes + ", concrete=" + this.concrete + ")";
         }
      }

      public static BGEMFGModelFeatureContribution.Options.OptionsBuilder builder() {
         return new BGEMFGModelFeatureContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<BGTypeProvider> getElementTypes() {
         return this.elementTypes;
      }

      public Class<? extends BGEMFGModelMapper<? extends EObject, ? extends GModelElement>> getConcrete() {
         return this.concrete;
      }
   }

   protected final Options options;

   public BGEMFGModelFeatureContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRElementFactorySetContributionModule<>(BGRElementFactorySetContributionModule.Options.<BGEMFGModelMapper<? extends EObject, ? extends GModelElement>>BGRElementFactorySetContributionModuleBuilder().manifest(this.options.manifest).elementTypes(this.options.elementTypes).contributionType(new TypeLiteral<BGEMFGModelMapper<? extends EObject, ? extends GModelElement>>() {
      }).concreteTypes(Set.of(TypeLiteralUtils.of(options.concrete))).build()));
   }
}
