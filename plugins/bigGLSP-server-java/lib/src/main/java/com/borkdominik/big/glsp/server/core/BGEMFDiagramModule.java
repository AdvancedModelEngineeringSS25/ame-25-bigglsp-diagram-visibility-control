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
import org.eclipse.glsp.server.emf.idgen.XMIIDGenerator;
import org.eclipse.glsp.server.emf.notation.EMFChangeBoundsOperationHandler;
import org.eclipse.glsp.server.emf.notation.EMFNotationDiagramModule;
import org.eclipse.glsp.server.features.popup.PopupModelFactory;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.gmodel.GModelCutOperationHandler;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.borkdominik.big.glsp.server.core.commands.BGCommandContext;
import com.borkdominik.big.glsp.server.core.commands.emf.BGEMFCommandContext;
import com.borkdominik.big.glsp.server.core.diagram.BGDiagramConfiguration;
import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditOperationHandler;
import com.borkdominik.big.glsp.server.core.features.popup.BGPopupFactory;
import com.borkdominik.big.glsp.server.core.features.tool_palette.BGToolPaletteItemProvider;
import com.borkdominik.big.glsp.server.core.handler.action.BGActionHandlerRegistry;
import com.borkdominik.big.glsp.server.core.handler.action.clipboard.BGRequestClipboardDataActionHandler;
import com.borkdominik.big.glsp.server.core.handler.action.model.BGRequestModelResourcesActionHandler;
import com.borkdominik.big.glsp.server.core.handler.action.new_file.BGRequestNewFileActionHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.change_bounds.BGEMFChangeBoundsOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.change_routing_points.BGEMFChangeRoutingPointsOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.copy_paste.BGEMFCutOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.copy_paste.BGEMFPasteOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateEdgeOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateNodeOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.operation_handler.BGOperationHandler;
import com.borkdominik.big.glsp.server.core.handler.operation.operation_handler.BGOperationHandlerRegistry;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeOperationHandler;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.borkdominik.big.glsp.server.core.model.BGModelElementIndex;
import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.borkdominik.big.glsp.server.core.model.BGModelState;
import com.borkdominik.big.glsp.server.core.model.integrations.BGEMFModelElementIndexImpl;
import com.borkdominik.big.glsp.server.core.model.integrations.BGEMFModelStateImpl;
import com.borkdominik.big.glsp.server.core.model.integrations.BGEMFSourceModelStorage;
import com.google.inject.Singleton;

public abstract class BGEMFDiagramModule extends EMFNotationDiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();

      bind(BGModelState.class).to(bindGModelState());
      bind(BGModelRepresentation.class).to(bindBGModelStateRepresentation());
      bind(BGModelElementIndex.class).to(BGEMFModelElementIndex.class);

      bind(BGEMFModelState.class).to(bindGModelState());
      bind(BGEMFModelElementIndex.class).to(BGEMFModelElementIndexImpl.class).in(Singleton.class);
      bind(BGEMFSourceModelStorage.class).to(bindSourceModelStorage());

      bind(BGEMFCommandContext.class);
      bind(BGCommandContext.class).to(BGEMFCommandContext.class);
   }

   @Override
   protected Class<? extends BGEMFModelState> bindGModelState() {
      return BGEMFModelStateImpl.class;
   }

   @Override
   protected Class<? extends BGEMFSourceModelStorage> bindSourceModelStorage() {
      return BGEMFSourceModelStorage.class;
   }

   protected abstract Class<? extends BGModelRepresentation> bindBGModelStateRepresentation();

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

   @Override
   protected Class<? extends EMFIdGenerator> bindEMFIdGenerator() {
      return XMIIDGenerator.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(OperationActionHandler.class, BGOperationHandler.class);
      bindings.add(BGRequestNewFileActionHandler.class);
      bindings.add(BGRequestClipboardDataActionHandler.class);
      bindings.add(BGRequestModelResourcesActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> bindings) {
      super.configureOperationHandlers(bindings);
      bindings.rebind(EMFChangeBoundsOperationHandler.class, BGEMFChangeBoundsOperationHandler.class);
      bindings.add(BGCreateNodeOperationHandler.class);
      bindings.add(BGCreateEdgeOperationHandler.class);
      bindings.add(BGDeleteOperationHandler.class);
      bindings.add(BGDirectEditOperationHandler.class);
      bindings.add(BGReconnectEdgeOperationHandler.class);
      bindings.add(BGEMFChangeRoutingPointsOperationHandler.class);
      bindings.add(BGEMFPasteOperationHandler.class);
      bindings.rebind(GModelCutOperationHandler.class, BGEMFCutOperationHandler.class);
   }
}
