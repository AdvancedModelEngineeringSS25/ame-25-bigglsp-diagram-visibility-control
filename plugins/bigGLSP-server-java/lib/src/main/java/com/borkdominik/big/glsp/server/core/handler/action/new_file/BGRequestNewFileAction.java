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
package com.borkdominik.big.glsp.server.core.handler.action.new_file;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.glsp.server.actions.RequestAction;

public class BGRequestNewFileAction extends RequestAction<BGNewFileResponseAction> {
   public static final String KIND = "requestNewFile";

   private final Map<String, String> options;
   private String diagramType;

   public BGRequestNewFileAction() {
      super(KIND);
      options = new HashMap<>();
   }

   public BGRequestNewFileAction(final Map<String, String> options) {
      super(KIND);
      this.options = options;
   }

   public Map<String, String> getOptions() { return options; }

   public String getDiagramType() { return diagramType; }

   public void setDiagramType(final String diagramType) { this.diagramType = diagramType; }
}
