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
import java.util.Map;
import java.util.Optional;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GEdgePlacement;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.builder.impl.GLabelBuilder;
import com.borkdominik.big.glsp.server.core.constants.BGCoreTypes;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCIdentifiable;
import com.borkdominik.big.glsp.server.sdk.cdk.gmodel.GCModelElement;

public class GCLabel extends GCModelElement<EObject, GLabel> implements GCIdentifiable {
   public static final String CSS_ID = "gc-label";
   protected GLabel rootGModel;
   protected Options options;

   public GCLabel(final GCModelContext context, final EObject source, final Options options) {
      super(context, source);
      this.options = options;
   }

   @Override
   public String getIdentifier() {
      return CSS_ID;
   }

   public Options getOptions() {
      return options;
   }

   @Override
   protected Optional<GLabel> createRootGModel() {
      var labelBuilder = new GLabelBuilder(options.type).id(context.idCountGenerator.getOrCreateId(origin)).addCssClasses(options.css).edgePlacement(options.edgePlacement).addArguments(options.arguments).text(options.label);
      options.id.ifPresent(id -> labelBuilder.id(id));
      return Optional.of(labelBuilder.build());
   }

   public static class Options {
      public String label;
      public Optional<String> id;
      public String type;
      public List<String> css;
      public Map<String, Object> arguments;
      public GEdgePlacement edgePlacement;

      private static Optional<String> $default$id() {
         return Optional.empty();
      }

      private static String $default$type() {
         return BGCoreTypes.LABEL_TEXT;
      }

      public static abstract class OptionsBuilder<C extends GCLabel.Options, B extends GCLabel.Options.OptionsBuilder<C, B>> {
         private String label;
         private boolean id$set;
         private Optional<String> id$value;
         private boolean type$set;
         private String type$value;
         private java.util.ArrayList<String> css;
         private java.util.ArrayList<String> arguments$key;
         private java.util.ArrayList<Object> arguments$value;
         private GEdgePlacement edgePlacement;

         public B label(final String label) {
            this.label = label;
            return self();
         }

         public B id(final Optional<String> id) {
            this.id$value = id;
            id$set = true;
            return self();
         }

         public B type(final String type) {
            this.type$value = type;
            type$set = true;
            return self();
         }

         public B css(final String css) {
            if (this.css == null) this.css = new java.util.ArrayList<String>();
            this.css.add(css);
            return self();
         }

         public B css(final java.util.Collection<? extends String> css) {
            if (css == null) {
               throw new java.lang.NullPointerException("css cannot be null");
            }
            if (this.css == null) this.css = new java.util.ArrayList<String>();
            this.css.addAll(css);
            return self();
         }

         public B clearCss() {
            if (this.css != null) this.css.clear();
            return self();
         }

         public B argument(final String argumentKey, final Object argumentValue) {
            if (this.arguments$key == null) {
               this.arguments$key = new java.util.ArrayList<String>();
               this.arguments$value = new java.util.ArrayList<Object>();
            }
            this.arguments$key.add(argumentKey);
            this.arguments$value.add(argumentValue);
            return self();
         }

         public B arguments(final java.util.Map<? extends String, ? extends Object> arguments) {
            if (arguments == null) {
               throw new java.lang.NullPointerException("arguments cannot be null");
            }
            if (this.arguments$key == null) {
               this.arguments$key = new java.util.ArrayList<String>();
               this.arguments$value = new java.util.ArrayList<Object>();
            }
            for (final java.util.Map.Entry<? extends String, ? extends Object> $lombokEntry : arguments.entrySet()) {
               this.arguments$key.add($lombokEntry.getKey());
               this.arguments$value.add($lombokEntry.getValue());
            }
            return self();
         }

         public B clearArguments() {
            if (this.arguments$key != null) {
               this.arguments$key.clear();
               this.arguments$value.clear();
            }
            return self();
         }

         public B edgePlacement(final GEdgePlacement edgePlacement) {
            this.edgePlacement = edgePlacement;
            return self();
         }

         protected abstract B self();

         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "GCLabel.Options.OptionsBuilder(label=" + this.label + ", id$value=" + this.id$value + ", type$value=" + this.type$value + ", css=" + this.css + ", arguments$key=" + this.arguments$key + ", arguments$value=" + this.arguments$value + ", edgePlacement=" + this.edgePlacement + ")";
         }
      }

      private static final class OptionsBuilderImpl extends GCLabel.Options.OptionsBuilder<GCLabel.Options, GCLabel.Options.OptionsBuilderImpl> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected GCLabel.Options.OptionsBuilderImpl self() {
            return this;
         }

         @Override
         public GCLabel.Options build() {
            return new GCLabel.Options(this);
         }
      }

      protected Options(final GCLabel.Options.OptionsBuilder<?, ?> b) {
         this.label = b.label;
         if (b.id$set) this.id = b.id$value;
          else this.id = GCLabel.Options.$default$id();
         if (b.type$set) this.type = b.type$value;
          else this.type = GCLabel.Options.$default$type();
         java.util.List<String> css;
         switch (b.css == null ? 0 : b.css.size()) {
         case 0: 
            css = java.util.Collections.emptyList();
            break;
         case 1: 
            css = java.util.Collections.singletonList(b.css.get(0));
            break;
         default: 
            css = java.util.Collections.unmodifiableList(new java.util.ArrayList<String>(b.css));
         }
         this.css = css;
         java.util.Map<String, Object> arguments;
         switch (b.arguments$key == null ? 0 : b.arguments$key.size()) {
         case 0: 
            arguments = java.util.Collections.emptyMap();
            break;
         case 1: 
            arguments = java.util.Collections.singletonMap(b.arguments$key.get(0), b.arguments$value.get(0));
            break;
         default: 
            arguments = new java.util.LinkedHashMap<String, Object>(b.arguments$key.size() < 1073741824 ? 1 + b.arguments$key.size() + (b.arguments$key.size() - 3) / 3 : java.lang.Integer.MAX_VALUE);
            for (int $i = 0; $i < b.arguments$key.size(); $i++) arguments.put(b.arguments$key.get($i), (Object) b.arguments$value.get($i));
            arguments = java.util.Collections.unmodifiableMap(arguments);
         }
         this.arguments = arguments;
         this.edgePlacement = b.edgePlacement;
      }

      public static GCLabel.Options.OptionsBuilder<?, ?> builder() {
         return new GCLabel.Options.OptionsBuilderImpl();
      }
   }
}
