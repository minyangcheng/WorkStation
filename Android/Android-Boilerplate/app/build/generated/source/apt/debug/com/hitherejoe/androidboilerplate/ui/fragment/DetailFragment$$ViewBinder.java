// Generated code from Butter Knife. Do not modify!
package com.hitherejoe.androidboilerplate.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailFragment$$ViewBinder<T extends com.hitherejoe.androidboilerplate.ui.fragment.DetailFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558522, "field 'mDetailRecycler'");
    target.mDetailRecycler = finder.castView(view, 2131558522, "field 'mDetailRecycler'");
    view = finder.findRequiredView(source, 2131558521, "field 'mNoDataText'");
    target.mNoDataText = finder.castView(view, 2131558521, "field 'mNoDataText'");
  }

  @Override public void unbind(T target) {
    target.mDetailRecycler = null;
    target.mNoDataText = null;
  }
}
