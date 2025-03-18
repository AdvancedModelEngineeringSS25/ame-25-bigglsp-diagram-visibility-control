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
package com.borkdominik.big.glsp.server.lib.registry;

import org.eclipse.emf.common.util.Enumerator;

public abstract class BGRepresentationSingleKeyRegistry<V> extends BGBaseRegistry<Enumerator, V> {
   @Override
   protected String deriveKey(final Enumerator key) {
      return key.getName();
   }
}
