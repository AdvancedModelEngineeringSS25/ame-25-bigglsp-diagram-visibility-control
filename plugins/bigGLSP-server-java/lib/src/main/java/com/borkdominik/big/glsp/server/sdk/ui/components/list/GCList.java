/******************************************************************************** * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.server.sdk.ui.components.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;
import com.borkdominik.big.glsp.server.core.constants.BGGapValues;
import com.borkdominik.big.glsp.server.core.constants.BGPaddingValues;
import com.borkdominik.big.glsp.server.sdk.cdk.GCModelContext;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCIdentifiable;
import com.borkdominik.big.glsp.server.sdk.cdk.base.GCProvider;
import com.borkdominik.big.glsp.server.sdk.cdk.gmodel.GCModelList;
import com.borkdominik.big.glsp.server.sdk.gmodel.BCCompartmentBuilder;
import com.borkdominik.big.glsp.server.sdk.gmodel.BCDividerBuilder;
import com.borkdominik.big.glsp.server.sdk.gmodel.BCLayoutOptions;

public class GCList extends GCModelList<EObject, GModelElement> implements GCIdentifiable {
   public static final String CSS_ID = "gc-list";
   protected Options options;
   protected ArrayList<Integer> dividerIndex = new ArrayList<>();

   public GCList(final GCModelContext context, final EObject source) {
      this(context, source, Options.builder().build());
   }

   public GCList(final GCModelContext context, final EObject source, final Options options) {
      super(context, source);
      this.options = options;
   }

   @Override
   public String getIdentifier() {
      return CSS_ID;
   }

   @Override
   public boolean add(final GCProvider e) {
      dividerIndex.add(size());
      return super.add(e);
   }

   @Override
   public boolean addAll(final Collection<? extends GCProvider> children) {
      dividerIndex.add(size());
      return super.addAll(children);
   }

   @Override
   protected void assignChildrenToGModel(final GModelElement rootGModel) {
      var gmodel = getChildrenGModel();
      var children = getAssignableChildren();
      var dividerIterator = dividerIndex.iterator();
      var dividerI = -1;
      for (int i = 0; i < children.size(); i++) {
         if (dividerI != i & dividerIterator.hasNext()) {
            dividerI = dividerIterator.next();
         }
         if (dividerI == i) {
            gmodel.getChildren().add(new BCDividerBuilder(origin, context).build());
         }
         var child = children.get(i);
         assignChildToGModel(gmodel, child);
      }
   }

   @Override
   protected Optional<GModelElement> createRootGModel() {
      return Optional.of(new BCCompartmentBuilder<>(origin, context).withVBoxLayout().addLayoutOptions(new BCLayoutOptions().paddingBottom(BGPaddingValues.LEVEL_1).vGap(BGGapValues.LEVEL_1)).build());
   }

   public static class Options {
      public boolean dividerBeforeInserts;

      private static boolean $default$dividerBeforeInserts() {
         return false;
      }

      public static abstract class OptionsBuilder<C extends GCList.Options, B extends GCList.Options.OptionsBuilder<C, B>> {
         private boolean dividerBeforeInserts$set;
         private boolean dividerBeforeInserts$value;

         public B dividerBeforeInserts(final boolean dividerBeforeInserts) {
            this.dividerBeforeInserts$value = dividerBeforeInserts;
            dividerBeforeInserts$set = true;
            return self();
         }

         protected abstract B self();

         public abstract C build();

         @Override
         public java.lang.String toString() {
            return "GCList.Options.OptionsBuilder(dividerBeforeInserts$value=" + this.dividerBeforeInserts$value + ")";
         }
      }

      private static final class OptionsBuilderImpl extends GCList.Options.OptionsBuilder<GCList.Options, GCList.Options.OptionsBuilderImpl> {
         private OptionsBuilderImpl() {
         }

         @Override
         protected GCList.Options.OptionsBuilderImpl self() {
            return this;
         }

         @Override
         public GCList.Options build() {
            return new GCList.Options(this);
         }
      }

      protected Options(final GCList.Options.OptionsBuilder<?, ?> b) {
         if (b.dividerBeforeInserts$set) this.dividerBeforeInserts = b.dividerBeforeInserts$value;
          else this.dividerBeforeInserts = GCList.Options.$default$dividerBeforeInserts();
      }

      public static GCList.Options.OptionsBuilder<?, ?> builder() {
         return new GCList.Options.OptionsBuilderImpl();
      }
   }
}
