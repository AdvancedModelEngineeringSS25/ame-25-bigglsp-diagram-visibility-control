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
package com.borkdominik.big.glsp.server.elements.configuration;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class BGElementConfigurationAccessor {
   @Inject
   protected BGElementConfigurationRegistry registry;

   protected Enumerator representation;

   @Inject
   public BGElementConfigurationAccessor(@Assisted final Enumerator representation) {
      this.representation = representation;
   }

   public BGElementConfigurationRegistry registry() {
      return registry;
   }

   public Optional<BGElementConfiguration> get(final Class<? extends EObject> key) {
      return registry.get(representation, key);
   }

   public Boolean has(final BGTypeProvider key) {
      return registry.has(representation, Set.of(key));
   }

   public Boolean has(final Class<? extends EObject> key) {
      return registry.has(representation, key);
   }

   public Boolean has(final Set<BGTypeProvider> keys) {
      return registry.has(representation, keys);
   }

   public Set<BGElementConfiguration> existing(final Set<BGTypeProvider> keys) {
      return registry.existing(representation, keys);
   }

   public BGElementConfiguration retrieve(final BGTypeProvider key) {
      return registry.retrieve(representation, key);
   }

   public BGElementConfiguration retrieve(final Class<? extends EObject> key) {
      return registry.retrieve(representation, key);
   }

   public List<String> existingConfigurationTypeIds(final Set<BGTypeProvider> keys) {
      return existing(keys).stream()
         .flatMap(c -> c.getHandledElementTypes().stream().map(t -> t.prefix(representation)))
         .collect(Collectors.toList());
   }

   /*-
   public List<String> existingConfigurationTypeIds(final Set<String> typeIds,
      final Set<Class<? extends EObject>> configurations) {
      var ids = new ArrayList<>(typeIds);
      ids.addAll(existing(configurations).stream()
         .flatMap(c -> c.typeIds().stream())
         .collect(Collectors.toList()));
      return ids;
   }
   */
}
