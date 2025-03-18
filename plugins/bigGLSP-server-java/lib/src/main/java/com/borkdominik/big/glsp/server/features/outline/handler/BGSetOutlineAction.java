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

import java.util.List;

import org.eclipse.glsp.server.actions.ResponseAction;

import com.borkdominik.big.glsp.server.features.outline.model.OutlineTreeNode;

public class BGSetOutlineAction extends ResponseAction {

   private List<OutlineTreeNode> outlineTreeNodes = List.of();

   public static final String KIND = "setOutline";

   public BGSetOutlineAction() {
      super(KIND);
   }

   public BGSetOutlineAction(final List<OutlineTreeNode> outlineTreeNodes) {
      super(KIND);
      this.outlineTreeNodes = outlineTreeNodes;
   }

   public List<OutlineTreeNode> getOutlineTreeNodes() { return outlineTreeNodes; }

   public void setOutlineTreeNodes(final List<OutlineTreeNode> outlineTreeNodes) {
      this.outlineTreeNodes = outlineTreeNodes;
   }

}
