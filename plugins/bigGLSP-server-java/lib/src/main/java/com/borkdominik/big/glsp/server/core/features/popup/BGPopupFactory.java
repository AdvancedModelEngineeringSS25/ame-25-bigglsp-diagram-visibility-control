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
package com.borkdominik.big.glsp.server.core.features.popup;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.graph.GHtmlRoot;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.features.popup.PopupModelFactory;
import org.eclipse.glsp.server.features.popup.RequestPopupModelAction;

import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;

import jakarta.inject.Inject;

public class BGPopupFactory implements PopupModelFactory {
   private static Logger LOGGER = LogManager.getLogger(BGPopupFactory.class.getSimpleName());

   @Inject
   protected BGModelState modelState;
   @Inject
   protected BGPopupMapperRegistry registry;

   @Override
   public Optional<GHtmlRoot> createPopupModel(final GModelElement element, final RequestPopupModelAction action) {
      if (element != null) {
         var representation = modelState.representation().getUnsafe();

         return modelState.getElementIndex().get(element.getId()).flatMap(semanticElement -> {
            var handler = registry.get(BGRegistryKey.of(representation, semanticElement.getClass()));

            if (handler.isEmpty()) {
               LOGGER.debug("No PopupMapper found for element: " + semanticElement.getClass().getSimpleName());
            }

            return handler.flatMap(h -> h.map(semanticElement, action));
         });

      }
      return Optional.empty();
   }

}
