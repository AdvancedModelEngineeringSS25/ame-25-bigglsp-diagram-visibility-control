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
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;

public class BGApplyLabelEditOperationLabelExtractor extends BGLabelExtractor {

   public String extractElementId(final ApplyLabelEditOperation operation) {
      return extractElementId(operation.getLabelId());
   }

   public <TElement extends EObject> TElement extractElement(final ApplyLabelEditOperation operation,
      final Class<TElement> elementType) {
      return extractElement(operation.getLabelId(), elementType);
   }

   public GLabel extractLabel(final ApplyLabelEditOperation operation) {
      return extractLabel(operation.getLabelId());
   }

   public String extractLabelSuffix(final ApplyLabelEditOperation operation) {
      return extractLabelSuffix(operation.getLabelId());
   }
}
