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
package com.borkdominik.big.glsp.server.core.handler.action;

import java.util.function.Consumer;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.ClientSessionModule;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import com.borkdominik.big.glsp.server.core.manifest.BGContributionManifest;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.manifest.contribution.BGSetContributionModule;
import com.borkdominik.big.glsp.server.core.manifest.contribution.representation.BGRSetContributionModule;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class BGActionFeatureContribution extends BGContributionManifest {

   public static class Options {
      protected BGRepresentationManifest manifest;
      protected Consumer<Multibinder<ActionHandler>> handlers;
      protected Consumer<Multibinder<ActionHandler>> overrideHandlers;
      protected Consumer<Multibinder<Action>> clientActions;
      protected Consumer<Multibinder<ContextActionsProvider>> contextActionsProviders;

      Options(final BGRepresentationManifest manifest, final Consumer<Multibinder<ActionHandler>> handlers, final Consumer<Multibinder<ActionHandler>> overrideHandlers, final Consumer<Multibinder<Action>> clientActions, final Consumer<Multibinder<ContextActionsProvider>> contextActionsProviders) {
         this.manifest = manifest;
         this.handlers = handlers;
         this.overrideHandlers = overrideHandlers;
         this.clientActions = clientActions;
         this.contextActionsProviders = contextActionsProviders;
      }

      public static class OptionsBuilder {
         private BGRepresentationManifest manifest;
         private Consumer<Multibinder<ActionHandler>> handlers;
         private Consumer<Multibinder<ActionHandler>> overrideHandlers;
         private Consumer<Multibinder<Action>> clientActions;
         private Consumer<Multibinder<ContextActionsProvider>> contextActionsProviders;

         OptionsBuilder() {
         }

         public BGActionFeatureContribution.Options.OptionsBuilder manifest(final BGRepresentationManifest manifest) {
            this.manifest = manifest;
            return this;
         }

         public BGActionFeatureContribution.Options.OptionsBuilder handlers(final Consumer<Multibinder<ActionHandler>> handlers) {
            this.handlers = handlers;
            return this;
         }

         public BGActionFeatureContribution.Options.OptionsBuilder overrideHandlers(final Consumer<Multibinder<ActionHandler>> overrideHandlers) {
            this.overrideHandlers = overrideHandlers;
            return this;
         }

         public BGActionFeatureContribution.Options.OptionsBuilder clientActions(final Consumer<Multibinder<Action>> clientActions) {
            this.clientActions = clientActions;
            return this;
         }

         public BGActionFeatureContribution.Options.OptionsBuilder contextActionsProviders(final Consumer<Multibinder<ContextActionsProvider>> contextActionsProviders) {
            this.contextActionsProviders = contextActionsProviders;
            return this;
         }

         public BGActionFeatureContribution.Options build() {
            return new BGActionFeatureContribution.Options(this.manifest, this.handlers, this.overrideHandlers, this.clientActions, this.contextActionsProviders);
         }

         @Override
         public java.lang.String toString() {
            return "BGActionFeatureContribution.Options.OptionsBuilder(manifest=" + this.manifest + ", handlers=" + this.handlers + ", overrideHandlers=" + this.overrideHandlers + ", clientActions=" + this.clientActions + ", contextActionsProviders=" + this.contextActionsProviders + ")";
         }
      }

      public static BGActionFeatureContribution.Options.OptionsBuilder builder() {
         return new BGActionFeatureContribution.Options.OptionsBuilder();
      }

      public BGRepresentationManifest getManifest() {
         return this.manifest;
      }

      public Consumer<Multibinder<ActionHandler>> getHandlers() {
         return this.handlers;
      }

      public Consumer<Multibinder<ActionHandler>> getOverrideHandlers() {
         return this.overrideHandlers;
      }

      public Consumer<Multibinder<Action>> getClientActions() {
         return this.clientActions;
      }

      public Consumer<Multibinder<ContextActionsProvider>> getContextActionsProviders() {
         return this.contextActionsProviders;
      }
   }

   protected final Options options;

   public BGActionFeatureContribution(final Options options) {
      this.options = options;
   }

   @Override
   protected void configure() {
      super.configure();
      install(new BGSetContributionModule<>(BGSetContributionModule.Options.<ActionHandler>BGSetContributionModuleBuilder().contributionType(TypeLiteralUtils.of(ActionHandler.class)).consumer(this.options.handlers).build()));
      install(new BGRSetContributionModule<>(BGRSetContributionModule.Options.<ActionHandler>BGRSetContributionModuleBuilder().manifest(this.options.manifest).contributionType(TypeLiteralUtils.of(ActionHandler.class)).consumer(this.options.overrideHandlers).build()));
      install(new BGSetContributionModule<>(BGSetContributionModule.Options.<Action>BGSetContributionModuleBuilder().contributionType(TypeLiteralUtils.of(Action.class)).consumer(this.options.clientActions).bindingName(Names.named(ClientSessionModule.CLIENT_ACTIONS)).build()));
      install(new BGSetContributionModule<>(BGSetContributionModule.Options.<ContextActionsProvider>BGSetContributionModuleBuilder().contributionType(TypeLiteralUtils.of(ContextActionsProvider.class)).consumer(this.options.contextActionsProviders).build()));
   }
}
