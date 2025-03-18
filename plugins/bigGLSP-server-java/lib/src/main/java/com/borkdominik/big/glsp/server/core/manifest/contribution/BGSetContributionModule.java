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
import com.google.inject.multibindings.Multibinder;

public final class BGSetContributionModule<TContribution, TOptions extends BGSetContributionModule.Options<TContribution>> extends BGContributionModule<TContribution, Multibinder<TContribution>, TOptions> {

   public static class Options<TContribution> extends BGContributionModule.Options<TContribution> {
      public Consumer<Multibinder<TContribution>> consumer;

      public static abstract class OptionsBuilder<TContribution, C extends BGSetContributionModule.Options<TContribution>, B extends BGSetContributionModule.Options.OptionsBuilder<TContribution, C, B>> extends BGContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private Consumer<Multibinder<TContribution>> consumer;

         public B consumer(final Consumer<Multibinder<TContribution>> consumer) {
            this.consumer = consumer;
            return self();
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGSetContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", consumer=" + this.consumer + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution> extends BGSetContributionModule.Options.OptionsBuilder<TContribution, BGSetContributionModule.Options<TContribution>, BGSetContributionModule.Options.OptionsBuilderImpl<TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGSetContributionModule.Options.OptionsBuilderImpl<TContribution> self() {
            return this;
         }

         @Override
         public BGSetContributionModule.Options<TContribution> build() {
            return new BGSetContributionModule.Options<TContribution>(this);
         }
      }

      protected Options(final BGSetContributionModule.Options.OptionsBuilder<TContribution, ?, ?> b) {
         super(b);
         this.consumer = b.consumer;
      }

      public static <TContribution> BGSetContributionModule.Options.OptionsBuilder<TContribution, ?, ?> BGSetContributionModuleBuilder() {
         return new BGSetContributionModule.Options.OptionsBuilderImpl<TContribution>();
      }
   }

   public BGSetContributionModule(final TOptions options) {
      super(options);
   }

   @Override
   protected boolean isDefinition() {
      return super.isDefinition() || options.consumer == null;
   }

   @Override
   protected Multibinder<TContribution> initContributionBinding() {
      if (options.bindingName != null) {
         return Multibinder.newSetBinder(binder(), options.contributionType, options.bindingName);
      }
      return Multibinder.newSetBinder(binder(), options.contributionType);
   }

   @Override
   protected void contribute(final Multibinder<TContribution> binding) {
      options.consumer.accept(binding);
   }
}
