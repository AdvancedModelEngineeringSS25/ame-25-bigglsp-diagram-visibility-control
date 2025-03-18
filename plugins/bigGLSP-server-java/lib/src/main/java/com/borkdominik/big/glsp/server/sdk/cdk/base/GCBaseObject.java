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
package com.borkdominik.big.glsp.server.sdk.cdk.base;

import org.eclipse.emf.ecore.EObject;

import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.borkdominik.big.glsp.server.sdk.cdk.GCObject;

public abstract class GCBaseObject<TOrigin extends EObject> implements GCObject {
   protected final GCModelContext context;
   protected final TOrigin origin;

   public GCBaseObject(final GCModelContext context, final TOrigin source) {
      super();
      this.context = context;
      this.origin = source;
   }
}
