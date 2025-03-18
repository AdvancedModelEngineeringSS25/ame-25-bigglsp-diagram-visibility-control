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
package com.borkdominik.big.glsp.server.core.features.direct_editing.label_edit;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;

import com.borkdominik.big.glsp.server.core.handler.BGTypeHandler;

public interface BGLabelEditHandler extends BGTypeHandler {
   boolean matches(ApplyLabelEditOperation operation);

   Command handle(ApplyLabelEditOperation operation, EObject element);
}
