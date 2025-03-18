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
package com.borkdominik.big.glsp.server.core.model.integrations;

import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelStateImpl;

import com.borkdominik.big.glsp.server.core.model.BGEMFModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;

import jakarta.inject.Inject;

public class BGEMFModelStateImpl extends EMFNotationModelStateImpl implements BGEMFModelState {
   @Inject
   protected BGModelRepresentation modelStateRepresentation;
   @Inject
   protected BGEMFModelElementIndex elementIndex;

   @Override
   public BGEMFModelElementIndex getElementIndex() { return this.elementIndex; }

   @Override
   public BGModelRepresentation representation() {
      return modelStateRepresentation;
   }

   @Override
   protected GModelIndex getOrUpdateIndex(final GModelRoot newRoot) {
      return BGEMFModelIndexImpl.getOrCreate(getRoot(), semanticIdConverter);
   }

}
