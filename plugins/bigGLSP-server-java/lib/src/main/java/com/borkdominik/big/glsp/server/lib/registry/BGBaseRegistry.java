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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.internal.registry.MapRegistry;
import org.eclipse.glsp.server.registry.Registry;

public abstract class BGBaseRegistry<K, V> implements Registry<K, V> {
   protected final Map<String, K> keys = new HashMap<>();
   protected final MapRegistry<String, V> registry = new MapRegistry<>();
   protected final Map<K, V> defaultValues = new HashMap<>();

   protected abstract String deriveKey(final K key);

   @Override
   public boolean register(final K key,
      final V value) {
      if (hasKey(key)) {
         var oldValue = this.retrieve(key);

         if (!oldValue.getClass().isAssignableFrom(value.getClass())) {
            printContent();
            throw new IllegalArgumentException(
               String.format("[%s] Key %s already exists with value %s. Tried to register with value %s",
                  this.getClass().getSimpleName(), deriveKey(key),
                  get(key).map(v -> v.getClass().getName()).orElse("Unknown"),
                  value.getClass().getName()));
         }

         deregister(key);
      }
      keys.put(deriveKey(key), key);
      return registry.register(deriveKey(key), value);
   }

   @Override
   public boolean deregister(final K key) {
      keys.remove(deriveKey(key));
      return registry.deregister(deriveKey(key));
   }

   @Override
   public boolean hasKey(final K key) {
      return registry.hasKey(deriveKey(key));
   }

   @Override
   public Optional<V> get(final K key) {
      return registry.get(deriveKey(key));
   }

   public Optional<V> getOrDefault(final K key) {
      return registry.get(deriveKey(key))
         .or(() -> Optional.ofNullable(defaultValues.get(key)));
   }

   public V retrieve(final K key) {
      return this.get(key).orElseThrow(
         () -> {
            printContent();
            return new NoSuchElementException(
               String.format("[%s] No value found for representation %s and key %s",
                  this.getClass().getSimpleName(), key.toString(), deriveKey(key)));
         });
   }

   public V retrieveOrDefault(final K key) {
      return retrieveOrElse(key, defaultValues.get(key));
   }

   public V retrieveOrElse(final K key, final V other) {
      var value = this.get(key).orElse(other);

      if (value == null) {
         printContent();
         new NoSuchElementException(
            String.format(
               "[%s] No value found for representation %s and key %s and provided default value was also null.",
               this.getClass().getSimpleName(), key.toString(), deriveKey(key)));
      }

      return value;
   }

   @Override
   public Set<V> getAll() { return registry.getAll(); }

   @Override
   public Set<K> keys() {
      return keys.values().stream().collect(Collectors.toSet());
   }

   public void printContent() {
      System.out.println("==== " + getClass().getName() + " ====");
      defaultValues.entrySet().stream().forEach(entry -> {
         System.out.println("Default Key:\t" + deriveKey(entry.getKey()));
         var value = entry.getValue();
         if (value instanceof Collection) {
            var values = (Collection<?>) value;
            var classes = values.stream().map(v -> v.getClass().getName()).collect(Collectors.toList());
            System.out.println("Default Values:\t" + String.join(", ", classes));
         } else {
            System.out.println("Default Value:\t" + value.getClass().getName());
         }
         System.out.println();
      });
      keys().stream().sorted((a, b) -> deriveKey(a).compareTo(deriveKey(b))).forEach(key -> {
         System.out.println("Key:\t" + deriveKey(key));
         var value = get(key).get();
         if (value instanceof Collection) {
            var values = (Collection<?>) value;
            var classes = values.stream().map(v -> v.getClass().getName()).collect(Collectors.toList());
            System.out.println("Values:\t" + String.join(", ", classes));
         } else {
            System.out.println("Value:\t" + value.getClass().getName());
         }
         System.out.println();
      });
      System.out.println("==== END ====");
   }
}
