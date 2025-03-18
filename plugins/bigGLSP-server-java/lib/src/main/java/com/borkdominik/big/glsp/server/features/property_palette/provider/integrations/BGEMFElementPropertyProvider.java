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
package com.borkdominik.big.glsp.server.features.property_palette.provider.integrations;

import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGElementPropertyProvider;
import com.google.inject.Inject;

public abstract class BGEMFElementPropertyProvider<TElement extends EObject>
   extends BGElementPropertyProvider<TElement> {

   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected BGEMFCommandContext context;

   public BGEMFElementPropertyProvider(final Enumerator representation, final Set<BGTypeProvider> elementTypes,
      final Set<String> handledProperties) {
      super(representation, elementTypes, handledProperties);
   }

}
