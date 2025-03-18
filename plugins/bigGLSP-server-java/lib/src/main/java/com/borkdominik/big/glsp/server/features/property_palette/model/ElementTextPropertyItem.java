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

public final class ElementTextPropertyItem extends ElementPropertyItem {
   public String label;
   public String text;

   {
      this.type = ElementPropertyType.TEXT.name();
   }

   public static abstract class ElementTextPropertyItemBuilder<C extends ElementTextPropertyItem, B extends ElementTextPropertyItem.ElementTextPropertyItemBuilder<C, B>> extends ElementPropertyItem.ElementPropertyItemBuilder<C, B> {
      private String label;
      private String text;

      public B label(final String label) {
         this.label = label;
         return self();
      }

      public B text(final String text) {
         this.text = text;
         return self();
      }

      @Override
      protected abstract B self();

      @Override
      public abstract C build();

      @Override
      public java.lang.String toString() {
         return "ElementTextPropertyItem.ElementTextPropertyItemBuilder(super=" + super.toString() + ", label=" + this.label + ", text=" + this.text + ")";
      }
   }

   private static final class ElementTextPropertyItemBuilderImpl extends ElementTextPropertyItem.ElementTextPropertyItemBuilder<ElementTextPropertyItem, ElementTextPropertyItem.ElementTextPropertyItemBuilderImpl> {
      private ElementTextPropertyItemBuilderImpl() {
      }

      @Override
      protected ElementTextPropertyItem.ElementTextPropertyItemBuilderImpl self() {
         return this;
      }

      @Override
      public ElementTextPropertyItem build() {
         return new ElementTextPropertyItem(this);
      }
   }

   protected ElementTextPropertyItem(final ElementTextPropertyItem.ElementTextPropertyItemBuilder<?, ?> b) {
      super(b);
      this.label = b.label;
      this.text = b.text;
   }

   public static ElementTextPropertyItem.ElementTextPropertyItemBuilder<?, ?> builder() {
      return new ElementTextPropertyItem.ElementTextPropertyItemBuilderImpl();
   }
}
