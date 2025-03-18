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

import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.elements.configuration.BGEdgeConfiguration;

public abstract class BGBaseEdgeConfiguration
   extends BGBaseElementConfiguration implements BGEdgeConfiguration {

   public BGBaseEdgeConfiguration(final Enumerator representation, final Set<BGTypeProvider> elementTypes) {
      super(representation, elementTypes);
   }
}
