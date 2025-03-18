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
package com.borkdominik.big.glsp.server.lib.utils.reflection;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.types.GLSPServerException;

public final class ReflectionUtil {
   private static Logger LOG = LogManager.getLogger(ReflectionUtil.class);

   private ReflectionUtil() {}

   public static <T> T castOrThrow(final Object toCast, final Class<T> clazz) {
      return castOrThrow(toCast, clazz,
         String.format("Failed to cast %s to %s", toCast.getClass().getSimpleName(), clazz.getSimpleName()));
   }

   public static <T> T castOrThrow(final Object toCast, final Class<T> clazz, final String exceptionMessage) {
      try {
         return clazz.cast(toCast);
      } catch (NoSuchElementException | ClassCastException ex) {
         throw new GLSPServerException(exceptionMessage, ex);
      }
   }

}
