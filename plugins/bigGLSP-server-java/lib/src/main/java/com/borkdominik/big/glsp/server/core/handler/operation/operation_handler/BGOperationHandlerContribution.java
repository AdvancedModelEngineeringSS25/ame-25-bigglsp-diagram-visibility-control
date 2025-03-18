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
package com.borkdominik.big.glsp.server.core.handler.operation.operation_handler;

import org.eclipse.glsp.server.operations.OperationHandler;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.BGSetContributionModule;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRSetContributionModule;
import com.google.inject.TypeLiteral;

public class BGOperationHandlerContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Class<OperationHandler<?>> handlers;
      protected Class<OperationHandler<?>> overrideHandlers;

      Options(final BGRepresentationManifest manifest, final Class<OperationHandler<?>> handlers, final Class<OperationHandler<?>> overrideHandlers) {
         this.manifest = manifest;
         this.handlers = handlers;
         this.overrideHandlers = overrideHandlers;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Class<OperationHandler<?>> handlers;
         private Class<OperationHandler<?>> overrideHandlers;

         OptionsBuilder() {
         }

         public BGOperationHandlerContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGOperationHandlerContribution.Options.OptionsBuilder handlers(final Class<OperationHandler<?>> handlers) {
            this.handlers = handlers;
            return this;
         }

         public BGOperationHandlerContribution.Options.OptionsBuilder overrideHandlers(final Class<OperationHandler<?>> overrideHandlers) {
            this.overrideHandlers = overrideHandlers;
            return this;
         }

         public BGOperationHandlerContribution.Options build() {
            return new BGOperationHandlerContribution.Options(this.manifest, this.handlers, this.overrideHandlers);
         }

         @Override
         public java.lang.String toString() {
            return "BGOperationHandlerContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", handlers=" + this.handlers + ", overrideHandlers=" + this.overrideHandlers + ")";
         }
      }

      public static BGOperationHandlerContribution.Options.OptionsBuilder builder() {
         return new BGOperationHandlerContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Class<OperationHandler<?>> getHandlers() {
         return this.handlers;
      }

      public Class<OperationHandler<?>> getOverrideHandlers() {
         return this.overrideHandlers;
      }
   }

   protected final Options options;

   public BGOperationHandlerContribution() {
      this(Options.builder().build());
   }

   public BGOperationHandlerContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      if (options.handlers != null) {
         install(new BGSetContributionModule<>(BGSetContributionModule.Options.<OperationHandler<?>>BGSetContributionModuleBuilder().contributionType(new TypeLiteral<OperationHandler<?>>() {
         }).consumer(contribution -> contribution.addBinding().to(options.handlers)).build()));
      }
      install(new BGRSetContributionModule<>(BGRSetContributionModule.Options.<OperationHandler<?>>BGRSetContributionModuleBuilder().manifest(this.options.manifest).contributionType(new TypeLiteral<OperationHandler<?>>() {
      }).consumer(contribution -> contribution.addBinding().to(options.overrideHandlers)).build()));
   }
}
