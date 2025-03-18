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
package com.borkdominik.big.glsp.server.sdk.ui.builder;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.builder.impl.GNodeBuilder;
import org.eclipse.glsp.graph.util.GConstants;

import com.borkdominik.big.glsp.server.core.constants.BGCoreCSS;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCProvider;
import com.borkdominik.big.glsp.server.sdk.cdk.gmodel.GCModelList;
import com.borkdominik.big.glsp.server.sdk.gmodel.BCCompartmentBuilder;
import com.borkdominik.big.glsp.server.sdk.gmodel.BCLayoutOptions;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GBorderProperty;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GModelProperty;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GNotationProperty;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GPositionProperty;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GSizeProperty;

public abstract class GCNodeBuilder<TOrigin extends EObject>
   extends GCModelBuilder<TOrigin, GNode> {

   protected final String type;

   public GCNodeBuilder(final GCModelContext context, final TOrigin origin, final String type) {
      super(context, origin);
      this.type = type;
   }

   @Override
   protected List<GModelProperty> getRootGModelProperties() { return List.of(new GBorderProperty()); }

   @Override
   protected List<GNotationProperty> getRootGNotationProperties() {
      return List.of(new GPositionProperty(context), new GSizeProperty(context));
   }

   @Override
   protected GNode createRootGModel() {
      return new GNodeBuilder(type)
         .id(context.idGenerator.getOrCreateId(origin))
         .layout(GConstants.Layout.VBOX)
         .layoutOptions(new BCLayoutOptions().clearPadding())
         .addArgument("build_by", "gbuilder")
         .addCssClass(BGCoreCSS.NODE)
         .build();
   }

   @Override
   protected GCModelList<?, ?> createRootComponent(final GNode gmodelRoot) {
      var componentRoot = new GCModelList<>(context, origin, new BCCompartmentBuilder<>(origin, context)
         .withRootComponentLayout()
         .build());

      componentRoot.addAll(createComponentChildren(gmodelRoot, componentRoot));

      return componentRoot;
   }

   protected abstract List<GCProvider> createComponentChildren(GNode gmodelRoot, GCModelList<?, ?> root);
}
