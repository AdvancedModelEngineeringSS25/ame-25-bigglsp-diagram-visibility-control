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
package com.borkdominik.big.glsp.server.core.di;

import java.util.Optional;
import java.util.function.Function;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;

public class DIProvider<TFactory, TComponent> implements Provider<TComponent> {

   protected Optional<Class<TFactory>> factoryType;
   protected Optional<TypeLiteral<TFactory>> factoryLiteral;
   protected Function<TFactory, TComponent> accessor;

   @Inject
   protected Injector injector;

   public static <TFactory, TComponent> DIProvider<TFactory, TComponent> byClass(final Class<TFactory> factoryType,
      final Function<TFactory, TComponent> accessor) {
      return new DIProvider<>(factoryType, null, accessor);
   }

   public static <TFactory, TComponent> DIProvider<TFactory, TComponent> byLiteral(
      final TypeLiteral<TFactory> factoryLiteral, final Function<TFactory, TComponent> accessor) {
      return new DIProvider<>(null, factoryLiteral, accessor);
   }

   protected DIProvider(
      final Class<TFactory> factoryType, final TypeLiteral<TFactory> factoryLiteral,
      final Function<TFactory, TComponent> accessor) {
      this.factoryType = Optional.ofNullable(factoryType);
      this.factoryLiteral = Optional.ofNullable(factoryLiteral);
      this.accessor = accessor;
   }

   @Override
   public TComponent get() {
      TFactory factory;
      if (this.factoryType.isPresent()) {
         factory = injector.getInstance(factoryType.get());
      } else {
         factory = injector.getInstance(Key.get(factoryLiteral.get()));
      }

      return accessor.apply(factory);
   }

}
