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
package com.borkdominik.big.glsp.server.elements.gmodel;

import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

import com.borkdominik.big.glsp.server.core.features.suffix.BGIdCountContextGenerator;
import com.borkdominik.big.glsp.server.core.features.suffix.BGSuffix;
import com.borkdominik.big.glsp.server.core.gmodel.BGEMFGModelMapHandler;
import com.borkdominik.big.glsp.server.core.gmodel.BGEMFGModelMapper;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public abstract class BGEMFElementGModelMapper<TSource extends EObject, TTarget extends GModelElement>
   implements BGEMFGModelMapper<TSource, TTarget> {

   protected final Enumerator representation;
   protected final Set<BGTypeProvider> handledElementTypes;

   @Inject
   protected TypeLiteral<TSource> sourceType;
   @Inject
   protected TypeLiteral<TTarget> targetType;

   @Inject
   protected BGEMFModelState modelState;
   @Inject
   protected EMFIdGenerator idGenerator;
   @Inject
   protected BGSuffix suffix;
   @Inject
   protected BGEMFGModelMapHandler mapHandler;
   @Inject
   protected BGIdCountContextGenerator idCountGenerator;
   @Inject
   protected GCModelContext gcmodelContext;

   public BGEMFElementGModelMapper(final Enumerator representation,
      final Set<BGTypeProvider> handledElementTypes) {
      super();
      this.representation = representation;
      this.handledElementTypes = handledElementTypes;
   }

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return handledElementTypes; }

   @Override
   public Class<TSource> getSourceType() { return TypeLiteralUtils.of(this.sourceType); }

   @Override
   public Class<TTarget> getTargetType() { return TypeLiteralUtils.of(this.targetType); }

}
