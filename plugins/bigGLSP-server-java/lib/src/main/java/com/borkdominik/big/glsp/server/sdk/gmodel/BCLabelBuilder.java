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
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.AbstractGLabelBuilder;

import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;

public class BCLabelBuilder
   extends AbstractGLabelBuilder<GLabel, BCLabelBuilder> {

   protected final EObject source;
   protected final GCModelContext context;

   public BCLabelBuilder(final EObject source, final GCModelContext context) {
      this(source, context, BGCoreTypes.LABEL_TEXT);
   }

   public BCLabelBuilder(final EObject source, final GCModelContext context, final String type) {
      super(type);

      this.source = source;
      this.context = context;

      this.prepare();
   }

   protected void prepare() {
      this.id(context.idCountGenerator.getOrCreateId(source));
   }

   public BCLabelBuilder text(final String seperator, final String... text) {
      return text(String.join(seperator, text));
   }

   @Override
   protected GLabel instantiate() {
      return GraphFactory.eINSTANCE.createGLabel();
   }

   @Override
   protected BCLabelBuilder self() {
      return this;
   }
}
