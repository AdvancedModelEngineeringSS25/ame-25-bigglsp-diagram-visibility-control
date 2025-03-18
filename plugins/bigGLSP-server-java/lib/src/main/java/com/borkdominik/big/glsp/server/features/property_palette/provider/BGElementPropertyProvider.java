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
package com.borkdominik.big.glsp.server.features.property_palette.provider;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.borkdominik.big.glsp.server.core.commands.BGCommandContext;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.core.model.BGTypeProvider;
import com.borkdominik.big.glsp.server.features.property_palette.handler.BGUpdateElementPropertyAction;
import com.borkdominik.big.glsp.server.features.property_palette.model.ElementPropertyItem;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.borkdominik.big.glsp.server.lib.utils.reflection.ReflectionUtil;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public abstract class BGElementPropertyProvider<TElement extends EObject> implements BGPropertyProvider {

   protected final Enumerator representation;
   protected final Set<BGTypeProvider> elementTypes;
   protected final Set<String> handledProperties;

   @Inject
   protected BGModelState modelState;
   @Inject
   protected TypeLiteral<TElement> elementType;
   @Inject
   protected BGCommandContext commandContext;
   @Inject
   protected BGPropertyProviderContext providerContext;
   @Inject
   protected EMFIdGenerator idGenerator;

   public BGElementPropertyProvider(final Enumerator representation,
      final Set<BGTypeProvider> elementTypes, final Set<String> handledProperties) {
      this.representation = representation;
      this.elementTypes = elementTypes;
      this.handledProperties = handledProperties;
   }

   @Override
   public Set<BGTypeProvider> getHandledElementTypes() { return elementTypes; }

   @Override
   public Set<String> getHandledProperties() { return handledProperties; }

   @Override
   public List<ElementPropertyItem> provide(final EObject element) {
      return doProvide(ReflectionUtil.castOrThrow(element, TypeLiteralUtils.of(elementType)));
   }

   public abstract List<ElementPropertyItem> doProvide(final TElement element);

   @Override
   public boolean matches(final BGUpdateElementPropertyAction action) {
      return handledProperties.contains(action.getPropertyId());
   }

   protected List<ElementPropertyItem> provideIfConfigured(final Set<BGTypeProvider> elementTypes,
      final Supplier<List<ElementPropertyItem>> supplier) {

      if (providerContext.elementConfig().has(elementTypes)) {
         return supplier.get();
      }

      return List.of();
   }

   @Override
   public Command handle(final BGUpdateElementPropertyAction action) {
      var element = modelState.getElementIndex().getOrThrow(action.getElementId(), TypeLiteralUtils.of(elementType));
      return doHandle(action, element);
   }

   public Command doHandle(final BGUpdateElementPropertyAction action, final TElement element) {
      throw new GLSPServerException(
         String.format("Handling %s is not supported in class %s", action.getPropertyId(), getClass()));
   }
}
