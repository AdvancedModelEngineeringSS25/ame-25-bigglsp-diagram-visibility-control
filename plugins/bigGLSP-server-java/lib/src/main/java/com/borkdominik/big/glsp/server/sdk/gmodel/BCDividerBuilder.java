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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.AbstractGNodeBuilder;
import org.eclipse.glsp.graph.builder.impl.GLabelBuilder;
import org.eclipse.glsp.graph.util.GConstants;

import com.borkdominik.big.glsp.server.core.constants.BGCoreCSS;
import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;

public class BCDividerBuilder
   extends AbstractGNodeBuilder<GNode, BCDividerBuilder> {

   protected EObject source;
   protected GCModelContext context;

   public BCDividerBuilder(final EObject source, final GCModelContext context) {
      super(BGCoreTypes.DIVIDER);
      this.source = source;
      this.context = context;

      prepare();
   }

   protected void prepare() {
      var options = new BCLayoutOptions().hGrab(true);
      this
         .id(context.idCountGenerator.getOrCreateId(source))
         .layout(GConstants.Layout.HBOX)
         .layoutOptions(options);
   }

   public BCDividerBuilder addSubtitle(final String subtitle) {
      add(new GLabelBuilder()
         .id(context.idCountGenerator.getOrCreateId(source))
         .text(subtitle)
         .addCssClass(BGCoreCSS.DIVIDER_SUBTITLE)
         .build());
      return self();
   }

   @Override
   protected GNode instantiate() {
      return GraphFactory.eINSTANCE.createGNode();
   }

   @Override
   protected BCDividerBuilder self() {
      return this;
   }
}
