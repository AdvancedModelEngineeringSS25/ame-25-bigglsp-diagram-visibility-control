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

import java.util.NoSuchElementException;
import java.util.Optional;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;

public abstract class BGTypeRegistry<V> extends BGRepresentationMultiKeyRegistry<BGTypeProvider, V> {

   @Override
   protected String deriveKey(final BGRegistryKey<BGTypeProvider> key) {
      var representation = key.representation;
      var typeProvider = key.key;

      return String.format("%s__%s", representation, typeProvider.typeId());
   }

   public Optional<V> get(final Enumerator representation, final Class<? extends EObject> elementType) {
      var typeProvider = keys.values().stream()
         .filter(t -> t.representation == representation && t.key.canHandle(elementType)).findFirst();

      return typeProvider.flatMap(t -> super.get(t));
   }

   public Optional<V> getOrDefault(final Enumerator representation, final Class<? extends EObject> elementType) {
      var typeProvider = keys.values().stream()
         .filter(t -> t.representation == representation && t.key.canHandle(elementType)).findFirst();
      var defaultProvider = defaultValues.keySet().stream()
         .filter(k -> k.representation == representation && k.key.canHandle(elementType))
         .findFirst()
         .map(k -> defaultValues.get(k));

      return typeProvider.flatMap(t -> super.get(t))
         .or(() -> defaultProvider);
   }

   public boolean has(final Enumerator representation,
      final Class<? extends EObject> elementType) {
      return keys.values().stream().filter(t -> t.key.canHandle(elementType)).findAny().isPresent();
   }

   public V retrieve(final Enumerator representation, final String elementTypeId) {
      var typeProvider = keys.values().stream()
         .filter(t -> t.representation == representation && t.key.prefix(representation).equals(elementTypeId))
         .findFirst()
         .orElseThrow(
            () -> {
               this.printContent();
               return new GLSPServerException(String.format("[%s] no key found for %s", representation, elementTypeId));
            });

      return retrieve(typeProvider);
   }

   public V retrieveOrDefault(final Enumerator representation, final String elementTypeId) {
      var key = keys.values().stream()
         .filter(t -> t.representation == representation && t.key.prefix(representation).equals(elementTypeId))
         .findFirst();

      var defaultValue = defaultValues.keySet().stream()
         .filter(k -> k.representation == representation && k.key.prefix(representation).equals(elementTypeId))
         .findFirst()
         .map(k -> defaultValues.get(k))
         .orElse(null);
      var value = key.flatMap(k -> get(k))
         .orElse(defaultValue);

      if (value == null) {
         printContent();
         new NoSuchElementException(
            String.format(
               "[%s] No value found for representation %s and key %s and default value was also null.",
               this.getClass().getSimpleName(), representation, elementTypeId));
      }

      return value;
   }

   public V retrieve(final Enumerator representation, final Class<? extends EObject> elementType) {
      var typeProvider = keys.values().stream()
         .filter(t -> t.representation == representation && t.key.canHandle(elementType))
         .findFirst()
         .orElseThrow(
            () -> {
               this.printContent();
               return new GLSPServerException(
                  String.format("[%s] no key found that can handle %s", representation, elementType.getSimpleName()));
            });

      return retrieve(typeProvider);
   }

   public V retrieveOrDefault(final Enumerator representation, final Class<? extends EObject> elementType) {
      var key = keys.values().stream().filter(t -> t.representation == representation && t.key.canHandle(elementType))
         .findFirst();

      var defaultValue = defaultValues.keySet().stream()
         .filter(k -> k.representation == representation && k.key.canHandle(elementType))
         .findFirst()
         .map(k -> defaultValues.get(k))
         .orElse(null);
      var value = key.flatMap(k -> get(k))
         .orElse(defaultValue);

      if (value == null) {
         printContent();
         new NoSuchElementException(
            String.format(
               "[%s] No value found for representation %s and key %s and default value was also null.",
               this.getClass().getSimpleName(), representation, elementType.getSimpleName()));
      }

      return value;
   }

}
