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
package com.borkdominik.big.glsp.server.elements.handler.operations.utils;

import java.util.Optional;

import org.eclipse.glsp.graph.GCompartment;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.utils.LayoutUtil;

import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.core.model.BGModelState;

public class BGCreateLocationAwareNodeHelper {

   protected final BGModelState modelState;

   public BGCreateLocationAwareNodeHelper(final BGModelState modelState) {
      this.modelState = modelState;
   }

   public Optional<GPoint> relativeLocationOf(final CreateNodeOperation operation) {
      var absoluteLocation = operation.getLocation();
      var container = modelState.getIndex().get(operation.getContainerId()).orElseGet(modelState::getRoot);
      return absoluteLocation.map(location -> LayoutUtil.getRelativeLocation(location,
         getChildrenContainer(container).orElse(container)));
   }

   public GPoint relativeLocationOf(final String containerId, final double x,
      final double y) {
      var absoluteLocation = GraphUtil.point(x, y);
      var container = modelState.getIndex().get(containerId).orElseGet(modelState::getRoot);
      return LayoutUtil.getRelativeLocation(absoluteLocation,
         getChildrenContainer(container).orElse(container));
   }

   public Optional<GModelElement> getChildrenContainer(final GModelElement container) {
      var rootComponentContainer = container.getChildren()
         .stream()
         .filter(GCompartment.class::isInstance)
         .filter(comp -> comp.getType().equals(BGCoreTypes.COMPARTMENT_ROOT_COMPONENT))
         .findFirst();

      if (rootComponentContainer.isPresent()) {
         return rootComponentContainer.flatMap(c -> {
            Optional<GModelElement> child = c.getChildren().stream()
               .filter(GCompartment.class::isInstance)
               .filter(comp -> comp.getType().equals(BGCoreTypes.COMPARTMENT_CONTAINER))
               .findFirst();

            return child;
         });
      }

      return Optional.empty();
   }
}
