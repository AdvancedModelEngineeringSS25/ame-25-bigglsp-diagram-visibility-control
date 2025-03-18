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
package com.borkdominik.big.glsp.server.elements;

import com.borkdominik.big.glsp.server.core.manifest.contribution.factory.BGRFactoryContributionModule;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationAccessorFactory;
import com.borkdominik.big.glsp.server.elements.configuration.BGElementConfigurationRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class BGElementModule extends AbstractModule {

   @Override
   protected void configure() {
      super.configure();

      bind(BGElementConfigurationRegistry.class).in(Singleton.class);

      install(new BGRFactoryContributionModule<>(BGElementConfigurationAccessorFactory.class));
   }

}
