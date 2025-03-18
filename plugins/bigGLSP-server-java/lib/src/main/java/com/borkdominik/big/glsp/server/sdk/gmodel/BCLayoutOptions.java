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
package com.borkdominik.big.glsp.server.sdk.gmodel;

import org.eclipse.glsp.graph.builder.impl.GLayoutOptions;

public class BCLayoutOptions extends GLayoutOptions {

   public BCLayoutOptions hGrab(final boolean grab) {
      this.put("hGrab", grab);

      return this;
   }

   public BCLayoutOptions vGrab(final boolean grab) {
      this.put("vGrab", grab);

      return this;
   }

   public BCLayoutOptions defaultPadding() {
      this.putAll(new BCLayoutOptions()
         .paddingTop(5.0)
         .paddingRight(10.0)
         .paddingBottom(5.0)
         .paddingLeft(10.0)
         .paddingFactor(1.0));

      return this;
   }

   public BCLayoutOptions padding(final Double padding) {
      this.putAll(new BCLayoutOptions()
         .paddingTop(padding)
         .paddingRight(padding)
         .paddingBottom(padding)
         .paddingLeft(padding)
         .paddingFactor(1.0));

      return this;
   }

   public BCLayoutOptions padding(final Double vertical, final Double horizontal) {
      this.putAll(new BCLayoutOptions()
         .paddingVertical(vertical)
         .paddingHorizontal(horizontal)
         .paddingFactor(1.0));

      return this;
   }

   public BCLayoutOptions paddingHorizontal(final Double padding) {
      this.putAll(new BCLayoutOptions()
         .paddingRight(padding)
         .paddingLeft(padding)
         .paddingFactor(1.0));

      return this;
   }

   public BCLayoutOptions paddingVertical(final Double padding) {
      this.putAll(new BCLayoutOptions()
         .paddingTop(padding)
         .paddingBottom(padding)
         .paddingFactor(1.0));

      return this;
   }

   public BCLayoutOptions clearPadding() {
      this.putAll(new BCLayoutOptions()
         .paddingTop(0.0)
         .paddingRight(0.0)
         .paddingBottom(0.0)
         .paddingLeft(0.0));

      return this;
   }
}
