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
package com.borkdominik.big.glsp.server.core.manifest;

import org.eclipse.emf.common.util.Enumerator;

import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditContribution;
import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditHandler;
import com.borkdominik.big.glsp.server.core.features.tool_palette.BGToolPaletteContribution;
import com.borkdominik.big.glsp.server.core.features.tool_palette.BGToolPaletteProvider;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteOperationContribution;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeOperationContribution;
import com.borkdominik.big.glsp.server.features.property_palette.BGPropertyPaletteContribution;
import com.borkdominik.big.glsp.server.features.property_palette.provider.BGPropertyPaletteProvider;
import com.google.inject.AbstractModule;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public abstract class BGRepresentationManifest extends AbstractModule {

   public String id() {
      return representation().getName();
   }

   public Named idNamed() {
      return Names.named(id());
   }

   public abstract Enumerator representation();

   public Named representationNamed() {
      return Names.named(representation().getName());
   }

   protected void bindToolPalette(final Class<? extends BGToolPaletteProvider> concrete) {
      install(new BGToolPaletteContribution(BGToolPaletteContribution.Options.builder()
         .manifest(this)
         .concrete(concrete)
         .build()));
   }

   protected void bindDefaultDeleteOperation(final Class<? extends BGDeleteHandler> defaultHandler) {
      install(new BGDeleteOperationContribution(BGDeleteOperationContribution.Options.builder()
         .manifest(this)
         .defaultHandler(defaultHandler)
         .build()));
   }

   protected void bindDefaultDirectEdit(final Class<? extends BGDirectEditHandler> defaultHandler) {
      install(new BGDirectEditContribution(BGDirectEditContribution.Options.builder()
         .manifest(this)
         .defaultDirectEditHandler(defaultHandler)
         .build()));
   }

   protected void bindDefaultReconnectOperation(final Class<? extends BGReconnectEdgeHandler> defaultHandler) {
      install(new BGReconnectEdgeOperationContribution(BGReconnectEdgeOperationContribution.Options.builder()
         .manifest(this)
         .defaultHandler(defaultHandler)
         .build()));
   }

   protected void bindDefaultPropertyPalette(final Class<? extends BGPropertyPaletteProvider> defaultProvider) {
      install(new BGPropertyPaletteContribution(BGPropertyPaletteContribution.Options.builder()
         .manifest(this)
         .defaultPaletteProvider(defaultProvider)
         .build()));
   }
}
