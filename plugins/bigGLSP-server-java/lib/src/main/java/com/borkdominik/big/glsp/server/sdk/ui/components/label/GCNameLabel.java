/******************************************************************************** * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.sdk.ui.components.label;

import java.util.List;
import java.util.Optional;
import org.eclipse.emf.ecore.EObject;
import com.borkdominik.big.glsp.server.core.constants.BGCoreCSS;
import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.core.features.suffix.BGNameLabelSuffix;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;

public class GCNameLabel extends GCLabel {
   public static final String CSS_ID = "gc-name-label";

   public GCNameLabel(final GCModelContext context, final EObject origin, final Options options) {
      super(context, origin, options);
      init();
   }

   protected void init() {
      if (options.id.isEmpty()) {
         options.id = Optional.of(context.suffix.appendTo(BGNameLabelSuffix.SUFFIX, context.idGenerator.getOrCreateId(origin)));
      }
   }

   @Override
   public Options getOptions() {
      return (Options) options;
   }

   @Override
   public String getIdentifier() {
      return CSS_ID;
   }

   public static class Options extends GCLabel.Options {

      public static abstract class OptionsBuilder<C extends Options, B extends OptionsBuilder<C, B>> extends GCLabel.Options.OptionsBuilder<C, B> {
         public OptionsBuilder() {
            type(BGCoreTypes.LABEL_NAME);
            css(List.of(BGCoreCSS.FONT_BOLD, BGCoreCSS.TEXT_HIGHLIGHT));
         }

         @Override
         protected abstract B self();

         @Override
         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "GCNameLabel.Options.OptionsBuilder(super=" + super.toString() + ")";
         }
      }

      private static final class OptionsBuilderImpl extends GCNameLabel.Options.OptionsBuilder<GCNameLabel.Options, GCNameLabel.Options.OptionsBuilderImpl> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected GCNameLabel.Options.OptionsBuilderImpl self() {
            return this;
         }

         @Override
         public GCNameLabel.Options build() {
            return new GCNameLabel.Options(this);
         }
      }

      protected Options(final GCNameLabel.Options.OptionsBuilder<?, ?> b) {
         super(b);
      }

      public static GCNameLabel.Options.OptionsBuilder<?, ?> builder() {
         return new GCNameLabel.Options.OptionsBuilderImpl();
      }
   }
}
