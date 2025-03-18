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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;

import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCBaseObject;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCProvider;
import com.borkdominik.big.glsp.server.sdk.cdk.gmodel.GCModelElement;
import com.borkdominik.big.glsp.server.sdk.cdk.gmodel.GCModelList;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GModelProperty;
import com.borkdominik.big.glsp.server.sdk.ui.properties.GNotationProperty;

public abstract class GCModelBuilder<TOrigin extends EObject, TElement extends GModelElement>
   extends GCBaseObject<TOrigin> {

   public GCModelBuilder(final GCModelContext context, final TOrigin origin) {
      super(context, origin);
   }

   protected List<GModelProperty> getRootGModelProperties() { return List.of(); }

   protected List<GNotationProperty> getRootGNotationProperties() { return List.of(); }

   protected abstract TElement createRootGModel();

   protected abstract GCModelList<?, ?> createRootComponent(TElement gmodelRoot);

   public TElement buildGModel() {
      var gmodelRoot = createRootGModel();
      getRootGModelProperties().forEach(p -> p.assign(gmodelRoot));
      getRootGNotationProperties().forEach(p -> p.assign(origin, gmodelRoot));

      gmodelRoot.getChildren().add(createRootComponent(gmodelRoot).provideGModel());

      return gmodelRoot;
   }

   protected List<GCProvider> providersOf(final List<? extends EObject> elements) {
      var list = new ArrayList<GCProvider>();
      list.addAll(elements.stream()
         .map(e -> context.mapHandler.handle(e))
         .map(e -> new GCModelElement<>(context, origin, e))
         .collect(Collectors.toList()));
      list.addAll(elements.stream()
         .map(e -> context.mapHandler.handleSiblings(e))
         .flatMap(Collection::stream)
         .map(e -> new GCModelElement<>(context, origin, e))
         .collect(Collectors.toList()));
      return list;
   }
}
