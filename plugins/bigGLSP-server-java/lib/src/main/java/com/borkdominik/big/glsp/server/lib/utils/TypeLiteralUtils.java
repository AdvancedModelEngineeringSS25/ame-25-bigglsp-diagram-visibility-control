/********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.lib.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

public class TypeLiteralUtils {

   @SuppressWarnings("unchecked")
   public static <T> Class<T> of(final TypeLiteral<T> literal) {
      if (literal == null) {
         return null;
      }
      return (Class<T>) literal.getRawType();
   }

   public static <T> TypeLiteral<T> of(final Class<T> type) {
      if (type == null) {
         return null;
      }
      return TypeLiteral.get(type);
   }

   public static <T> Set<TypeLiteral<? extends T>> ofs(final Class<? extends T> type) {
      if (type == null) {
         return Set.of();
      }
      return ofs(Set.of(type));
   }

   public static <T> Set<TypeLiteral<? extends T>> ofs(final Set<Class<? extends T>> types) {
      if (types == null || types.size() == 0) {
         return Set.of();
      }
      return types.stream().map(t -> TypeLiteral.get(t)).collect(Collectors.toSet());
   }

   @SuppressWarnings("unchecked")
   public static <TType, TArgument, TResult extends TType> TypeLiteral<TResult> subtypeOf(
      final Class<TType> type,
      final Class<TArgument> argument) {
      if (type == null) {
         return null;
      }

      var subtype = Types.subtypeOf(argument);
      var newType = Types.newParameterizedType(type, subtype);
      return (TypeLiteral<TResult>) TypeLiteral.get(newType);
   }

   @SuppressWarnings("unchecked")
   public static <TType, TArgument, TResult extends TType> TypeLiteral<TResult> parameterizedType(
      final Class<TType> type,
      final Class<TArgument> argument) {
      if (type == null) {
         return null;
      }

      var newType = Types.newParameterizedType(type, argument);
      return (TypeLiteral<TResult>) TypeLiteral.get(newType);
   }

   @SuppressWarnings("unchecked")
   public static <TType, TArgument, TResult extends TType> TypeLiteral<TResult> parameterizedType(
      final Class<TType> type,
      final TypeLiteral<TArgument> argument) {
      if (type == null) {
         return null;
      }

      var newType = Types.newParameterizedType(type, argument.getType());
      return (TypeLiteral<TResult>) TypeLiteral.get(newType);
   }

   @SuppressWarnings("unchecked")
   public static <TType, TArgument, TResult extends TType> TypeLiteral<TResult> parameterizedTypeWithOwner(
      final Class<?> owner, final Class<TType> type,
      final TypeLiteral<TArgument> argument) {
      if (type == null) {
         return null;
      }

      var newType = Types.newParameterizedTypeWithOwner(owner, type, argument.getType());
      return (TypeLiteral<TResult>) TypeLiteral.get(newType);
   }

}
