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
package com.borkdominik.big.glsp.server.core.model;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class BGTypeProviderAll implements BGTypeProvider {

   public static final BGTypeProviderAll instance = new BGTypeProviderAll();

   protected BGTypeProviderAll() {}

   @Override
   public String typeId() {
      return "*";
   }

   @Override
   public Collection<Class<? extends EObject>> handledElements() {
      return List.of(EObject.class);
   }

   @Override
   public boolean canHandle(final Class<? extends EObject> element) {
      return true;
   }

}
