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
package com.borkdominik.big.glsp.server.sdk.cdk;

import java.util.Optional;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

import com.borkdominik.big.glsp.server.core.features.suffix.BGIdCountContextGenerator;
import com.borkdominik.big.glsp.server.core.features.suffix.BGSuffix;
import com.borkdominik.big.glsp.server.core.gmodel.BGEMFGModelMapHandler;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessor;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessorFactory;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationRegistry;
import com.google.inject.Inject;

public class GCModelContext {
   @Inject
   public BGEMFModelState modelState;
   @Inject
   public EMFIdGenerator idGenerator;
   @Inject
   public BGSuffix suffix;
   @Inject
   public BGEMFGModelMapHandler mapHandler;
   @Inject
   public BGIdCountContextGenerator idCountGenerator;
   @Inject
   public BGElementConfigurationRegistry configurationRegistry;
   @Inject
   protected BGElementConfigurationAccessorFactory elementConfigurationAccessorFactory;
   protected Optional<BGElementConfigurationAccessor> elementConfigurationAccessor = Optional.empty();

   public Enumerator representation() {
      return modelState.representation().getUnsafe();
   }

   public BGElementConfigurationAccessor elementConfig() {
      if (elementConfigurationAccessor.isEmpty()) {
         elementConfigurationAccessor = Optional.of(elementConfigurationAccessorFactory.create(representation()));
      }

      return elementConfigurationAccessor.get();
   }
}
