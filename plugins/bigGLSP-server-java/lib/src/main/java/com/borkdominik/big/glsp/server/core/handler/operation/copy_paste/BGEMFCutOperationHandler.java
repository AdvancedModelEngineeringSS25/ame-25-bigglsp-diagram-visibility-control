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
package com.borkdominik.big.glsp.server.core.handler.operation.copy_paste;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.operations.CutOperation;

import com.borkdominik.big.glsp.server.core.handler.operation.BGEMFOperationHandler;

public class BGEMFCutOperationHandler
      extends BGEMFOperationHandler<CutOperation> {

   @Override
   public Optional<Command> createCommand(final CutOperation operation) {
      return doNothing();

   }
}
