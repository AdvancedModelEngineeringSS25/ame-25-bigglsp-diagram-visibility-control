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
package com.borkdominik.big.glsp.server.elements.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.glsp.server.operations.DeleteOperation;

import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;
import com.borkdominik.big.glsp.server.core.commands.emf.notation.BGEMFDeleteNotationCommand;
import com.borkdominik.big.glsp.server.core.commands.semantic.BGDeleteElementSemanticCommand;
import com.borkdominik.big.glsp.server.elements.configuration.BGEdgeConfiguration;
import com.borkdominik.big.glsp.server.elements.configuration.BGNodeConfiguration;

public class BGEMFDeleteElementCommand extends CompoundCommand {
   private static Logger LOGGER = LogManager.getLogger(BGEMFDeleteElementCommand.class);

   protected final BGEMFCommandContext context;
   protected EObject element;
   protected Set<String> deletedIds = new HashSet<>();

   public BGEMFDeleteElementCommand(final BGEMFCommandContext context, final EObject element) {
      this.context = context;
      this.element = element;

      createCommands(element);
   }

   public static class DeleteCrossReferencer extends UsageCrossReferencer {
      private final Set<EReference> crossReferences = new HashSet<>();
      private final Set<EObject> referencedObjects = new HashSet<>();
      protected EObject targetObject;

      public DeleteCrossReferencer(final EObject root) {
         super(root);
      }

      public Collection<Setting> find(final EObject element) {
         this.clear();
         this.targetObject = element;
         return findUsage(element);
      }

      @Override
      protected boolean crossReference(final EObject eObject, final EReference eReference,
         final EObject crossReferencedEObject) {
         var isDescendant = EcoreUtil.isAncestor(eObject, targetObject);

         if (isDescendant) {
            return false;
         }

         var isAncestor = targetObject != eObject
            && EcoreUtil.isAncestor(targetObject, eObject);
         var isDirected = crossReferencedEObject == targetObject;

         if (isAncestor || isDirected) {
            crossReferences.add(eReference);
            referencedObjects.add(eObject);
            return true;
         }
         return false;
      }

      public Set<EReference> getCrossReferences() { return crossReferences; }

      public Set<EObject> getReferencedObjects() { return referencedObjects; }
   }

   public void createCommands(final EObject element) {
      var referencer = new DeleteCrossReferencer(context.modelState().getSemanticModel());
      referencer.find(element);
      var references = referencer.getReferencedObjects();

      for (var reference : references) {
         var referenceId = context.idGenerator().getOrCreateId(reference);
         if (!deletedIds.contains(referenceId)) {
            deletedIds.add(referenceId);

            context.deleteRegistry().get(context.modelState().representation().getUnsafe(), reference.getClass())
               .ifPresentOrElse((handler) -> {
                  handler.handleDelete(new DeleteOperation(List.of(referenceId)), reference).ifPresent(this::append);
               }, () -> {
                  context.elementConfig().get(reference.getClass()).ifPresent(config -> {
                     var isAncestor = element != reference && EcoreUtil.isAncestor(element, reference);

                     if (config instanceof BGNodeConfiguration && isAncestor) {
                        createCommands(reference);
                     } else if (config instanceof BGEdgeConfiguration) {
                        createCommands(reference);
                     }
                  });
               });
         }
      }

      this.append(new BGDeleteElementSemanticCommand(context, context.modelState().getSemanticModel(), element));
      this.append(new BGEMFDeleteNotationCommand(context, element));
   }

}
