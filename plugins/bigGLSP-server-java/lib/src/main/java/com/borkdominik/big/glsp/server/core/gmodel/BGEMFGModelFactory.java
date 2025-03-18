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
package com.borkdominik.big.glsp.server.core.gmodel;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.notation.EMFNotationGModelFactory;

import com.borkdominik.big.glsp.server.core.features.suffix.BGIdCountContextGenerator;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;

import jakarta.inject.Inject;

public abstract class BGEMFGModelFactory extends EMFNotationGModelFactory {
   @Inject
   protected BGEMFGModelMapHandler mapHandler;
   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected BGIdCountContextGenerator idCountGenerator;

   protected abstract Collection<? extends EObject> childrenOf(EObject semanticModel);

   @Override
   protected void fillRootElement(final EObject semanticModel, final Diagram notationModel, final GModelRoot newRoot) {
      idCountGenerator.clearAll();

      if (newRoot instanceof GGraph graph) {
         newRoot.setId(idGenerator.getOrCreateId(semanticModel));

         var children = childrenOf(semanticModel).stream()
            .map(element -> {
               var current = mapHandler.handle(element);
               var siblings = mapHandler.handleSiblings(element);

               var gmodels = new ArrayList<GModelElement>();
               gmodels.add(current);
               gmodels.addAll(siblings);

               return gmodels;
            }).toList();

         children.forEach(elements -> {
            graph.getChildren().addAll(elements);
         });
      }

   }
}
