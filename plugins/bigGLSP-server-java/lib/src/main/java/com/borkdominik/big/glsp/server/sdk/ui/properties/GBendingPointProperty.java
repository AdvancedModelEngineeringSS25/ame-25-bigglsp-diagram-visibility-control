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
package com.borkdominik.big.glsp.server.sdk.ui.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.emf.model.notation.Edge;

import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;

public class GBendingPointProperty implements GNotationProperty {
   protected final GCModelContext context;

   public GBendingPointProperty(final GCModelContext context) {
      super();
      this.context = context;
   }

   @Override
   public void assign(final EObject source, final GModelElement element) {
      if (element instanceof GEdge) {
         var edge = (GEdge) element;
         getNotationBendingPoints(source).ifPresent(bendingPoints -> {
            if (bendingPoints != null) {
               edge.getRoutingPoints().addAll(bendingPoints);
            }
         });
      } else {
         throw new IllegalStateException();
      }
   }

   protected Optional<List<GPoint>> getNotationBendingPoints(final EObject source) {
      return context.modelState.getIndex().getNotation(source, Edge.class)
         .map(edge -> edge.getBendPoints())
         .map(points -> {
            var bendPoints = new ArrayList<GPoint>();
            points.forEach(p -> bendPoints.add(GraphUtil.copy(p)));
            return bendPoints;
         });
   }

}
