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

import java.util.Set;
import com.borkdominik.big.glsp.server.core.di.BGRFactory;
import com.borkdominik.big.glsp.server.core.di.DIProvider;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRSetContributionModule;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class BGRFactoryMapContributionModule<TContribution, TConcrete extends TContribution, TOptions extends BGRFactoryMapContributionModule.Options<TContribution, TConcrete>> extends BGRSetContributionModule<TContribution, TOptions> {
   protected TypeLiteral<TConcrete> concreteType;
   protected TypeLiteral<BGRFactory<TConcrete>> factoryType;

   public static class Options<TContribution, TConcrete> extends BGRSetContributionModule.Options<TContribution> {
      public Set<BGTypeProvider> elementTypes;
      public Class<TConcrete> concrete;

      public static abstract class OptionsBuilder<TContribution, TConcrete, C extends BGRFactoryMapContributionModule.Options<TContribution, TConcrete>, B extends BGRFactoryMapContributionModule.Options.OptionsBuilder<TContribution, TConcrete, C, B>> extends BGRSetContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private Set<BGTypeProvider> elementTypes;
         private Class<TConcrete> concrete;

         public B elementTypes(final Set<BGTypeProvider> elementTypes) {
            this.elementTypes = elementTypes;
            return self();
         }

         public B concrete(final Class<TConcrete> concrete) {
            this.concrete = concrete;
            return self();
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGRFactoryMapContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", elementTypes=" + this.elementTypes + ", concrete=" + this.concrete + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution, TConcrete> extends BGRFactoryMapContributionModule.Options.OptionsBuilder<TContribution, TConcrete, BGRFactoryMapContributionModule.Options<TContribution, TConcrete>, BGRFactoryMapContributionModule.Options.OptionsBuilderImpl<TContribution, TConcrete>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGRFactoryMapContributionModule.Options.OptionsBuilderImpl<TContribution, TConcrete> self() {
            return this;
         }

         @Override
         public BGRFactoryMapContributionModule.Options<TContribution, TConcrete> build() {
            return new BGRFactoryMapContributionModule.Options<TContribution, TConcrete>(this);
         }
      }

      protected Options(final BGRFactoryMapContributionModule.Options.OptionsBuilder<TContribution, TConcrete, ?, ?> b) {
         super(b);
         this.elementTypes = b.elementTypes;
         this.concrete = b.concrete;
      }

      public static <TContribution, TConcrete> BGRFactoryMapContributionModule.Options.OptionsBuilder<TContribution, TConcrete, ?, ?> BGRFactoryMapContributionModuleBuilder() {
         return new BGRFactoryMapContributionModule.Options.OptionsBuilderImpl<TContribution, TConcrete>();
      }
   }

   public BGRFactoryMapContributionModule(final TOptions options) {
      super(options);
      options.consumer = this::consume;
   }

   @Override
   protected boolean isDefinition() {
      return super.isDefinition() || options.concrete == null;
   }

   @Override
   protected void onBeforeContribute() {
      super.onBeforeContribute();
      this.concreteType = TypeLiteral.get(options.concrete);
      this.configureFactory();
   }

   protected void configureFactory() {
      this.factoryType = TypeLiteralUtils.parameterizedType(BGRFactory.class, this.concreteType);
      this.install(new BGRFactoryContributionModule<>(this.factoryType));
   }

   protected void consume(final Multibinder<TContribution> contribution) {
      var provider = DIProvider.byLiteral(this.factoryType, fa -> fa.create(options.manifest.representation()));
      contribution.addBinding().toProvider(provider);
   }
}
