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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;

import com.borkdominik.big.glsp.server.core.handler.action.BGActionHandler;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.features.outline.generator.BGOutlineGenerator;
import com.google.inject.Inject;

public class BGRequestOutlineHandler extends BGActionHandler<BGRequestOutlineAction> {

   private static final Logger LOG = LogManager.getLogger(BGRequestOutlineHandler.class);

   @Inject
   protected BGModelState modelState;

   @Inject
   protected BGOutlineGeneratorRegistry registry;

   @Inject
   protected BGOutlineGenerator defaultGenerator;

   @Override
   protected List<Action> executeAction(final BGRequestOutlineAction actualAction) {
      return modelState.representation().get().map(representation -> {

         var generator = registry.get(representation).orElse(defaultGenerator);

         return List.<Action> of(new BGSetOutlineAction(generator.generate()));
      }).orElse(List.of());
   }
}
