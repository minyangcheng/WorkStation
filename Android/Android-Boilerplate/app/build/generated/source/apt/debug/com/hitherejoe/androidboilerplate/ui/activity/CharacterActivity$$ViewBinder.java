// Generated code from Butter Knife. Do not modify!
package com.hitherejoe.androidboilerplate.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CharacterActivity$$ViewBinder<T extends com.hitherejoe.androidboilerplate.ui.activity.CharacterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558508, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131558508, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131558507, "field 'mCollapsingToolbar'");
    target.mCollapsingToolbar = finder.castView(view, 2131558507, "field 'mCollapsingToolbar'");
  }

  @Override public void unbind(T target) {
    target.mToolbar = null;
    target.mCollapsingToolbar = null;
  }
}
