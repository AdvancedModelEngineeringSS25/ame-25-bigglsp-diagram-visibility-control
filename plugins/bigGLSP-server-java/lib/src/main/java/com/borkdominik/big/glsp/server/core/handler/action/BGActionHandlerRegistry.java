/********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.handler.action;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.internal.actions.DefaultActionHandlerRegistry;

import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.google.inject.Inject;

public class BGActionHandlerRegistry extends DefaultActionHandlerRegistry {
   protected BGModelRepresentation modelStateRepresentation;
   protected BGOverrideActionHandlerRegistry overrideRegistry;

   @Inject
   public BGActionHandlerRegistry(final Set<ActionHandler> handlers,
      final BGModelRepresentation modelStateRepresentation,
      final BGOverrideActionHandlerRegistry overrideRegsitry) {
      super(handlers);

      this.modelStateRepresentation = modelStateRepresentation;
      this.overrideRegistry = overrideRegsitry;
   }

   @Override
   public List<ActionHandler> get(final Class<? extends Action> key) {
      var actionHandler = this.modelStateRepresentation.get().flatMap(representation -> {
         var overrideKey = BGRegistryKey.<Class<? extends Action>> of(representation,
            key);

         if (overrideRegistry.hasKey(overrideKey)) {
            return overrideRegistry.get(overrideKey);
         }

         return Optional.<List<ActionHandler>> empty();
      });

      return actionHandler.orElseGet(() -> super.get(key));
   }
}
