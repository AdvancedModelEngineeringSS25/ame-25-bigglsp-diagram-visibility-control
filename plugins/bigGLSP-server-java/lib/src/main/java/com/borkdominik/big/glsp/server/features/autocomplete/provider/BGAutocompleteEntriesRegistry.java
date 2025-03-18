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
package com.borkdominik.big.glsp.server.features.autocomplete.provider;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.glsp.server.types.EditorContext;

import com.borkdominik.big.glsp.server.lib.registry.BGRepresentationSingleKeyRegistry;
import com.google.inject.Inject;

public class BGAutocompleteEntriesRegistry
   extends BGRepresentationSingleKeyRegistry<Set<BGAutocompleteEntriesProvider>> {

   @Inject
   public BGAutocompleteEntriesRegistry(
      final Map<Enumerator, Set<BGAutocompleteEntriesProvider>> providers) {
      providers.entrySet().forEach(e -> {
         var representation = e.getKey();

         register(representation, e.getValue());
      });

      // printContent();
   }

   public Set<BGAutocompleteEntriesProvider> getSupported(final Enumerator key,
      final EditorContext context) {
      return get(key).map(providers -> providers.stream().filter(p -> p.handles(context)).collect(Collectors.toSet()))
         .orElse(Set.of());
   }

}
