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
package com.borkdominik.big.glsp.server.core.model.integrations;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;

import com.borkdominik.big.glsp.server.core.model.BGModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGModelState;

import jakarta.inject.Inject;

public class BGGModelElementIndexImpl implements BGModelElementIndex {

   @Inject
   protected BGModelState modelState;

   @Override
   public <T extends EObject> Optional<T> get(final String elementId, final Class<T> type) {
      if (elementId == null) {
         return Optional.empty();
      }

      var element = this.modelState.getIndex().get(elementId);
      if (element.isPresent() && type.isInstance(element.get())) {
         return Optional.of(type.cast(element.get()));
      }
      return Optional.empty();
   }

   @Override
   public <T extends GModelElement> Optional<T> getGModel(final String elementId, final Class<T> type) {
      return modelState.getIndex().getByClass(elementId, type);
   }
}
