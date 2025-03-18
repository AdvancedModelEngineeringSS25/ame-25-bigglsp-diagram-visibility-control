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
package com.borkdominik.big.glsp.server.core.commands.emf.notation;

import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;

public class BGEMFCreateEdgeNotationCommand extends BGEMFNotationCommand {

   public static record Options(
      EMFIdGenerator idGenerator,
      Supplier<EObject> element) {}

   protected Options options;

   public BGEMFCreateEdgeNotationCommand(final BGEMFCommandContext context, final Options options) {
      super(context);
      this.options = options;
   }

   @Override
   protected void doExecute() {
      var elementId = this.options.idGenerator.getOrCreateId(this.options.element.get());
      var newEdge = NotationFactory.eINSTANCE.createEdge();
      var semanticReference = NotationFactory.eINSTANCE.createSemanticElementReference();
      semanticReference.setElementId(elementId);

      newEdge.setSemanticElement(semanticReference);

      context.modelState().getNotationModel().getElements().add(newEdge);
   }
}
