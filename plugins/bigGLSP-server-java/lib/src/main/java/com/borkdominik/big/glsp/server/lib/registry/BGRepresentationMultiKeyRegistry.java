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
package com.borkdominik.big.glsp.server.lib.registry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;

public abstract class BGRepresentationMultiKeyRegistry<K, V> extends BGBaseRegistry<BGRegistryKey<K>, V> {
   @Override
   protected String deriveKey(final BGRegistryKey<K> key) {
      var representation = key.representation;

      return representation.getName() + ":" + key.key;
   }

   public Boolean has(final Enumerator representation,
      final K key) {
      return has(representation, Set.of(key));
   }

   public Boolean has(final Enumerator representation,
      final Set<K> keys) {
      return keys.stream().allMatch(c -> this.get(representation, c).isPresent());
   }

   @SuppressWarnings("unchecked")
   public Set<V> existing(final Enumerator representation,
      final Set<K> keys) {
      return (Set<V>) keys.stream()
         .filter(c -> has(representation, Set.of(c)))
         .map(c -> retrieveTyped(representation, c))
         .collect(Collectors.toSet());
   }

   public Optional<V> get(final Enumerator representation, final K key) {
      return get(BGRegistryKey.of(representation, key));
   }

   public V retrieve(final Enumerator representation, final K key) {
      return retrieve(BGRegistryKey.of(representation, key));
   }

   public V retrieveOrDefault(final Enumerator representation, final K key) {
      return retrieveOrDefault(BGRegistryKey.of(representation, key));
   }

   public V retrieveOrElse(final Enumerator representation, final K key, final V value) {
      return retrieveOrElse(BGRegistryKey.of(representation, key), value);
   }

   public <TValue extends V> TValue retrieveTyped(
      final Enumerator representation,
      final K key) {
      return (TValue) retrieve(representation, key);
   }

   public <TValue extends V> TValue retrieveTyped(
      final Enumerator representation,
      final K key,
      final Class<TValue> type) {
      return (TValue) retrieve(representation, key);
   }

   public Set<V> getAll(final Enumerator representation) {
      return keys().stream().filter(k -> k.representation.equals(representation))
         .map(k -> retrieve(k))
         .collect(Collectors.toSet());
   }
}
