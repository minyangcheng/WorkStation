// Generated code from Butter Knife. Do not modify!
package com.hitherejoe.androidboilerplate.ui.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailAdapter$DetailHolder$$ViewBinder<T extends com.hitherejoe.androidboilerplate.ui.adapter.DetailAdapter.DetailHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558530, "field 'detailText'");
    target.detailText = finder.castView(view, 2131558530, "field 'detailText'");
  }

  @Override public void unbind(T target) {
    target.detailText = null;
  }
}
