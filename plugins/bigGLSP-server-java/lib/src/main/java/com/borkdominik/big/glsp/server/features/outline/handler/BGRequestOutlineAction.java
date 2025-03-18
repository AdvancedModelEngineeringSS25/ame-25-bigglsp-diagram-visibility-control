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
package com.borkdominik.big.glsp.server.features.outline.handler;

import org.eclipse.glsp.server.actions.RequestAction;

public class BGRequestOutlineAction extends RequestAction<BGSetOutlineAction> {
   public static final String KIND = "requestOutline";

   public BGRequestOutlineAction() {
      super(KIND);

   }

}
