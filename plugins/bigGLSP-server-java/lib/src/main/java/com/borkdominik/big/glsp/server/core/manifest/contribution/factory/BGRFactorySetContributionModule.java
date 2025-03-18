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
package com.borkdominik.big.glsp.server.core.manifest.contribution.factory;

import java.util.HashSet;
import java.util.Set;
import com.borkdominik.big.glsp.server.core.di.BGRFactory;
import com.borkdominik.big.glsp.server.core.di.DIProvider;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRSetContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class BGRFactorySetContributionModule<TContribution, TOptions extends BGRFactorySetContributionModule.Options<TContribution>> extends BGRSetContributionModule<TContribution, TOptions> {
   protected Set<TypeLiteral<BGRFactory<? extends TContribution>>> factoryTypes = new HashSet<>();

   public static class Options<TContribution> extends BGRSetContributionModule.Options<TContribution> {
      public Set<BGTypeProvider> elementTypes;
      public Set<TypeLiteral<? extends TContribution>> concreteTypes;

      public static abstract class OptionsBuilder<TContribution, C extends BGRFactorySetContributionModule.Options<TContribution>, B extends BGRFactorySetContributionModule.Options.OptionsBuilder<TContribution, C, B>> extends BGRSetContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private Set<BGTypeProvider> elementTypes;
         private Set<TypeLiteral<? extends TContribution>> concreteTypes;

         public B elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return self();
         }

         public B concreteTypes(final Set<TypeLiteral<? extends TContribution>> concreteTypes) {
            this.concreteTypes = concreteTypes;
            return self();
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGRFactorySetContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", elementTypes=" + this.elementTypes + ", concreteTypes=" + this.concreteTypes + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution> extends BGRFactorySetContributionModule.Options.OptionsBuilder<TContribution, BGRFactorySetContributionModule.Options<TContribution>, BGRFactorySetContributionModule.Options.OptionsBuilderImpl<TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGRFactorySetContributionModule.Options.OptionsBuilderImpl<TContribution> self() {
            return this;
         }

         @Override
         public BGRFactorySetContributionModule.Options<TContribution> build() {
            return new BGRFactorySetContributionModule.Options<TContribution>(this);
         }
      }

      protected Options(final BGRFactorySetContributionModule.Options.OptionsBuilder<TContribution, ?, ?> b) {
         super(b);
         this.elementTypes = b.elementTypes;
         this.concreteTypes = b.concreteTypes;
      }

      public static <TContribution> BGRFactorySetContributionModule.Options.OptionsBuilder<TContribution, ?, ?> BGRFactorySetContributionModuleBuilder() {
         return new BGRFactorySetContributionModule.Options.OptionsBuilderImpl<TContribution>();
      }
   }

   public BGRFactorySetContributionModule(final TOptions options) {
      super(options);
      options.consumer = this::consume;
   }

   @Override
   protected boolean isDefinition() {
      return super.isDefinition() || options.concreteTypes == null || options.concreteTypes.size() == 0;
   }

   @Override
   protected void onBeforeContribute() {
      super.onBeforeContribute();
      this.configureFactory();
   }

   protected void configureFactory() {
      for (var concreteType : options.concreteTypes) {
         TypeLiteral<BGRFactory<? extends TContribution>> factoryType = TypeLiteralUtils.parameterizedType(BGRFactory.class, concreteType);
         this.factoryTypes.add(factoryType);
         this.install(new BGRFactoryContributionModule<>(factoryType));
      }
   }

   protected void consume(final Multibinder<TContribution> contribution) {
      for (var factoryType : factoryTypes) {
         var provider = DIProvider.byLiteral(factoryType, fa -> fa.create(options.manifest.representation()));
         contribution.addBinding().toProvider(provider);
      }
   }
}
