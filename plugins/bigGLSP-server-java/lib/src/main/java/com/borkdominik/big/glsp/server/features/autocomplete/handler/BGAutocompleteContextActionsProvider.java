/********************************************************************************
 * Copyright (c) 2021 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.features.autocomplete.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.types.EditorContext;

import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.features.autocomplete.constants.BGAutocompleteConstants;
import com.borkdominik.big.glsp.server.features.autocomplete.provider.BGAutocompleteEntriesRegistry;
import com.google.inject.Inject;

public class BGAutocompleteContextActionsProvider implements ContextActionsProvider {
   private static Logger LOGGER = LogManager.getLogger(BGAutocompleteContextActionsProvider.class.getSimpleName());

   @Inject
   protected BGModelState modelState;

   @Inject
   protected BGAutocompleteEntriesRegistry registry;

   @Override
   public String getContextId() { return BGAutocompleteConstants.CONTEXT_ID; }

   @Override
   public List<? extends LabeledAction> getActions(final EditorContext editorContext) {
      return modelState.representation().get().map(representation -> {

         var providers = registry.getSupported(representation, editorContext);

         return providers.stream().flatMap(p -> p.process(editorContext).stream()).collect(Collectors.toList());
      }).orElse(List.of());
   }

}
