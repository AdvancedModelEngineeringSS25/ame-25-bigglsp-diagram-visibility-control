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
package com.borkdominik.big.glsp.server.core.features.tool_palette;

import java.util.Optional;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessor;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessorFactory;
import com.google.inject.Inject;

public abstract class BGBaseToolPaletteProvider implements BGToolPaletteProvider {

   @Inject
   protected BGElementConfigurationAccessorFactory elementConfigurationAccessorFactory;
   protected Optional<BGElementConfigurationAccessor> elementConfigurationAccessor = Optional.empty();

   protected Enumerator representation;

   public BGBaseToolPaletteProvider(final Enumerator representation) {
      super();
      this.representation = representation;
   }

   public Enumerator representation() {
      return representation;
   }

   protected BGElementConfigurationAccessor elementConfig() {
      if (elementConfigurationAccessor.isEmpty()) {
         elementConfigurationAccessor = Optional.of(elementConfigurationAccessorFactory.create(representation));
      }

      return elementConfigurationAccessor.get();
   }
}
