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

import java.util.List;

import org.eclipse.glsp.server.actions.Action;

import com.borkdominik.big.glsp.server.core.handler.action.integrations.BGEMFActionHandler;
import com.borkdominik.big.glsp.server.core.model.integrations.BGEMFSourceModelStorage;
import com.google.inject.Inject;

public class BGRequestNewFileActionHandler extends BGEMFActionHandler<BGRequestNewFileAction> {

   @Inject
   protected BGEMFSourceModelStorage storage;

   @Override
   protected List<Action> executeAction(final BGRequestNewFileAction actualAction) {
      var uri = storage.createSourceModel(actualAction);
      return List.of(new BGNewFileResponseAction(uri.toFileString()));
   }
}
