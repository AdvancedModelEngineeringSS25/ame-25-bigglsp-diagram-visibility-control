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
package com.borkdominik.big.glsp.server.core.manifest.contribution.factory;

import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class BGRFactoryContributionModule<TFactory> extends AbstractModule {
   protected final TypeLiteral<TFactory> factory;

   public BGRFactoryContributionModule(final Class<TFactory> factory) {
      this(TypeLiteralUtils.of(factory));
   }

   public BGRFactoryContributionModule(final TypeLiteral<TFactory> factory) {
      this.factory = factory;
   }

   @Override
   protected void configure() {
      install(new FactoryModuleBuilder()
         .build(factory));
   }
}
