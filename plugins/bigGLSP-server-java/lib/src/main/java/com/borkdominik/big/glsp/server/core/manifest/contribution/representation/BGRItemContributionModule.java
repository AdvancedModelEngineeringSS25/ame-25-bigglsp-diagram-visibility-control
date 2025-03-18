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
package com.borkdominik.big.glsp.server.core.manifest.contribution.representation;

import org.eclipse.emf.common.util.Enumerator;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class BGRItemContributionModule<TContribution> extends BGRContributionModule<TContribution, TContribution, BGRItemContributionModule.Options<TContribution>> {

   public static class Options<TContribution> extends BGRContributionModule.Options<TContribution> {
      public TypeLiteral<? extends TContribution> concreteType;

      public static abstract class OptionsBuilder<TContribution, C extends BGRItemContributionModule.Options<TContribution>, B extends BGRItemContributionModule.Options.OptionsBuilder<TContribution, C, B>> extends BGRContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private TypeLiteral<? extends TContribution> concreteType;

         public B concreteType(final TypeLiteral<? extends TContribution> concreteType) {
            this.concreteType = concreteType;
            return self();
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGRItemContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", concreteType=" + this.concreteType + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution> extends BGRItemContributionModule.Options.OptionsBuilder<TContribution, BGRItemContributionModule.Options<TContribution>, BGRItemContributionModule.Options.OptionsBuilderImpl<TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGRItemContributionModule.Options.OptionsBuilderImpl<TContribution> self() {
            return this;
         }

         @Override
         public BGRItemContributionModule.Options<TContribution> build() {
            return new BGRItemContributionModule.Options<TContribution>(this);
         }
      }

      protected Options(final BGRItemContributionModule.Options.OptionsBuilder<TContribution, ?, ?> b) {
         super(b);
         this.concreteType = b.concreteType;
      }

      public static <TContribution> BGRItemContributionModule.Options.OptionsBuilder<TContribution, ?, ?> BGRItemContributionModuleBuilder() {
         return new BGRItemContributionModule.Options.OptionsBuilderImpl<TContribution>();
      }
   }

   public BGRItemContributionModule(final Options<TContribution> options) {
      super(options);
   }

   @Override
   protected boolean isDefinition() {
      return super.isDefinition() || options.concreteType == null;
   }

   @Override
   protected MapBinder<Enumerator, TContribution> initContributionBinding() {
      if (options.bindingName != null) {
         return MapBinder.newMapBinder(binder(), new TypeLiteral<Enumerator>() {
         }, options.contributionType, options.bindingName);
      }
      return MapBinder.newMapBinder(binder(), new TypeLiteral<Enumerator>() {
      }, options.contributionType);
   }

   @Override
   protected void contribute(final MapBinder<Enumerator, TContribution> defined) {
      defined.addBinding(options.manifest.representation()).to(options.concreteType);
   }
}
