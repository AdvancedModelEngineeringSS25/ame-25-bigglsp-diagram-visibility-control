/********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.features.direct_editing.extractor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.borkdominik.big.glsp.server.core.features.suffix.BGSuffix;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.google.inject.Inject;

public class BGLabelExtractor {

   @Inject
   protected BGSuffix suffix;
   @Inject
   protected BGModelState modelState;

   public String extractElementId(final String labelId) {
      return suffix.extractId(labelId)
         .orElseThrow(() -> new GLSPServerException("No elementId found by extractor for label " + labelId));
   }

   public <TElement extends EObject> TElement extractElement(final String labelId,
      final Class<TElement> elementType) {
      var elementId = extractElementId(labelId);
      return modelState.getElementIndex().getOrThrow(elementId, elementType);
   }

   public GLabel extractLabel(final String labelId) {
      return modelState.getElementIndex().getGModelOrThrow(labelId, GLabel.class);
   }

   public String extractLabelSuffix(final String labelId) {
      return suffix.extractSuffix(labelId)
         .orElseThrow(() -> new GLSPServerException("No suffix found by extractor for label " + labelId));
   }
}
