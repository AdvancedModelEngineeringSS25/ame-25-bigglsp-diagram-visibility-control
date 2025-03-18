/********************************************************************************
 * Copyright (c) 2024 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.sdk.cdk.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.builder.impl.GLabelBuilder;

import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCBaseObject;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCBuildable;

public class GCNone extends GCBaseObject<EObject> implements GCBuildable<GModelElement> {

   public GCNone(final GCModelContext context, final EObject origin) {
      super(context, origin);
   }

   @Override
   public boolean isVisible() { return false; }

   @Override
   public GModelElement provideGModel() {
      return new GLabelBuilder()
         .id(context.idCountGenerator.getOrCreateId(origin))
         .type(BGCoreTypes.LABEL_TEXT)
         .text("This should not be visible")
         .build();
   }

}
