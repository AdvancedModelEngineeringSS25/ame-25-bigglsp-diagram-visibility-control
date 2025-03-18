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
package com.borkdominik.big.glsp.server.core.model.integrations;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.glsp.server.emf.UUIDXMIResourceFactory;
import org.eclipse.glsp.server.emf.notation.EMFNotationSourceModelStorage;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.borkdominik.big.glsp.server.core.handler.action.new_file.BGRequestNewFileAction;

public class BGEMFSourceModelStorage extends EMFNotationSourceModelStorage {
   @Override
   public void loadSourceModel(final RequestModelAction action) {
      var sourceURI = ClientOptionsUtil.getSourceUri(action.getOptions())
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      // FIX: Handle the source URI correctly
      var resourceURI = URI.createURI(sourceURI);

      var editingDomain = getOrCreateEditingDomain();
      doLoadSourceModel(editingDomain.getResourceSet(), resourceURI, action);
   }

   @Override
   protected ResourceSet setupResourceSet(final ResourceSet resourceSet) {
      var set = super.setupResourceSet(resourceSet);
      set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new UUIDXMIResourceFactory());
      return set;
   }

   public URI createSourceModel(final BGRequestNewFileAction action) {
      var sourceURI = ClientOptionsUtil.getSourceUri(action.getOptions())
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      var resourceURI = URI.createFileURI(sourceURI);

      var editingDomain = getOrCreateEditingDomain();
      doCreateSourceModel(editingDomain.getResourceSet(), resourceURI, action);

      return resourceURI;
   }

   protected void doCreateSourceModel(final ResourceSet resourceSet, final URI resourceURI,
      final BGRequestNewFileAction action) {
      var packageRegistry = resourceSet.getPackageRegistry();
      for (var entry : packageRegistry.entrySet()) {
         if (entry.getValue() instanceof EPackage ePackage) {
            doCreateResource(resourceSet, ePackage, resourceURI);
         }
      }
   }

   protected void doCreateResource(final ResourceSet resourceSet, final EPackage ePackage, final URI resourceURI) {
      var packageURI = resourceURI.appendFileExtension(ePackage.getNsPrefix());
      var packageResource = resourceSet.createResource(packageURI);

      packageResource.getContents().add(doCreateResourceContent(ePackage));
      try {
         packageResource.save(null);
      } catch (IOException e) {
         throw new GLSPServerException("Failed to create file", e);
      }
   }

   protected EObject doCreateResourceContent(final EPackage ePackage) {
      return ePackage;
   }
}
