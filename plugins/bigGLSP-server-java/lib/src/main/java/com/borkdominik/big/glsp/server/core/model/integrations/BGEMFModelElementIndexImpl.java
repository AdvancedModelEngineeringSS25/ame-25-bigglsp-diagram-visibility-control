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
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;

import com.borkdominik.big.glsp.server.core.model.BGEMFModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;

import jakarta.inject.Inject;

public class BGEMFModelElementIndexImpl extends BGGModelElementIndexImpl implements BGEMFModelElementIndex {

   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   public <T extends EObject> Optional<T> get(final String elementId, final Class<T> type) {
      return getSemantic(elementId, type);
   }

   @Override
   public <T extends EObject> Optional<T> getSemantic(final String elementId, final Class<T> type) {
      return modelState.getIndex().getEObject(elementId, type);
   }

   @Override
   public <T extends EObject> T getSemanticOrThrow(final EObject element, final Class<T> type) {
      return getSemanticOrThrow(idGenerator.getOrCreateId(element), type);
   }

   @Override
   public Optional<NotationElement> getNotation(final EObject element) {
      return modelState.getIndex().getNotation(element);
   }

   @Override
   public <T extends NotationElement> Optional<T> getNotation(final String elementId, final Class<T> type) {
      return modelState.getIndex().getNotation(elementId, type);
   }

   @Override
   public boolean hasNotation(final EObject element) {
      return modelState.getIndex().getNotation(element).isPresent();
   }

}
