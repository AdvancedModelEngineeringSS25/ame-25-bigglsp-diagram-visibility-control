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
package com.borkdominik.big.glsp.server.features.property_palette.handler;

import org.eclipse.glsp.server.actions.Action;

public class BGUpdateElementPropertyAction extends Action {
   public static final String KIND = "updateElementProperty";

   protected String elementId;
   protected String propertyId;
   protected String value;

   public BGUpdateElementPropertyAction() {
      super(KIND);
   }

   public BGUpdateElementPropertyAction(final String elementId, final String propertyId,
      final String value) {
      super(KIND);
      this.elementId = elementId;
      this.propertyId = propertyId;
      this.value = value;
   }

   public String getElementId() { return elementId; }

   public void setElementId(final String elementId) { this.elementId = elementId; }

   public String getPropertyId() { return propertyId; }

   public void setPropertyId(final String propertyId) { this.propertyId = propertyId; }

   public String getValue() { return value; }

   public void setValue(final String value) { this.value = value; }

}
