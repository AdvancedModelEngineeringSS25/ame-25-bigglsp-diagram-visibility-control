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

import org.eclipse.glsp.server.model.DefaultGModelState;

import com.borkdominik.big.glsp.server.core.model.BGModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.borkdominik.big.glsp.server.core.model.BGModelState;

import jakarta.inject.Inject;

public class BGGModelStateImpl extends DefaultGModelState implements BGModelState {
   @Inject
   protected BGModelRepresentation modelStateRepresentation;
   @Inject
   protected BGModelElementIndex elementIndex;

   @Override
   public BGModelElementIndex getElementIndex() { return this.elementIndex; }

   @Override
   public BGModelRepresentation representation() {
      return modelStateRepresentation;
   }
}
