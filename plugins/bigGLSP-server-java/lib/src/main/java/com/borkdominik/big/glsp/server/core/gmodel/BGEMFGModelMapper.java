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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;

import com.borkdominik.big.glsp.server.core.handler.BGTypeHandler;

public interface BGEMFGModelMapper<TSource extends EObject, TTarget extends GModelElement> extends BGTypeHandler {
   TTarget map(TSource source);

   default List<GModelElement> mapSiblings(final TSource source) {
      return List.of();
   }

   Class<TSource> getSourceType();

   Class<TTarget> getTargetType();
}
