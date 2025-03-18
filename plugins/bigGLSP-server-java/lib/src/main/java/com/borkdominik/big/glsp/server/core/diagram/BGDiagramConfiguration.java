/********************************************************************************
 * Copyright (c) 2021-2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.diagram.BaseDiagramConfiguration;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.types.EdgeTypeHint;
import org.eclipse.glsp.server.types.ShapeTypeHint;

import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.google.inject.Inject;

public class BGDiagramConfiguration extends BaseDiagramConfiguration {

   @Inject
   protected Map<Enumerator, Set<BGDiagramConfigurationProvider>> diagramConfigurations;
   @Inject
   protected Map<Enumerator, Set<BGDiagramConfigurationProvider.Node>> nodeConfigurations;
   @Inject
   protected Map<Enumerator, Set<BGDiagramConfigurationProvider.Edge>> edgeConfigurations;

   @Override
   public List<EdgeTypeHint> getEdgeTypeHints() {
      var hints = new ArrayList<EdgeTypeHint>();

      edgeConfigurations.values().stream().flatMap(c -> c.stream())
         .flatMap(c -> c.getEdgeTypeHints().stream())
         .forEach(hints::add);

      return hints;
   }

   @Override
   public List<ShapeTypeHint> getShapeTypeHints() {
      var hints = new ArrayList<ShapeTypeHint>();
      var graphContainableElements = new ArrayList<String>();

      nodeConfigurations.values().stream().flatMap(c -> c.stream())
         .flatMap(c -> c.getGraphContainableElements().stream())
         .forEach(graphContainableElements::add);

      hints.add(
         new ShapeTypeHint(DefaultTypes.GRAPH, false, false, false, false,
            graphContainableElements));

      nodeConfigurations.values().stream().flatMap(c -> c.stream())
         .flatMap(c -> c.getShapeTypeHints().stream())
         .forEach(hints::add);

      return hints;
   }

   @Override
   public Map<String, EClass> getTypeMappings() {
      Map<String, EClass> mappings = DefaultTypes.getDefaultTypeMappings();

      // COMMONS
      mappings.put(BGCoreTypes.LABEL_NAME, GraphPackage.Literals.GLABEL);
      mappings.put(BGCoreTypes.LABEL_TEXT, GraphPackage.Literals.GLABEL);
      mappings.put(BGCoreTypes.LABEL_EDGE_NAME, GraphPackage.Literals.GLABEL);
      mappings.put(BGCoreTypes.ICON_CSS, GraphPackage.Literals.GCOMPARTMENT);
      mappings.put(BGCoreTypes.DIVIDER, GraphPackage.Literals.GNODE);

      diagramConfigurations.values().stream().flatMap(c -> c.stream())
         .map(c -> c.getTypeMappings())
         .forEach(typeMappings -> {
            mappings.putAll(typeMappings);
         });

      nodeConfigurations.values().stream().flatMap(c -> c.stream())
         .map(c -> c.getTypeMappings())
         .forEach(typeMappings -> {
            mappings.putAll(typeMappings);
         });

      edgeConfigurations.values().stream().flatMap(c -> c.stream())
         .map(c -> c.getTypeMappings())
         .forEach(typeMappings -> {
            mappings.putAll(typeMappings);
         });

      return mappings;
   }

   @Override
   public ServerLayoutKind getLayoutKind() { return ServerLayoutKind.MANUAL; }
}
