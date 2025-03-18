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
package com.borkdominik.big.glsp.server.features.outline.generator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.model.notation.Edge;

import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.google.inject.Inject;

public class BGEMFOutlineGenerator extends BGDefaultOutlineGenerator {

   @Inject
   protected BGEMFModelState modelState;

   @Override
   protected boolean filter(final EObject eObject) {
      return super.filter(eObject) && modelState.getElementIndex().hasNotation(eObject);
   }

   @Override
   protected String iconOf(final EObject element) {
      return modelState.getIndex().getNotation(element).map(notation -> {
         if (notation instanceof Edge) {
            return "edge";
         }

         return "element";
      }).orElse("element");
   }
}
