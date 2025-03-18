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
package com.borkdominik.big.glsp.server.elements.handler.operations;

import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

import com.borkdominik.big.glsp.server.core.handler.BGTypeHandler;
import com.borkdominik.big.glsp.server.core.model.BGModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationRegistry;
import com.google.inject.Inject;

public abstract class BGElementOperationHandler implements BGTypeHandler {
   protected final Enumerator representation;
   protected final Set<BGTypeProvider> handledElementTypes;

   @Inject
   protected BGModelState modelState;
   @Inject
   protected BGModelElementIndex elementIndex;
   @Inject
   protected EMFIdGenerator idGenerator;
   @Inject
   protected BGElementConfigurationRegistry configurationRegistry;

   public BGElementOperationHandler(final Enumerator representation,
      final Set<BGTypeProvider> handledElementTypes) {
      this.representation = representation;
      this.handledElementTypes = handledElementTypes;
   }

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return handledElementTypes; }
}
