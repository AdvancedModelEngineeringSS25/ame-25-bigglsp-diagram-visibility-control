/******************************************************************************** * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.features.property_palette.model;


public class ElementPropertyItem {
   public String elementId;
   public String propertyId;
   public String type;
   public Boolean disabled;

   private static Boolean $default$disabled() {
      return false;
   }

   public static abstract class ElementPropertyItemBuilder<C extends ElementPropertyItem, B extends ElementPropertyItem.ElementPropertyItemBuilder<C, B>> {
      private String elementId;
      private String propertyId;
      private String type;
      private boolean disabled$set;
      private Boolean disabled$value;

      public B elementId(final String elementId) {
         if (elementId == null) {
            throw new java.lang.NullPointerException("elementId is marked non-null but is null");
         }
         this.elementId = elementId;
         return self();
      }

      public B propertyId(final String propertyId) {
         if (propertyId == null) {
            throw new java.lang.NullPointerException("propertyId is marked non-null but is null");
         }
         this.propertyId = propertyId;
         return self();
      }

      public B type(final String type) {
         this.type = type;
         return self();
      }

      public B disabled(final Boolean disabled) {
         this.disabled$value = disabled;
         disabled$set = true;
         return self();
      }

      protected abstract B self();

      public abstract C build();

      @Override
      public java.lang.String toString() {
         return "ElementPropertyItem.ElementPropertyItemBuilder(elementId=" + this.elementId + ", propertyId=" + this.propertyId + ", type=" + this.type + ", disabled$value=" + this.disabled$value + ")";
      }
   }

   private static final class ElementPropertyItemBuilderImpl extends ElementPropertyItem.ElementPropertyItemBuilder<ElementPropertyItem, ElementPropertyItem.ElementPropertyItemBuilderImpl> {
      private ElementPropertyItemBuilderImpl() {
      }

      @Override
      protected ElementPropertyItem.ElementPropertyItemBuilderImpl self() {
         return this;
      }

      @Override
      public ElementPropertyItem build() {
         return new ElementPropertyItem(this);
      }
   }

   protected ElementPropertyItem(final ElementPropertyItem.ElementPropertyItemBuilder<?, ?> b) {
      this.elementId = b.elementId;
      if (elementId == null) {
         throw new java.lang.NullPointerException("elementId is marked non-null but is null");
      }
      this.propertyId = b.propertyId;
      if (propertyId == null) {
         throw new java.lang.NullPointerException("propertyId is marked non-null but is null");
      }
      this.type = b.type;
      if (b.disabled$set) this.disabled = b.disabled$value;
       else this.disabled = ElementPropertyItem.$default$disabled();
   }

   public static ElementPropertyItem.ElementPropertyItemBuilder<?, ?> builder() {
      return new ElementPropertyItem.ElementPropertyItemBuilderImpl();
   }
}
