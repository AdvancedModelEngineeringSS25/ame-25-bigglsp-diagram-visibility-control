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
package com.borkdominik.big.glsp.server.sdk.gmodel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GCompartment;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.AbstractGCompartmentBuilder;
import org.eclipse.glsp.graph.util.GConstants;

import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.core.constants.BGLayoutConstants;
import com.borkdominik.big.glsp.server.core.constants.BGPaddingValues;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;

public class BCCompartmentBuilder<TBuilder extends BCCompartmentBuilder<TBuilder>>
   extends AbstractGCompartmentBuilder<GCompartment, TBuilder> {
   protected EObject source;
   protected GCModelContext context;

   public BCCompartmentBuilder(final EObject source, final GCModelContext context) {
      this(source, context, DefaultTypes.COMPARTMENT);
   }

   public BCCompartmentBuilder(final EObject source, final GCModelContext context, final String type) {
      super(type);

      this.source = source;
      this.context = context;

      this.prepareProperties();
   }

   protected void prepareProperties() {
      this.id(context.idCountGenerator.getOrCreateId(source));
   }

   @Deprecated
   public TBuilder withHeaderLayout() {
      this.type(DefaultTypes.COMPARTMENT_HEADER)
         .layout(GConstants.Layout.VBOX)
         .layoutOptions(new BCLayoutOptions()
            .padding(BGPaddingValues.LEVEL_1, BGPaddingValues.LEVEL_2)
            .hGrab(true)
            .hAlign(GConstants.HAlign.CENTER));

      return self();
   }

   public TBuilder withRootComponentLayout() {
      this.withVBoxLayout()
         .addLayoutOptions(new BCLayoutOptions()
            .clearPadding()
            .hGrab(true)
            .vGrab(true)
            .hAlign(GConstants.HAlign.LEFT));
      this.type(BGCoreTypes.COMPARTMENT_ROOT_COMPONENT);
      return self();
   }

   public TBuilder withVBoxLayout() {
      this.type(DefaultTypes.COMPARTMENT)
         .layout(GConstants.Layout.VBOX)
         .layoutOptions(new BCLayoutOptions()
            .clearPadding()
            .hGrab(true)
            .hAlign(GConstants.HAlign.LEFT));

      return self();
   }

   public TBuilder withHBoxLayout() {
      this.type(DefaultTypes.COMPARTMENT)
         .layout(GConstants.Layout.HBOX)
         .layoutOptions(new BCLayoutOptions()
            .clearPadding()
            .hAlign(GConstants.HAlign.LEFT));

      return self();
   }

   public TBuilder withFreeformLayout() {
      this.type(BGCoreTypes.COMPARTMENT_CONTAINER)
         .layout(BGLayoutConstants.FREEFORM)
         .layoutOptions(new BCLayoutOptions()
            .clearPadding()
            .hAlign(GConstants.HAlign.LEFT));

      return self();
   }

   public TBuilder addLayoutOptions(final Map<String, Object> layoutOptions) {
      if (this.layoutOptions == null) {
         this.layoutOptions = new LinkedHashMap<>();
      }
      this.layoutOptions.putAll(layoutOptions);
      return self();
   }

   @Override
   protected GCompartment instantiate() {
      return GraphFactory.eINSTANCE.createGCompartment();
   }

   @Override
   protected TBuilder self() {
      return (TBuilder) this;
   }
}
