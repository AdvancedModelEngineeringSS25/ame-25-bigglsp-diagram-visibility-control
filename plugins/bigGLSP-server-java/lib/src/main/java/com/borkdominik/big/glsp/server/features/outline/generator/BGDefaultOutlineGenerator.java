/********************************************************************************
 * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.features.outline.generator;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.features.outline.model.OutlineTreeNode;
import com.google.inject.Inject;

public class BGDefaultOutlineGenerator implements BGOutlineGenerator {

   @Inject
   protected BGModelState modelState;

   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   public List<OutlineTreeNode> generate() {
      var root = modelState.getRoot();
      var model = modelState.getElementIndex().getOrThrow(root.getId());

      var outlineTreeNodes = model.eContents().stream()
         .filter(this::filter)
         .map(this::map)
         .collect(Collectors.toList());

      return List.of(new OutlineTreeNode("Model", root.getId(), outlineTreeNodes, "model", true));
   }

   protected boolean filter(final EObject eObject) {
      return true;
   }

   protected OutlineTreeNode map(final EObject element) {
      var label = labelOf(element);
      var children = childrenOf(element);
      var icon = iconOf(element);

      return new OutlineTreeNode(label, EcoreUtil.getURI(element).fragment(), children, icon);
   }

   protected String labelOf(final EObject element) {
      var prefix = element.getClass().getSimpleName().split("Impl")[0];
      var label = idGenerator.getOrCreateId(element);

      return "[" + prefix + "] " + label;
   }

   protected String iconOf(final EObject element) {
      return "element";
   }

   protected List<OutlineTreeNode> childrenOf(final EObject element) {
      if (element.eContents().isEmpty()) {
         return List.of();
      }

      return element.eContents().stream()
         .filter(this::filter)
         .map(elem -> map(elem))
         .collect(Collectors.toList());
   }
}
