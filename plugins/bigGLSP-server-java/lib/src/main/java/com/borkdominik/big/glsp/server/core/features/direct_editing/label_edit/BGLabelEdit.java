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
package com.borkdominik.big.glsp.server.core.features.direct_editing.label_edit;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;

import com.borkdominik.big.glsp.server.core.commands.BGCommandContext;
import com.borkdominik.big.glsp.server.core.features.direct_editing.extractor.BGApplyLabelEditOperationLabelExtractor;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.google.inject.Inject;

public abstract class BGLabelEdit<TElement extends EObject> implements BGLabelEditHandler {
   protected final Map<String, List<String>> supported;
   protected final Enumerator representation;
   protected final Set<BGTypeProvider> handledElementTypes;

   @Inject
   protected BGModelState modelState;
   @Inject
   protected BGApplyLabelEditOperationLabelExtractor labelExtractor;
   @Inject
   protected BGCommandContext context;

   public BGLabelEdit(final Enumerator representation, final Set<BGTypeProvider> handledElementTypes,
      final Map<String, List<String>> supported) {
      super();
      this.representation = representation;
      this.handledElementTypes = handledElementTypes;
      this.supported = supported;
   }

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return handledElementTypes; }

   @Override
   public boolean matches(final ApplyLabelEditOperation operation) {
      var extractedLabel = labelExtractor.extractLabel(operation);
      var extractedSuffix = labelExtractor.extractLabelSuffix(operation);

      for (var entry : supported.entrySet()) {
         if (extractedLabel.getType().equals(entry.getKey())
            && entry.getValue().stream().anyMatch(v -> extractedSuffix.equals(v))) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Command handle(final ApplyLabelEditOperation operation, final EObject element) {
      return doHandle(operation, (TElement) element);
   }

   protected abstract Command doHandle(ApplyLabelEditOperation operation, TElement element);
}
