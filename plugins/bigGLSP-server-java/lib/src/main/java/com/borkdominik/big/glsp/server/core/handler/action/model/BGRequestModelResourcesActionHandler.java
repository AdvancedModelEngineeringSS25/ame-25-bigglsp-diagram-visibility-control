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
package com.borkdominik.big.glsp.server.core.handler.action.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emfcloud.jackson.annotations.EcoreIdentityInfo;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.borkdominik.big.glsp.server.core.handler.action.integrations.BGEMFActionHandler;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class BGRequestModelResourcesActionHandler extends BGEMFActionHandler<BGRequestModelResourcesAction> {
   private static Logger LOGGER = LogManager.getLogger(BGRequestModelResourcesActionHandler.class.getSimpleName());

   @Inject
   protected BGEMFModelState modelState;

   @Override
   protected List<Action> executeAction(final BGRequestModelResourcesAction action) {
      return List.of(new BGModelResourcesResponseAction(this.getResources(action)));
   }

   protected Map<String, BGModelResource> getResources(final BGRequestModelResourcesAction action) {
      var resources = new HashMap<String, BGModelResource>();

      var formats = action.getFormats();
      if (formats.size() == 0) {
         formats.add("xml");
      }

      for (var resource : modelState.getResourceSet().getResources()) {
         if (resource.getURI() != null) {
            for (var format : formats) {
               if (format.equals("xml")) {
                  resources.put(resource.getURI().toString(), this.provideXML(resource));
               } else if (format.equals("json")) {
                  resources.put(resource.getURI().toString() + "#json", this.provideJSON(resource));
               } else {
                  throw new GLSPServerException("Unsupported format: " + format);
               }
            }
         }
      }

      return resources;
   }

   protected BGModelResource provideXML(Resource resource) {
      var outputStream = new ByteArrayOutputStream();
      var options = new HashMap<>();
      options.put(XMLResource.OPTION_ENCODING, "UTF-8");

      try {
         resource.save(outputStream, options);
         var modelResource = new BGModelResource(resource.getURI().toString(), outputStream.toString(), "xml");
         outputStream.close();
         return modelResource;
      } catch (IOException e) {
         LOGGER.error("Could not get resource: " + resource.getURI(), e);
         throw new GLSPServerException("Could not get resource: " + resource.getURI(), e);
      }
   }

   protected BGModelResource provideJSON(Resource resource) {
      var mapper = new ObjectMapper();
      var module = new EMFModule();

      module.configure(EMFModule.Feature.OPTION_USE_ID, true);
      module.setIdentityInfo(new EcoreIdentityInfo("id"));
      mapper.registerModule(module);

      try {
         String jsonString = mapper.writeValueAsString(resource);
         var modelResource = new BGModelResource(resource.getURI().toString(), jsonString, "json");
         return modelResource;
      } catch (IOException e) {
         LOGGER.error("Could not get resource: " + resource.getURI(), e);
         throw new GLSPServerException("Could not get resource: " + resource.getURI(), e);
      }
   }
}
