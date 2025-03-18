/********************************************************************************
 * Copyright (c) 2021 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core;

import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.idgen.FragmentIdGenerator;
import org.eclipse.glsp.server.features.popup.PopupModelFactory;
import org.eclipse.glsp.server.features.sourcemodelwatcher.FileWatcher;
import org.eclipse.glsp.server.features.sourcemodelwatcher.SourceModelWatcher;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.gmodel.GModelApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.gmodel.GModelDeleteOperationHandler;
import org.eclipse.glsp.server.gmodel.GModelDiagramModule;
import org.eclipse.glsp.server.gmodel.GModelReconnectEdgeOperationHandler;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.borkdominik.big.glsp.server.core.commands.BGCommandContext;
import com.borkdominik.big.glsp.server.core.diagram.BGDiagramConfiguration;
import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditOperationHandler;
import com.borkdominik.big.glsp.server.core.features.popup.BGPopupFactory;
import com.borkdominik.big.glsp.server.core.features.tool_palette.BGToolPaletteItemProvider;
import com.borkdominik.big.glsp.server.core.handler.action.BGActionHandlerRegistry;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateEdgeOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateNodeOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.operation_handler.BGOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.operation_handler.BGOperationHandlerRegistry;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeOperationHandler;
import com.borkdominik.big.glsp.server.core.model.BGModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.core.model.integrations.BGGModelElementIndexImpl;
import com.borkdominik.big.glsp.server.core.model.integrations.BGGModelStateImpl;
import com.google.inject.Singleton;

public abstract class BGGModelDiagramModule extends GModelDiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();

      bind(BGModelRepresentation.class).to(bindBGModelStateRepresentation());
      bind(BGModelState.class).to(bindGModelState());
      bind(BGModelElementIndex.class).to(BGGModelElementIndexImpl.class).in(Singleton.class);
      bind(BGCommandContext.class);

      configureEMFIdGenerator(bindEMFIdGenerator());
   }

   @Override
   protected Class<? extends BGModelState> bindGModelState() {
      return BGGModelStateImpl.class;
   }

   protected abstract Class<? extends BGModelRepresentation> bindBGModelStateRepresentation();

   @Override
   protected Class<? extends SourceModelWatcher> bindSourceModelWatcher() {
      return FileWatcher.class;
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return BGToolPaletteItemProvider.class;
   }

   @Override
   protected Class<? extends DiagramConfiguration> bindDiagramConfiguration() {
      return BGDiagramConfiguration.class;
   }

   @Override
   protected Class<? extends OperationHandlerRegistry> bindOperationHandlerRegistry() {
      return BGOperationHandlerRegistry.class;
   }

   @Override
   protected Class<? extends ActionHandlerRegistry> bindActionHandlerRegistry() {
      return BGActionHandlerRegistry.class;
   }

   @Override
   protected Class<? extends PopupModelFactory> bindPopupModelFactory() {
      return BGPopupFactory.class;
   }

   protected Class<? extends EMFIdGenerator> bindEMFIdGenerator() {
      return FragmentIdGenerator.class;
   }

   protected void configureEMFIdGenerator(final Class<? extends EMFIdGenerator> generatorClass) {
      bind(generatorClass).in(Singleton.class);
      bind(EMFIdGenerator.class).to(generatorClass);
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(OperationActionHandler.class, BGOperationHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> bindings) {
      super.configureOperationHandlers(bindings);
      bindings.rebind(GModelApplyLabelEditOperationHandler.class, BGDirectEditOperationHandler.class);
      bindings.rebind(GModelDeleteOperationHandler.class, BGDeleteOperationHandler.class);
      bindings.rebind(GModelReconnectEdgeOperationHandler.class, BGReconnectEdgeOperationHandler.class);

      bindings.add(BGCreateNodeOperationHandler.class);
      bindings.add(BGCreateEdgeOperationHandler.class);
   }

   @Override
   protected void configureAdditionals() {
      super.configureAdditionals();

      // Helper handlers
      bind(GModelDeleteOperationHandler.class);
      bind(GModelApplyLabelEditOperationHandler.class);
      bind(GModelReconnectEdgeOperationHandler.class);
   }
}
