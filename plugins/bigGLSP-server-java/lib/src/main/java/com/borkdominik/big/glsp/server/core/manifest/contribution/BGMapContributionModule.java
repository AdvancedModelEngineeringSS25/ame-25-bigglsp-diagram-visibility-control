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
package com.borkdominik.big.glsp.server.core.manifest.contribution;

import java.util.function.Consumer;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRContributionModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class BGMapContributionModule<TKey, TContribution, TOptions extends BGMapContributionModule.Options<TKey, TContribution>> extends BGContributionModule<TContribution, MapBinder<TKey, TContribution>, TOptions> {

   public static class Options<TKey, TContribution> extends BGRContributionModule.Options<TContribution> {
      public TypeLiteral<TKey> keyType;
      public Consumer<MapBinder<TKey, TContribution>> consumer;

      public static abstract class OptionsBuilder<TKey, TContribution, C extends BGMapContributionModule.Options<TKey, TContribution>, B extends BGMapContributionModule.Options.OptionsBuilder<TKey, TContribution, C, B>> extends BGRContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private TypeLiteral<TKey> keyType;
         private Consumer<MapBinder<TKey, TContribution>> consumer;

         public B keyType(final TypeLiteral<TKey> keyType) {
            this.keyType = keyType;
            return self();
         }

         public B consumer(final Consumer<MapBinder<TKey, TContribution>> consumer) {
            this.consumer = consumer;
            return self();
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGMapContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", keyType=" + this.keyType + ", consumer=" + this.consumer + ")";
         }
      }

      private static final class OptionsBuilderImpl<TKey, TContribution> extends BGMapContributionModule.Options.OptionsBuilder<TKey, TContribution, BGMapContributionModule.Options<TKey, TContribution>, BGMapContributionModule.Options.OptionsBuilderImpl<TKey, TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGMapContributionModule.Options.OptionsBuilderImpl<TKey, TContribution> self() {
            return this;
         }

         @Override
         public BGMapContributionModule.Options<TKey, TContribution> build() {
            return new BGMapContributionModule.Options<TKey, TContribution>(this);
         }
      }

      protected Options(final BGMapContributionModule.Options.OptionsBuilder<TKey, TContribution, ?, ?> b) {
         super(b);
         this.keyType = b.keyType;
         this.consumer = b.consumer;
      }

      public static <TKey, TContribution> BGMapContributionModule.Options.OptionsBuilder<TKey, TContribution, ?, ?> BGMapContributionModuleBuilder() {
         return new BGMapContributionModule.Options.OptionsBuilderImpl<TKey, TContribution>();
      }
   }

   public BGMapContributionModule(final TOptions options) {
      super(options);
   }

   @Override
   protected MapBinder<TKey, TContribution> initContributionBinding() {
      if (options.bindingName != null) {
         return MapBinder.newMapBinder(binder(), options.keyType, options.contributionType, options.bindingName);
      }
      return MapBinder.newMapBinder(binder(), options.keyType, options.contributionType);
   }

   @Override
   protected void contribute(final MapBinder<TKey, TContribution> binding) {
      options.consumer.accept(binding);
   }
}
