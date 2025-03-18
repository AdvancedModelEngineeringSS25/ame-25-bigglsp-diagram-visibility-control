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

import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.Enumerator;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;

public class BGRSetContributionModule<TContribution, TOptions extends BGRSetContributionModule.Options<TContribution>> extends BGRContributionModule<TContribution, Set<TContribution>, TOptions> {
   protected TypeLiteral<Set<TContribution>> contributionSetType;

   public static class Options<TContribution> extends BGRContributionModule.Options<TContribution> {
      public Set<TypeLiteral<? extends TContribution>> concretes;
      public Consumer<Multibinder<TContribution>> consumer;

      public static abstract class OptionsBuilder<TContribution, C extends BGRSetContributionModule.Options<TContribution>, B extends BGRSetContributionModule.Options.OptionsBuilder<TContribution, C, B>> extends BGRContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private Set<TypeLiteral<? extends TContribution>> concretes;
         private Consumer<Multibinder<TContribution>> consumer;

         public B concretes(final Set<TypeLiteral<? extends TContribution>> concretes) {
            this.concretes = concretes;
            return self();
         }

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
            return "BGRSetContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", concretes=" + this.concretes + ", consumer=" + this.consumer + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution> extends BGRSetContributionModule.Options.OptionsBuilder<TContribution, BGRSetContributionModule.Options<TContribution>, BGRSetContributionModule.Options.OptionsBuilderImpl<TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGRSetContributionModule.Options.OptionsBuilderImpl<TContribution> self() {
            return this;
         }

         @Override
         public BGRSetContributionModule.Options<TContribution> build() {
            return new BGRSetContributionModule.Options<TContribution>(this);
         }
      }

      protected Options(final BGRSetContributionModule.Options.OptionsBuilder<TContribution, ?, ?> b) {
         super(b);
         this.concretes = b.concretes;
         this.consumer = b.consumer;
      }

      public static <TContribution> BGRSetContributionModule.Options.OptionsBuilder<TContribution, ?, ?> BGRSetContributionModuleBuilder() {
         return new BGRSetContributionModule.Options.OptionsBuilderImpl<TContribution>();
      }
   }

   public BGRSetContributionModule(final TOptions options) {
      super(options);
   }

   @Override
   protected boolean isDefinition() {
      return super.isDefinition() || (options.concretes == null && options.consumer == null);
   }

   @Override
   protected void onBeforeContributionBinding() {
      super.onBeforeContributionBinding();
      this.contributionSetType = TypeLiteralUtils.parameterizedType(Set.class, this.options.contributionType);
   }

   @Override
   protected MapBinder<Enumerator, Set<TContribution>> initContributionBinding() {
      if (options.bindingName != null) {
         return MapBinder.newMapBinder(binder(), new TypeLiteral<Enumerator>() {
         }, contributionSetType, options.bindingName);
      }
      return MapBinder.newMapBinder(binder(), new TypeLiteral<Enumerator>() {
      }, contributionSetType);
   }

   @Override
   protected void contribute(final MapBinder<Enumerator, Set<TContribution>> defined) {
      var binder = binder();
      var multibinder = Multibinder.newSetBinder(binder, this.options.contributionType, options.manifest.idNamed());
      if (options.concretes != null) {
         options.concretes.forEach(concrete -> {
            multibinder.addBinding().to(concrete);
         });
      }
      if (options.consumer != null) {
         options.consumer.accept(multibinder);
      }
      var key = Key.get(contributionSetType, options.manifest.idNamed());
      defined.addBinding(options.manifest.representation()).to(key);
   }
}
