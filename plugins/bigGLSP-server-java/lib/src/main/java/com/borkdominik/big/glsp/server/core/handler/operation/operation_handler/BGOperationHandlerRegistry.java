/********************************************************************************
 * Copyright (c) 2022 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.core.handler.operation.operation_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.internal.registry.MapRegistry;
import org.eclipse.glsp.server.internal.util.ReflectionUtil;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.borkdominik.big.glsp.server.lib.registry.BGRegistryKey;
import com.google.inject.Inject;

public class BGOperationHandlerRegistry implements OperationHandlerRegistry {

   protected final MapRegistry<String, OperationHandler<?>> internalRegistry;
   protected final Map<String, Operation> operations;

   protected final BGModelRepresentation modelStateRepresentation;
   protected final BGOverrideOperationHandlerRegistry overrideRegistry;

   @Inject
   public BGOperationHandlerRegistry(final Set<OperationHandler<?>> handlers,
      final BGModelRepresentation modelStateRepresentation,
      final BGOverrideOperationHandlerRegistry overrideRegsitry) {
      this.modelStateRepresentation = modelStateRepresentation;
      this.overrideRegistry = overrideRegsitry;

      operations = new HashMap<>();
      internalRegistry = new MapRegistry<>() {};
      handlers.forEach(handler -> {
         ReflectionUtil.construct(handler.getHandledOperationType())
            .ifPresent(operation -> register(operation, handler));
      });

      // printContent();
   }

   @Override
   public Optional<OperationHandler<?>> getOperationHandler(final Operation operation) {
      return get(operation);
   }

   protected void debug() {
      System.out.println("==== " + getClass().getName() + " ====");
      keys().forEach(key -> {
         System.out.println("Key: " + deriveKey(key) + " | Value: " + get(key).get().getClass().getName());
      });
      System.out.println("==== END ====");
   }

   protected String deriveKey(final Operation key) {
      return key.getClass().getName();
   }

   @Override
   public boolean register(final Operation key, final OperationHandler<?> handler) {
      final String strKey = deriveKey(key);
      operations.put(strKey, key);
      return internalRegistry.register(strKey, handler);
   }

   @Override
   public boolean deregister(final Operation key) {
      return internalRegistry.deregister(deriveKey(key));
   }

   @Override
   public boolean hasKey(final Operation key) {
      return internalRegistry.hasKey(deriveKey(key));
   }

   @Override
   public Optional<OperationHandler<?>> get(final Operation key) {
      var diagramHandler = this.modelStateRepresentation.get().flatMap(representation -> {
         var overrideKey = BGRegistryKey.<Class<? extends Operation>> of(representation,
            key.getClass());

         if (overrideRegistry.hasKey(overrideKey)) {
            return overrideRegistry.get(overrideKey);
         }

         return Optional.<OperationHandler<?>> empty();
      });

      return diagramHandler.or(() -> internalRegistry.get(deriveKey(key)));
   }

   @Override
   public Set<OperationHandler<?>> getAll() { return internalRegistry.getAll(); }

   @Override
   public Set<Operation> keys() {
      return internalRegistry.keys().stream().map(operations::get).collect(Collectors.toSet());
   }

}
