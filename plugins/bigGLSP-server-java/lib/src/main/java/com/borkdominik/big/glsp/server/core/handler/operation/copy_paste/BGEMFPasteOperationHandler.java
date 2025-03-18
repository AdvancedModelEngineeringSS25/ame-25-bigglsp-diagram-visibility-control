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
package com.borkdominik.big.glsp.server.core.handler.operation.copy_paste;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.gson.GraphGsonConfigurationFactory;
import org.eclipse.glsp.server.operations.PasteOperation;

import com.borkdominik.big.glsp.server.core.commands.BGRecordingRunnableCommand;
import com.borkdominik.big.glsp.server.core.handler.action.clipboard.BGRequestClipboardDataActionHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.BGEMFOperationHandler;
import com.borkdominik.big.glsp.server.lib.emf.BGEMFCopier;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class BGEMFPasteOperationHandler
      extends BGEMFOperationHandler<PasteOperation> {

   protected final Gson gson;

   @Inject
   protected EMFIdGenerator idGenerator;

   @Inject
   public BGEMFPasteOperationHandler(final GraphGsonConfigurationFactory gsonConfigurator) {
      var builder = gsonConfigurator.configureGson();
      gson = builder.create();
   }

   @Override
   public Optional<Command> createCommand(final PasteOperation operation) {
      var copiedElements = getCopiedElements(
            operation.getClipboardData().get(BGRequestClipboardDataActionHandler.CLIPBOARD_SELECTED_ELEMENTS));

      if (copiedElements.isEmpty()) {
         return Optional.empty();
      }

      var filteredElements = filterElements(copiedElements);

      EcoreUtil.Copier semanticCopier = new EcoreUtil.Copier();
      var compoundCommand = new CompoundCommand();

      compoundCommand.append(new BGRecordingRunnableCommand(modelState.getSemanticModel(), () -> {
         BGEMFCopier.copySemantic(modelState, semanticCopier, filteredElements);
      }));
      compoundCommand.append(new BGRecordingRunnableCommand(modelState.getNotationModel(), () -> {
         BGEMFCopier.copyNotation(modelState, idGenerator, semanticCopier, operation.getEditorContext());
      }));

      return Optional.of(compoundCommand);
   }

   protected Set<GModelElement> getCopiedElements(final String jsonString) {
      var elements = gson.fromJson(jsonString, GModelElement[].class);
      return elements != null
            ? new HashSet<>(
                  Arrays.asList(elements).stream()
                        .filter(e -> this.modelState.getElementIndex().getGModel(e.getId()).isPresent())
                        .map(e -> this.modelState.getElementIndex().getGModelOrThrow(e.getId()))
                        .toList())
            : Collections.emptySet();
   }

   protected Set<GModelElement> filterElements(final Set<GModelElement> elements) {
      return elements.stream()
            .filter(e -> {
               var isNotAncestor = elements.stream().noneMatch(el -> el != e && EcoreUtil.isAncestor(el, e));
               return isNotAncestor;
            })
            .collect(Collectors.toSet());
   }
}
