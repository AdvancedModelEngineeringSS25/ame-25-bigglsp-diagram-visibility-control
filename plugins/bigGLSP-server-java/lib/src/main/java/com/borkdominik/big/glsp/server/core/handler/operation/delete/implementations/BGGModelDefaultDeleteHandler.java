/********************************************************************************
 * Copyright (c) 2021 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.handler.operation.delete.implementations;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.gmodel.GModelDeleteOperationHandler;
import org.eclipse.glsp.server.operations.DeleteOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteHandler;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.core.model.BGTypeProviderAll;
import com.google.inject.Inject;

public class BGGModelDefaultDeleteHandler implements BGDeleteHandler {

   @Inject
   protected GModelDeleteOperationHandler deleteHandler;

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return Set.of(BGTypeProviderAll.instance); }

   @Override
   public Optional<Command> handleDelete(final DeleteOperation operation, final EObject object) {
      return deleteHandler.execute(operation);
   }

}
