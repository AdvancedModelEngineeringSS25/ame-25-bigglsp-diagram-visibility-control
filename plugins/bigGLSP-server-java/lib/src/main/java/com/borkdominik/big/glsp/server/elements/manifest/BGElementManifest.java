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
package com.borkdominik.big.glsp.server.elements.manifest;

import java.util.Set;

import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditContribution;
import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditHandler;
import com.borkdominik.big.glsp.server.core.features.direct_editing.label_edit.BGLabelEditHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteOperationContribution;
import com.borkdominik.big.glsp.server.core.manifest.BGRepresentationManifest;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.features.autocomplete.BGAutocompleteContribution;
import com.borkdominik.big.glsp.server.features.property_palette.BGPropertyPaletteContribution;
import com.google.inject.AbstractModule;

public abstract class BGElementManifest extends AbstractModule {

   protected final BGRepresentationManifest manifest;
   protected final Set<BGTypeProvider> elementTypes;

   public BGElementManifest(final BGRepresentationManifest manifest, final Set<BGTypeProvider> elementTypes) {
      super();
      this.manifest = manifest;
      this.elementTypes = elementTypes;
   }

   @Override
   protected void configure() {
      this.configureElement();
   }

   protected abstract void configureElement();

   protected void bindDelete(final Class<? extends BGDeleteHandler> handler) {
      install(new BGDeleteOperationContribution(BGDeleteOperationContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .concrete(handler)
         .build()));
   }

   protected void bindDirectEdit(final Class<? extends BGDirectEditHandler> handler) {
      install(new BGDirectEditContribution(BGDirectEditContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .directEditHandler(handler)
         .build()));
   }

   protected void bindEditLabel(final Set<Class<? extends BGLabelEditHandler>> handlers) {
      install(new BGDirectEditContribution(BGDirectEditContribution.Options.builder()
         .manifest(manifest)
         .elementTypes(elementTypes)
         .labelEditHandlers(handlers)
         .build()));
   }

   protected void bindPropertyPalette(final BGPropertyPaletteContribution.Options.OptionsBuilder builder) {
      install(new BGPropertyPaletteContribution(builder
         .manifest(manifest)
         .elementTypes(elementTypes)
         .build()));
   }

   protected void bindAutocomplete(final BGAutocompleteContribution.Options.OptionsBuilder builder) {
      install(new BGAutocompleteContribution(builder
         .manifest(manifest)
         .build()));
   }
}
