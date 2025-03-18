/********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.manifest.integrations;

import com.borkdominik.big.glsp.server.core.diagram.BGDiagramFeatureManifest;
import com.borkdominik.big.glsp.server.core.features.direct_editing.BGDirectEditManifest;
import com.borkdominik.big.glsp.server.core.features.popup.BGPopupManifest;
import com.borkdominik.big.glsp.server.core.features.suffix.BGSuffixManifest;
import com.borkdominik.big.glsp.server.core.handler.action.BGActionFeatureManifest;
import com.borkdominik.big.glsp.server.core.handler.operation.create.BGCreateOperationManifest;
import com.borkdominik.big.glsp.server.core.handler.operation.delete.BGDeleteOperationManifest;
import com.borkdominik.big.glsp.server.core.handler.operation.operation_handler.BGOperationHandlerManifest;
import com.borkdominik.big.glsp.server.core.handler.operation.reconnect_edge.BGReconnectEdgeOperationManifest;
import com.google.inject.AbstractModule;

public class BGCoreManifest extends AbstractModule {

   @Override
   protected void configure() {
      super.configure();

      install(new BGDiagramFeatureManifest());
      install(new BGOperationHandlerManifest());
      install(new BGActionFeatureManifest());

      install(new BGPopupManifest());
      install(new BGDirectEditManifest());
      install(new BGSuffixManifest());

      install(new BGCreateOperationManifest());
      install(new BGDeleteOperationManifest());
      install(new BGReconnectEdgeOperationManifest());
   }
}
