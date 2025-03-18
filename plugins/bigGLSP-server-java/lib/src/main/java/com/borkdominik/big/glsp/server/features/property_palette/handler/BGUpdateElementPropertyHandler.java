/********************************************************************************
 * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.features.property_palette.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.borkdominik.big.glsp.server.core.handler.action.BGActionHandler;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyProviderRegistry;
import com.google.inject.Inject;

public class BGUpdateElementPropertyHandler extends BGActionHandler<BGUpdateElementPropertyAction> {

   private static final Logger LOG = LogManager.getLogger(BGUpdateElementPropertyHandler.class);

   @Inject
   protected BGModelState modelState;
   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;
   @Inject
   protected BGPropertyProviderRegistry registry;

   @Override
   protected List<Action> executeAction(final BGUpdateElementPropertyAction action) {
      return modelState.representation().get().<List<Action>> map(representation -> {
         var elementId = action.getElementId();

         var semanticElement = modelState.getElementIndex().getOrThrow(elementId);
         var mapper = registry.retrieve(representation, semanticElement.getClass());
         var handler = mapper.stream()
            .filter(p -> p.matches(action))
            .findFirst()
            .orElseThrow(
               () -> {
                  registry.printContent();
                  return new GLSPServerException(
                     String.format("[%s|%s] no matching handler found for property %s within %s",
                        representation,
                        semanticElement.getClass().getSimpleName(),
                        action.propertyId,
                        mapper.stream().flatMap(m -> m.getHandledProperties().stream()).toList()));
               });

         var command = handler.handle(action);
         modelState.execute(command);
         return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.OPERATION);
      }).orElse(none());
   }
}
