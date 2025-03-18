/******************************************************************************** * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.features.autocomplete;

import java.util.Set;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRSetContributionModule;
import com.borkdominik.big.glsp.server.features.autocomplete.provider.BGAutocompleteEntriesProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;

public class BGAutocompleteContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Set<Class<? extends BGAutocompleteEntriesProvider>> providers;

      Options(final BGRepresentationManifest manifest, final Set<Class<? extends BGAutocompleteEntriesProvider>> providers) {
         this.manifest = manifest;
         this.providers = providers;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Set<Class<? extends BGAutocompleteEntriesProvider>> providers;

         OptionsBuilder() {
         }

         public BGAutocompleteContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGAutocompleteContribution.Options.OptionsBuilder providers(final Set<Class<? extends BGAutocompleteEntriesProvider>> providers) {
            this.providers = providers;
            return this;
         }

         public BGAutocompleteContribution.Options build() {
            return new BGAutocompleteContribution.Options(this.manifest, this.providers);
         }

         @Override
         public java.lang.String toString() {
            return "BGAutocompleteContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", providers=" + this.providers + ")";
         }
      }

      public static BGAutocompleteContribution.Options.OptionsBuilder builder() {
         return new BGAutocompleteContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Set<Class<? extends BGAutocompleteEntriesProvider>> getProviders() {
         return this.providers;
      }
   }

   protected final Options options;

   public BGAutocompleteContribution() {
      this(Options.builder().build());
   }

   public BGAutocompleteContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGRSetContributionModule<>(BGRSetContributionModule.Options.<BGAutocompleteEntriesProvider>BGRSetContributionModuleBuilder().manifest(options.manifest).contributionType(TypeLiteralUtils.of(BGAutocompleteEntriesProvider.class)).concretes(TypeLiteralUtils.ofs(options.providers)).build()));
   }
}
