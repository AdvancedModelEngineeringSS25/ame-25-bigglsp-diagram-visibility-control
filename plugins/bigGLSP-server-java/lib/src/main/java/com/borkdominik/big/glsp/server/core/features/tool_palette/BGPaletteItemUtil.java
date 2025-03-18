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
package com.borkdominik.big.glsp.server.core.features.tool_palette;

import java.util.List;

import org.eclipse.glsp.server.actions.TriggerEdgeCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;
import org.eclipse.glsp.server.features.toolpalette.PaletteItem;

public final class BGPaletteItemUtil {
   private BGPaletteItemUtil() {}

   public static PaletteItem node(final String typeId, final String label, final String icon) {
      return new PaletteItem(typeId, label, new TriggerNodeCreationAction(typeId), icon);
   }

   public static PaletteItem edge(final String typeId, final String label, final String icon) {
      return new PaletteItem(typeId, label, new TriggerEdgeCreationAction(typeId), icon);
   }

   public static void sortAsListed(final List<PaletteItem> items) {
      items.forEach(i -> i.setSortString(String.valueOf(items.indexOf(i))));
   }
}
