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
package com.borkdominik.big.glsp.server.core.gmodel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;

import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.google.inject.Inject;

public class BGEMFGModelMapHandler {
   @Inject
   protected BGEMFGModelMapperRegistry registry;
   @Inject
   protected BGModelRepresentation modelStateRepresentation;

   public GModelElement handle(final EObject source) {
      var representation = modelStateRepresentation.getUnsafe();
      var mapper = registry.retrieve(representation, source.getClass());

      return mapper.map(source);
   }

   public List<GModelElement> handle(final Collection<? extends EObject> sources) {
      return sources.stream().map(obj -> handle(obj)).collect(Collectors.toList());
   }

   public List<GModelElement> handleSiblings(final EObject source) {
      var representation = modelStateRepresentation.getUnsafe();
      var mapper = registry
         .retrieve(representation, source.getClass());

      return mapper.mapSiblings(source);
   }
}
