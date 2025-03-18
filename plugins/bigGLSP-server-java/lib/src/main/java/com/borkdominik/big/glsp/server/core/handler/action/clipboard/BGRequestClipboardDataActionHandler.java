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
package com.borkdominik.big.glsp.server.core.handler.action.clipboard;

import java.util.List;
import java.util.Map;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.clipboard.RequestClipboardDataAction;
import org.eclipse.glsp.server.features.clipboard.SetClipboardDataAction;
import org.eclipse.glsp.server.gmodel.GModelRequestClipboardDataActionHandler;
import org.eclipse.glsp.server.gson.GraphGsonConfigurationFactory;

import com.google.gson.JsonArray;
import com.google.inject.Inject;

public class BGRequestClipboardDataActionHandler extends GModelRequestClipboardDataActionHandler {
   public static String CLIPBOARD_SELECTED_ELEMENTS = "selectedElements";
   public static String CLIPBOARD_ROOT = "root";
   private final List<String> ignoreList = List.of();

   @Inject
   public BGRequestClipboardDataActionHandler(final GraphGsonConfigurationFactory gsonConfigurator) {
      super(gsonConfigurator);
   }

   @Override
   public List<Action> executeAction(final RequestClipboardDataAction action) {
      JsonArray selectedElementsJsonArray = new JsonArray();

      var index = modelState.getIndex();
      var rootJson = gson.toJsonTree(index.getRoot());

      var selectedElements = index.getAll(action.getEditorContext().getSelectedElementIds());
      selectedElements.stream()
         .filter(element -> !ignoreList.contains(element.getType()))
         .map(gson::toJsonTree).forEach(selectedElementsJsonArray::add);

      return listOf(new SetClipboardDataAction(
         Map.of(CLIPBOARD_SELECTED_ELEMENTS, selectedElementsJsonArray.toString(), CLIPBOARD_ROOT,
            rootJson.toString())));
   }

}
