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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

public interface BGTypeProvider {
   String typeId();

   Collection<Class<? extends EObject>> handledElements();

   default boolean canHandle(final Class<? extends EObject> element) {
      return handledElements().stream().anyMatch(e -> {
         if (e.isInterface()) {
            var interfaces = List.of(element.getInterfaces());
            return interfaces.contains(e);
         }

         return e.equals(element);
      });
   }

   default boolean isSame(final Enumerator representation, final String typeId) {
      return this.prefix(representation).equals(typeId);
   }

   default String prefix(final String prefix) {
      return String.format("%s__%s", prefix, typeId());
   }

   default String prefix(final Enumerator representation) {
      return prefix(representation.getName());
   }

}
