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
package com.borkdominik.big.glsp.server.features.property_palette.handler;

import org.eclipse.glsp.server.actions.ResponseAction;

import com.borkdominik.big.glsp.server.features.property_palette.model.PropertyPalette;

public class BGSetPropertyPaletteAction extends ResponseAction {
   public static final String KIND = "setPropertyPalette";

   private PropertyPalette palette;

   public BGSetPropertyPaletteAction() {
      super(KIND);
   }

   public BGSetPropertyPaletteAction(final PropertyPalette palette) {
      super(KIND);
      this.palette = palette;
   }

   public PropertyPalette getPalette() { return palette; }

   public void setPalette(final PropertyPalette palette) { this.palette = palette; }

}
