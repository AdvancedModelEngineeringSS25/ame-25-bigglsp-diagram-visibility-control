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
package com.borkdominik.big.glsp.server.lib.emf;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class BGEcoreUtils {

   @SuppressWarnings("unchecked")
   public static void replace(final EObject element, final EReference reference, final EObject newValue) {
      var value = element.eGet(reference);
      if (value instanceof EObject) {
         element.eSet(reference, newValue);
      } else if (value instanceof Collection c) {
         c.clear();
         c.add(newValue);
      }
   }
}
