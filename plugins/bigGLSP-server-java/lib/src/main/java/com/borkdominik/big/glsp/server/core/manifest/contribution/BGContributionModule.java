/******************************************************************************** * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.manifest.contribution;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;

public abstract class BGContributionModule<TContribution, TBinding, TOptions extends BGContributionModule.Options<TContribution>> extends AbstractModule {

   public static class Options<TContribution> {
      public TypeLiteral<TContribution> contributionType;
      public Named bindingName;

      public static abstract class OptionsBuilder<TContribution, C extends BGContributionModule.Options<TContribution>, B extends BGContributionModule.Options.OptionsBuilder<TContribution, C, B>> {
         private TypeLiteral<TContribution> contributionType;
         private Named bindingName;

         public B contributionType(final TypeLiteral<TContribution> contributionType) {
            this.contributionType = contributionType;
            return self();
         }

         public B bindingName(final Named bindingName) {
            this.bindingName = bindingName;
            return self();
         }

         protected abstract B self();

         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGContributionModule.Options.OptionsBuilder(contributionType=" + this.contributionType + ", bindingName=" + this.bindingName + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution> extends BGContributionModule.Options.OptionsBuilder<TContribution, BGContributionModule.Options<TContribution>, BGContributionModule.Options.OptionsBuilderImpl<TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGContributionModule.Options.OptionsBuilderImpl<TContribution> self() {
            return this;
         }

         @Override
         public BGContributionModule.Options<TContribution> build() {
            return new BGContributionModule.Options<TContribution>(this);
         }
      }

      protected Options(final BGContributionModule.Options.OptionsBuilder<TContribution, ?, ?> b) {
         this.contributionType = b.contributionType;
         this.bindingName = b.bindingName;
      }

      public static <TContribution> BGContributionModule.Options.OptionsBuilder<TContribution, ?, ?> BGContributionModuleBuilder() {
         return new BGContributionModule.Options.OptionsBuilderImpl<TContribution>();
      }
   }

   protected TOptions options;

   public BGContributionModule(final TOptions options) {
      this.options = options;
   }

   protected boolean isDefinition() {
      return false;
   }

   @Override
   protected void configure() {
      super.configure();
      onBeforeContributionBinding();
      var binding = initContributionBinding();
      if (!isDefinition()) {
         onBeforeContribute();
         contribute(binding);
      }
   }

   protected void onBeforeContributionBinding() {
   }

   protected abstract TBinding initContributionBinding();

   protected void onBeforeContribute() {
   }

   protected abstract void contribute(TBinding binding);

   protected void bindAdditionals(final TBinding binding) {
   }
}
