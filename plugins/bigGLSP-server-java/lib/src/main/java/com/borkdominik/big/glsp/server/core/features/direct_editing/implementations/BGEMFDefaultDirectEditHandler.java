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
package com.borkdominik.big.glsp.server.core.features.direct_editing.implementations;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;
import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditHandler;
import com.borkdominik.big.glsp.server.core.features.direct_editing.label_edit.BGLabelEditHandlerRegistry;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.core.model.BGTypeProviderAll;
import com.google.inject.Inject;

public class BGEMFDefaultDirectEditHandler implements BGDirectEditHandler {

   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected BGEMFCommandContext commandContext;
   @Inject
   protected BGLabelEditHandlerRegistry labelEditHandlerRegistry;

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return Set.of(BGTypeProviderAll.instance); }

   @Override
   public Optional<Command> handleDirectEdit(final ApplyLabelEditOperation operation, final EObject element) {
      var handlers = this.labelEditHandlerRegistry.retrieve(modelState.representation().getUnsafe(),
         element.getClass());
      var compoundCommand = new CompoundCommand();

      handlers.forEach(handler -> {
         if (handler.matches(operation)) {
            var command = handler.handle(operation, element);
            compoundCommand.append(command);
         }
      });

      return Optional.of(compoundCommand);
   }
}
