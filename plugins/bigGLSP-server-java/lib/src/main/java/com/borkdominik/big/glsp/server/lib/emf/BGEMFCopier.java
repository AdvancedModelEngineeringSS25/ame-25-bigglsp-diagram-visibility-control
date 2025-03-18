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
package com.borkdominik.big.glsp.server.lib.emf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.types.EditorContext;

import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;

public class BGEMFCopier {
   public static void copySemantic(final BGEMFModelState modelState,
      final EcoreUtil.Copier semanticCopier,
      final Set<GModelElement> elements) {

      var nodes = elements.stream().filter(e -> e instanceof GNode).toList();
      var semanticNodes = nodes.stream().map(n -> modelState.getElementIndex().getOrThrow(n.getId())).toList();
      for (var element : semanticNodes) {
         copyEObject(semanticCopier, element);
      }

      var edges = elements.stream().filter(e -> e instanceof GEdge).toList();
      var semanticEdges = edges.stream().map(e -> modelState.getElementIndex().getOrThrow(e.getId()))
         .filter(e -> !EcoreUtil.isAncestor(semanticNodes, e))
         .toList();
      for (var element : semanticEdges) {
         copyEObject(semanticCopier, element);
      }
   }

   public static void copyNotation(final BGEMFModelState modelState, final EMFIdGenerator idGenerator,
      final EcoreUtil.Copier semanticCopier, final EditorContext editorContext) {
      var copier = new EcoreUtil.Copier();

      for (var entry : semanticCopier.entrySet()) {
         var originalSemantic = entry.getKey();
         var copySemantic = entry.getValue();

         if (modelState.getElementIndex().hasNotation(originalSemantic)) {
            var notation = copyEObject(copier, modelState.getElementIndex().getNotationOrThrow(originalSemantic));
            var semanticElementReference = NotationFactory.eINSTANCE.createSemanticElementReference();
            semanticElementReference.setElementId(idGenerator.getOrCreateId(copySemantic));
            notation.setSemanticElement(semanticElementReference);

            if (notation instanceof Shape s) {
               var p = s.getPosition();
               s.setPosition(GraphUtil.point(p.getX() + 10, p.getY() + 10));
            }
         }
      }
   }

   private static <T extends EObject> T copyEObject(final EcoreUtil.Copier copier, final T originalObject) {
      if (originalObject != null) {
         var copiedObject = copier.copy(originalObject);

         copier.copyReferences();

         var originalContainer = originalObject.eContainer();
         var containmentReference = originalObject.eContainmentFeature();

         var existingValue = originalContainer.eGet(containmentReference);
         if (existingValue instanceof Collection c) {
            c.add(copiedObject);
         } else {
            originalContainer.eSet(containmentReference, copiedObject);
         }

         updateReferences(originalObject, copiedObject, copier);

         return (T) copiedObject;
      }

      return null;
   }

   private static void updateReferences(final EObject original, final EObject copiedObject,
      final EcoreUtil.Copier copier) {
      for (var reference : original.eClass().getEAllReferences()) {
         if (reference.isContainment() || !reference.isContainer() || !reference.isChangeable()) {
            continue;
         }
         var value = original.eGet(reference);
         if (value instanceof EObject) {
            var originalReferencedObject = (EObject) value;
            var copiedReferencedObject = copier.get(originalReferencedObject);
            if (copiedReferencedObject != null) {
               copiedObject.eSet(reference, copiedReferencedObject);
            }
         } else if (value instanceof Collection<?>) {
            var originalReferencedObjects = (Collection<?>) value;
            var copiedReferencedObjects = new ArrayList<EObject>();
            for (var originalReferencedObject : originalReferencedObjects) {
               var copiedReferencedObject = copier.get(originalReferencedObject);
               if (copiedReferencedObject != null) {
                  copiedReferencedObjects.add(copiedReferencedObject);
               } else {
                  copiedReferencedObjects.add((EObject) originalReferencedObject);
               }
            }
            copiedObject.eSet(reference, copiedReferencedObjects);
         }
      }
   }
}
