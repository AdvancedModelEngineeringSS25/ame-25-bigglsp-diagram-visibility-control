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
package com.borkdominik.big.glsp.server.core.model;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.types.GLSPServerException;

public interface BGEMFModelElementIndex extends BGModelElementIndex {
   default Optional<EObject> getSemantic(final String elementId) {
      return getSemantic(elementId, EObject.class);
   }

   <T extends EObject> Optional<T> getSemantic(String elementId, Class<T> type);

   <T extends EObject> T getSemanticOrThrow(EObject element, Class<T> type);

   default EObject getSemanticOrThrow(final String elementId) {
      return getSemanticOrThrow(elementId, EObject.class);
   }

   default <T extends EObject> T getSemanticOrThrow(final String elementId, final Class<T> type) {
      return GLSPServerException.getOrThrow(
         getSemantic(elementId),
         type,
         String.format("Could not find semantic for element with id '%s' and type '%s'", elementId,
            type.getSimpleName()));
   }

   boolean hasNotation(EObject element);

   Optional<NotationElement> getNotation(final EObject element);

   default NotationElement getNotationOrThrow(final EObject element) {
      return getNotation(element).orElseThrow();
   }

   default Optional<NotationElement> getNotation(final String elementId) {
      return getNotation(elementId, NotationElement.class);
   }

   <T extends NotationElement> Optional<T> getNotation(String elementId, Class<T> type);

   default NotationElement getNotationOrThrow(final String elementId) {
      return getNotationOrThrow(elementId, NotationElement.class);
   }

   default <T extends NotationElement> T getNotationOrThrow(final String elementId, final Class<T> type) {
      return GLSPServerException.getOrThrow(
         getNotation(elementId),
         type,
         String.format("Could not find notation for element with id '%s' and type '%s'", elementId,
            type.getSimpleName()));
   }
}
