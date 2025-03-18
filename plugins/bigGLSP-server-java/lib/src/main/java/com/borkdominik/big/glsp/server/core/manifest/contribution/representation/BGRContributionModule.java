/******************************************************************************** * Copyright (c) 2022 borkdominik and others.
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
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.BGContributionModule;
import com.google.inject.multibindings.MapBinder;

public abstract class BGRContributionModule<TContribution, TBinding, TOptions extends BGRContributionModule.Options<TContribution>> extends BGContributionModule<TContribution, MapBinder<Enumerator, TBinding>, TOptions> {

   public static class Options<TContribution> extends BGContributionModule.Options<TContribution> {
      public final BGRepresentationManifest manifest;

      public static abstract class OptionsBuilder<TContribution, C extends BGRContributionModule.Options<TContribution>, B extends BGRContributionModule.Options.OptionsBuilder<TContribution, C, B>> extends BGContributionModule.Options.OptionsBuilder<TContribution, C, B> {
         private BGRepresentationManifest manifest;

         public B manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return self();
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "BGRContributionModule.Options.OptionsBuilder(super=" + super.toString() + ", manifest=" + this.manifest + ")";
         }
      }

      private static final class OptionsBuilderImpl<TContribution> extends BGRContributionModule.Options.OptionsBuilder<TContribution, BGRContributionModule.Options<TContribution>, BGRContributionModule.Options.OptionsBuilderImpl<TContribution>> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected BGRContributionModule.Options.OptionsBuilderImpl<TContribution> self() {
            return this;
         }

         @Override
         public BGRContributionModule.Options<TContribution> build() {
            return new BGRContributionModule.Options<TContribution>(this);
         }
      }

      protected Options(final BGRContributionModule.Options.OptionsBuilder<TContribution, ?, ?> b) {
         super(b);
         this.manifest = b.manifest;
      }

      public static <TContribution> BGRContributionModule.Options.OptionsBuilder<TContribution, ?, ?> BGRContributionModuleBuilder() {
         return new BGRContributionModule.Options.OptionsBuilderImpl<TContribution>();
      }
   }

   public BGRContributionModule(final TOptions options) {
      super(options);
   }

   @Override
   protected boolean isDefinition() {
      return options.manifest == null;
   }
}
