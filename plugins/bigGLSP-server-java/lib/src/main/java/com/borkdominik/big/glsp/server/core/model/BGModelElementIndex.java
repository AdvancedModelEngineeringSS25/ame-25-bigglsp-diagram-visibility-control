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
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.types.GLSPServerException;

public interface BGModelElementIndex {
   default Optional<EObject> get(final String elementId) {
      return get(elementId, EObject.class);
   }

   <T extends EObject> Optional<T> get(String elementId, Class<T> type);

   default EObject getOrThrow(final String elementId) {
      return getOrThrow(elementId, EObject.class);
   }

   default <T extends EObject> T getOrThrow(final String elementId, final Class<T> type) {
      return GLSPServerException.getOrThrow(
         this.get(elementId),
         type,
         String.format("Could not find element with id '%s' and type '%s'", elementId,
            type.getSimpleName()));
   }

   default Optional<GModelElement> getGModel(final String elementId) {
      return getGModel(elementId, GModelElement.class);
   }

   <T extends GModelElement> Optional<T> getGModel(String elementId, Class<T> type);

   default GModelElement getGModelOrThrow(final String elementId) {
      return getGModelOrThrow(elementId, GModelElement.class);
   }

   default <T extends GModelElement> T getGModelOrThrow(final String elementId, final Class<T> type) {
      return GLSPServerException.getOrThrow(
         getGModel(elementId),
         type,
         String.format("Could not find gmodel for element with id '%s' and type '%s'", elementId,
            type.getSimpleName()));
   }

}
