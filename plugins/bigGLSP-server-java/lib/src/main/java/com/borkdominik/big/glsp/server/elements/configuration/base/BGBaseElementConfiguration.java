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
package com.borkdominik.big.glsp.server.elements.configuration.base;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfiguration;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessor;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessorFactory;
import com.google.inject.Inject;

public abstract class BGBaseElementConfiguration
   implements BGElementConfiguration {
   protected final Enumerator representation;
   protected final Set<BGTypeProvider> elementTypes;

   @Inject
   protected BGModelState modelState;
   @Inject
   protected BGElementConfigurationAccessorFactory elementConfigurationAccessorFactory;
   protected Optional<BGElementConfigurationAccessor> elementConfigurationAccessor = Optional.empty();

   public BGBaseElementConfiguration(final Enumerator representation, final Set<BGTypeProvider> elementTypes) {
      super();
      this.representation = representation;
      this.elementTypes = elementTypes;
   }

   public Enumerator representation() {
      return representation;
   }

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return elementTypes; }

   protected BGElementConfigurationAccessor elementConfig() {
      if (elementConfigurationAccessor.isEmpty()) {
         elementConfigurationAccessor = Optional.of(elementConfigurationAccessorFactory.create(representation));
      }

      return elementConfigurationAccessor.get();
   }
}
