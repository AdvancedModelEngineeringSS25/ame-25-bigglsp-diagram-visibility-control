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

import java.util.ArrayList;
import java.util.List;

public final class ElementChoicePropertyItem extends ElementPropertyItem {
   public String label;
   public List<ElementChoicePropertyItem.Choice> choices;
   public String choice;

   {
      type = ElementPropertyType.CHOICE.name();
   }

   public static class Choice {
      public String label;
      public String value;
      public String secondaryText;

      Choice(final String label, final String value, final String secondaryText) {
         this.label = label;
         this.value = value;
         this.secondaryText = secondaryText;
      }

      public static class ChoiceBuilder {
         private String label;
         private String value;
         private String secondaryText;

         ChoiceBuilder() {
         }

         public ElementChoicePropertyItem.Choice.ChoiceBuilder label(final String label) {
            this.label = label;
            return this;
         }

         public ElementChoicePropertyItem.Choice.ChoiceBuilder value(final String value) {
            this.value = value;
            return this;
         }

         public ElementChoicePropertyItem.Choice.ChoiceBuilder secondaryText(final String secondaryText) {
            this.secondaryText = secondaryText;
            return this;
         }

         public ElementChoicePropertyItem.Choice build() {
            return new ElementChoicePropertyItem.Choice(this.label, this.value, this.secondaryText);
         }

         @Override
         public java.lang.String toString() {
            return "ElementChoicePropertyItem.Choice.ChoiceBuilder(label=" + this.label + ", value=" + this.value + ", secondaryText=" + this.secondaryText + ")";
         }
      }

      public static ElementChoicePropertyItem.Choice.ChoiceBuilder builder() {
         return new ElementChoicePropertyItem.Choice.ChoiceBuilder();
      }
   }

   private static List<ElementChoicePropertyItem.Choice> $default$choices() {
      return new ArrayList<>();
   }

   public static abstract class ElementChoicePropertyItemBuilder<C extends ElementChoicePropertyItem, B extends ElementChoicePropertyItem.ElementChoicePropertyItemBuilder<C, B>> extends ElementPropertyItem.ElementPropertyItemBuilder<C, B> {
      private String label;
      private boolean choices$set;
      private List<ElementChoicePropertyItem.Choice> choices$value;
      private String choice;

      public B label(final String label) {
         this.label = label;
         return self();
      }

      public B choices(final List<ElementChoicePropertyItem.Choice> choices) {
         this.choices$value = choices;
         choices$set = true;
         return self();
      }

      public B choice(final String choice) {
         this.choice = choice;
         return self();
      }

      @Override
      protected abstract B self();

      @Override
      public abstract C build();

      @Override
      public java.lang.String toString() {
         return "ElementChoicePropertyItem.ElementChoicePropertyItemBuilder(super=" + super.toString() + ", label=" + this.label + ", choices$value=" + this.choices$value + ", choice=" + this.choice + ")";
      }
   }

   private static final class ElementChoicePropertyItemBuilderImpl extends ElementChoicePropertyItem.ElementChoicePropertyItemBuilder<ElementChoicePropertyItem, ElementChoicePropertyItem.ElementChoicePropertyItemBuilderImpl> {
      private ElementChoicePropertyItemBuilderImpl() {
      }

      @Override
      protected ElementChoicePropertyItem.ElementChoicePropertyItemBuilderImpl self() {
         return this;
      }

      @Override
      public ElementChoicePropertyItem build() {
         return new ElementChoicePropertyItem(this);
      }
   }

   protected ElementChoicePropertyItem(final ElementChoicePropertyItem.ElementChoicePropertyItemBuilder<?, ?> b) {
      super(b);
      this.label = b.label;
      if (b.choices$set) this.choices = b.choices$value;
       else this.choices = ElementChoicePropertyItem.$default$choices();
      this.choice = b.choice;
   }

   public static ElementChoicePropertyItem.ElementChoicePropertyItemBuilder<?, ?> builder() {
      return new ElementChoicePropertyItem.ElementChoicePropertyItemBuilderImpl();
   }
}
