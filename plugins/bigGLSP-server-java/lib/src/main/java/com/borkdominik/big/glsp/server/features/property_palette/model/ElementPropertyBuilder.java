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
package com.borkdominik.big.glsp.server.features.property_palette.model;

import java.util.ArrayList;
import java.util.List;

public class ElementPropertyBuilder {
   private final String elementId;
   private final List<ElementPropertyItem> items = new ArrayList<>();

   public ElementPropertyBuilder(final String elementId) {
      this.elementId = elementId;
   }

   public ElementPropertyBuilder text(final String property, final String label,
      final String value) {
      return text(elementId, property, label, value, false);
   }

   public ElementPropertyBuilder text(final String property, final String label,
      final String value, final Boolean isReadOnly) {
      return text(elementId, property, label, value, isReadOnly);
   }

   public ElementPropertyBuilder text(final String elementId, final String property,
      final String label,
      final String value, final Boolean isReadOnly) {
      items.add(ElementTextPropertyItem.builder()
         .elementId(elementId)
         .propertyId(property)
         .label(label)
         .text(value)
         .disabled(isReadOnly).build());
      return this;
   }

   public ElementPropertyBuilder bool(final String property, final String label,
      final boolean value) {
      return bool(elementId, property, label, value, false);
   }

   public ElementPropertyBuilder bool(final String property, final String label,
      final boolean value, final Boolean isReadOnly) {
      return bool(elementId, property, label, value, isReadOnly);
   }

   public ElementPropertyBuilder bool(final String elementId, final String property,
      final String label,
      final boolean value, final Boolean isReadOnly) {
      items.add(
         ElementBoolPropertyItem.builder()
            .elementId(elementId)
            .propertyId(property)
            .label(label)
            .value(value).build());
      return this;
   }

   public ElementPropertyBuilder choice(final String property, final String label,
      final List<ElementChoicePropertyItem.Choice> choices,
      final String choice) {
      return choice(elementId, property, label, choices, choice, false);
   }

   public ElementPropertyBuilder choice(final String property, final String label,
      final List<ElementChoicePropertyItem.Choice> choices,
      final String choice, final boolean isReadOnly) {
      return choice(elementId, property, label, choices, choice, isReadOnly);
   }

   public ElementPropertyBuilder choice(final String elementId, final String property,
      final String label,
      final List<ElementChoicePropertyItem.Choice> choices, final String choice, final Boolean isReadonly) {
      items.add(ElementChoicePropertyItem.builder()
         .elementId(elementId)
         .propertyId(property)
         .label(label)
         .choices(choices)
         .choice(choice)
         .disabled(isReadonly)
         .build());
      return this;
   }

   public ElementPropertyBuilder reference(final String property, final String label,
      final List<ElementReferencePropertyItem.Reference> references) {
      return reference(elementId, property, label, references, List.of(), false, false);
   }

   public ElementPropertyBuilder reference(final String property, final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates) {
      return reference(elementId, property, label, references, creates, false, false);
   }

   public ElementPropertyBuilder reference(final String property, final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates,
      final Boolean isOrderable) {
      return reference(elementId, property, label, references, creates, isOrderable, false);
   }

   public ElementPropertyBuilder reference(final String property, final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates,
      final Boolean isOrderable, final Boolean isAutocomplete) {
      return reference(elementId, property, label, references, creates, isOrderable, isAutocomplete);
   }

   public ElementPropertyBuilder reference(final String elementId, final String property,
      final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates,
      final Boolean isOrderable, final Boolean isAutocomplete) {
      items.add(ElementReferencePropertyItem.builder()
         .elementId(elementId)
         .propertyId(property)
         .label(label)
         .references(references)
         .creates(creates)
         .isOrderable(isOrderable)
         .isAutocomplete(isAutocomplete)
         .build());
      return this;
   }

   public ElementPropertyBuilder choice(final ElementChoicePropertyItem item) {
      items.add(item);
      return this;
   }

   public ElementPropertyBuilder reference(final ElementReferencePropertyItem item) {
      items.add(item);
      return this;
   }

   public List<ElementPropertyItem> items() {
      return items;
   }

}
