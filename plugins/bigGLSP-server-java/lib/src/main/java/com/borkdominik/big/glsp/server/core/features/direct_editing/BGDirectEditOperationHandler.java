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
package com.borkdominik.big.glsp.server.core.features.direct_editing;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.borkdominik.big.glsp.server.core.features.suffix.BGSuffix;
import com.borkdominik.big.glsp.server.core.handler.operation.BGBasicOperationHandler;
import com.google.inject.Inject;

public class BGDirectEditOperationHandler
   extends BGBasicOperationHandler<ApplyLabelEditOperation> {
   private static Logger LOGGER = LogManager.getLogger(BGDirectEditOperationHandler.class.getSimpleName());

   @Inject
   protected BGDirectEditHandlerRegistry registry;
   @Inject
   protected BGSuffix suffix;

   @Override
   public Optional<Command> createCommand(final ApplyLabelEditOperation operation) {
      var representation = modelState.representation().getUnsafe();
      var labelId = operation.getLabelId();
      var elementId = suffix.extractId(labelId)
         .orElseThrow(() -> new GLSPServerException("No elementId found by extractor for label " + labelId));
      var semanticElement = modelState.getElementIndex().getOrThrow(elementId);

      return registry.retrieveOrDefault(representation, semanticElement.getClass())
         .handleDirectEdit(operation, semanticElement);
   }
}
