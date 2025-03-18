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

import java.util.Objects;

import org.eclipse.emf.common.util.Enumerator;

public class BGRegistryKey<Key> {
   public final Enumerator representation;
   public final Key key;

   public BGRegistryKey(final Enumerator representation, final Key key) {
      this.representation = representation;
      this.key = key;
   }

   public static <Key> BGRegistryKey<Key> of(final Enumerator representation, final Key key) {
      return new BGRegistryKey<>(representation, key);
   }

   @Override
   public int hashCode() {
      return Objects.hash(representation, key);
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if ((obj == null) || (getClass() != obj.getClass())) {
         return false;
      }
      BGRegistryKey other = (BGRegistryKey) obj;
      return Objects.equals(representation, other.representation) && Objects.equals(key, other.key);
   }

}
