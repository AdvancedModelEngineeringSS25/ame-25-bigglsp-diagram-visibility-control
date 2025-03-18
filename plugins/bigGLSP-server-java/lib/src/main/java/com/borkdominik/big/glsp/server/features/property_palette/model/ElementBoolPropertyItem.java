/******************************************************************************** * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.features.property_palette.model;

public final class ElementBoolPropertyItem extends ElementPropertyItem {
   public String label;
   public boolean value;

   {
      this.type = ElementPropertyType.BOOL.name();
   }

   public static abstract class ElementBoolPropertyItemBuilder<C extends ElementBoolPropertyItem, B extends ElementBoolPropertyItem.ElementBoolPropertyItemBuilder<C, B>> extends ElementPropertyItem.ElementPropertyItemBuilder<C, B> {
      private String label;
      private boolean value;

      public B label(final String label) {
         this.label = label;
         return self();
      }

      public B value(final boolean value) {
         this.value = value;
         return self();
      }

      @Override
      protected abstract B self();

      @Override
      public abstract C build();

      @Override
      public java.lang.String toString() {
         return "ElementBoolPropertyItem.ElementBoolPropertyItemBuilder(super=" + super.toString() + ", label=" + this.label + ", value=" + this.value + ")";
      }
   }

   private static final class ElementBoolPropertyItemBuilderImpl extends ElementBoolPropertyItem.ElementBoolPropertyItemBuilder<ElementBoolPropertyItem, ElementBoolPropertyItem.ElementBoolPropertyItemBuilderImpl> {
      private ElementBoolPropertyItemBuilderImpl() {
      }

      @Override
      protected ElementBoolPropertyItem.ElementBoolPropertyItemBuilderImpl self() {
         return this;
      }

      @Override
      public ElementBoolPropertyItem build() {
         return new ElementBoolPropertyItem(this);
      }
   }

   protected ElementBoolPropertyItem(final ElementBoolPropertyItem.ElementBoolPropertyItemBuilder<?, ?> b) {
      super(b);
      this.label = b.label;
      this.value = b.value;
   }

   public static ElementBoolPropertyItem.ElementBoolPropertyItemBuilder<?, ?> builder() {
      return new ElementBoolPropertyItem.ElementBoolPropertyItemBuilderImpl();
   }
}
