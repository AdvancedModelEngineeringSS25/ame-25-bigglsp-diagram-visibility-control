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
package com.borkdominik.big.glsp.server.features.autocomplete.provider;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.types.EditorContext;

import com.borkdominik.big.glsp.server.core.features.direct_editing.extractor.BGLabelExtractor;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.features.autocomplete.constants.BGAutocompleteConstants;
import com.borkdominik.big.glsp.server.features.autocomplete.handler.BGAutocompleteEntryAction;
import com.borkdominik.big.glsp.server.lib.utils.TypeLiteralUtils;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public abstract class BGBaseAutocompleteEntriesProvider<TElement extends EObject>
   implements BGAutocompleteEntriesProvider {
   @Inject
   protected BGModelState modelState;
   @Inject
   protected BGLabelExtractor labelExtractor;
   @Inject
   protected TypeLiteral<TElement> elementType;
   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   public boolean handles(final EditorContext context) {
      var element = element(context);
      return element.isPresent();
   }

   @Override
   public List<BGAutocompleteEntryAction> process(final EditorContext context) {
      return doProcess(context, (TElement) element(context).get());
   }

   protected abstract List<BGAutocompleteEntryAction> doProcess(EditorContext context, TElement element);

   protected Optional<String> elementId(final EditorContext context) {
      return Optional.ofNullable(context.getArgs().get(BGAutocompleteConstants.EDITOR_CONTEXT_LABEL_ID))
         .map(labelId -> labelExtractor.extractElementId(labelId));
   }

   protected Optional<EObject> element(final EditorContext context) {
      return elementId(context).flatMap(id -> modelState.getElementIndex().get(id, TypeLiteralUtils.of(elementType)));
   }
}
