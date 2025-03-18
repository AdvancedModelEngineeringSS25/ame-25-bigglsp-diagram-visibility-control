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
import org.eclipse.glsp.server.actions.Action;

public final class ElementReferencePropertyItem extends ElementPropertyItem {
   public String label;
   public List<Reference> references;
   public List<CreateReference> creates;
   public Boolean isOrderable;
   public Boolean isAutocomplete;

   {
      type = ElementPropertyType.REFERENCE.name();
   }

   public static class Reference {
      public String elementId;
      public String label;
      public String name;
      public String hint;
      public List<Action> deleteActions;

      private static List<Action> $default$deleteActions() {
         return new ArrayList<>();
      }

      Reference(final String elementId, final String label, final String name, final String hint, final List<Action> deleteActions) {
         this.elementId = elementId;
         this.label = label;
         this.name = name;
         this.hint = hint;
         this.deleteActions = deleteActions;
      }

      public static class ReferenceBuilder {
         private String elementId;
         private String label;
         private String name;
         private String hint;
         private boolean deleteActions$set;
         private List<Action> deleteActions$value;

         ReferenceBuilder() {
         }

         public ElementReferencePropertyItem.Reference.ReferenceBuilder elementId(final String elementId) {
            this.elementId = elementId;
            return this;
         }

         public ElementReferencePropertyItem.Reference.ReferenceBuilder label(final String label) {
            this.label = label;
            return this;
         }

         public ElementReferencePropertyItem.Reference.ReferenceBuilder name(final String name) {
            this.name = name;
            return this;
         }

         public ElementReferencePropertyItem.Reference.ReferenceBuilder hint(final String hint) {
            this.hint = hint;
            return this;
         }

         public ElementReferencePropertyItem.Reference.ReferenceBuilder deleteActions(final List<Action> deleteActions) {
            this.deleteActions$value = deleteActions;
            deleteActions$set = true;
            return this;
         }

         public ElementReferencePropertyItem.Reference build() {
            List<Action> deleteActions$value = this.deleteActions$value;
            if (!this.deleteActions$set) deleteActions$value = Reference.$default$deleteActions();
            return new ElementReferencePropertyItem.Reference(this.elementId, this.label, this.name, this.hint, deleteActions$value);
         }

         @Override
         public java.lang.String toString() {
            return "ElementReferencePropertyItem.Reference.ReferenceBuilder(elementId=" + this.elementId + ", label=" + this.label + ", name=" + this.name + ", hint=" + this.hint + ", deleteActions$value=" + this.deleteActions$value + ")";
         }
      }

      public static ElementReferencePropertyItem.Reference.ReferenceBuilder builder() {
         return new ElementReferencePropertyItem.Reference.ReferenceBuilder();
      }
   }

   public static class CreateReference {
      public String label;
      public Action action;

      CreateReference(final String label, final Action action) {
         this.label = label;
         this.action = action;
      }

      public static class CreateReferenceBuilder {
         private String label;
         private Action action;

         CreateReferenceBuilder() {
         }

         public ElementReferencePropertyItem.CreateReference.CreateReferenceBuilder label(final String label) {
            this.label = label;
            return this;
         }

         public ElementReferencePropertyItem.CreateReference.CreateReferenceBuilder action(final Action action) {
            this.action = action;
            return this;
         }

         public ElementReferencePropertyItem.CreateReference build() {
            return new ElementReferencePropertyItem.CreateReference(this.label, this.action);
         }

         @Override
         public java.lang.String toString() {
            return "ElementReferencePropertyItem.CreateReference.CreateReferenceBuilder(label=" + this.label + ", action=" + this.action + ")";
         }
      }

      public static ElementReferencePropertyItem.CreateReference.CreateReferenceBuilder builder() {
         return new ElementReferencePropertyItem.CreateReference.CreateReferenceBuilder();
      }
   }

   private static List<Reference> $default$references() {
      return new ArrayList<>();
   }

   private static List<CreateReference> $default$creates() {
      return new ArrayList<>();
   }

   public static abstract class ElementReferencePropertyItemBuilder<C extends ElementReferencePropertyItem, B extends ElementReferencePropertyItem.ElementReferencePropertyItemBuilder<C, B>> extends ElementPropertyItem.ElementPropertyItemBuilder<C, B> {
      private String label;
      private boolean references$set;
      private List<Reference> references$value;
      private boolean creates$set;
      private List<CreateReference> creates$value;
      private Boolean isOrderable;
      private Boolean isAutocomplete;

      public B label(final String label) {
         this.label = label;
         return self();
      }

      public B references(final List<Reference> references) {
         this.references$value = references;
         references$set = true;
         return self();
      }

      public B creates(final List<CreateReference> creates) {
         this.creates$value = creates;
         creates$set = true;
         return self();
      }

      public B isOrderable(final Boolean isOrderable) {
         this.isOrderable = isOrderable;
         return self();
      }

      public B isAutocomplete(final Boolean isAutocomplete) {
         this.isAutocomplete = isAutocomplete;
         return self();
      }

      @Override
      protected abstract B self();

      @Override
      public abstract C build();

      @Override
      public java.lang.String toString() {
         return "ElementReferencePropertyItem.ElementReferencePropertyItemBuilder(super=" + super.toString() + ", label=" + this.label + ", references$value=" + this.references$value + ", creates$value=" + this.creates$value + ", isOrderable=" + this.isOrderable + ", isAutocomplete=" + this.isAutocomplete + ")";
      }
   }

   private static final class ElementReferencePropertyItemBuilderImpl extends ElementReferencePropertyItem.ElementReferencePropertyItemBuilder<ElementReferencePropertyItem, ElementReferencePropertyItem.ElementReferencePropertyItemBuilderImpl> {
      private ElementReferencePropertyItemBuilderImpl() {
      }

      @Override
      protected ElementReferencePropertyItem.ElementReferencePropertyItemBuilderImpl self() {
         return this;
      }

      @Override
      public ElementReferencePropertyItem build() {
         return new ElementReferencePropertyItem(this);
      }
   }

   protected ElementReferencePropertyItem(final ElementReferencePropertyItem.ElementReferencePropertyItemBuilder<?, ?> b) {
      super(b);
      this.label = b.label;
      if (b.references$set) this.references = b.references$value;
       else this.references = ElementReferencePropertyItem.$default$references();
      if (b.creates$set) this.creates = b.creates$value;
       else this.creates = ElementReferencePropertyItem.$default$creates();
      this.isOrderable = b.isOrderable;
      this.isAutocomplete = b.isAutocomplete;
   }

   public static ElementReferencePropertyItem.ElementReferencePropertyItemBuilder<?, ?> builder() {
      return new ElementReferencePropertyItem.ElementReferencePropertyItemBuilderImpl();
   }
}
