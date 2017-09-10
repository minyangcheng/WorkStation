// Generated code from Butter Knife. Do not modify!
package com.hitherejoe.androidboilerplate.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.hitherejoe.androidboilerplate.ui.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558513, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131558513, "field 'mProgressBar'");
    view = finder.findRequiredView(source, 2131558512, "field 'mCharactersRecycler'");
    target.mCharactersRecycler = finder.castView(view, 2131558512, "field 'mCharactersRecycler'");
    view = finder.findRequiredView(source, 2131558511, "field 'mSwipeRefresh'");
    target.mSwipeRefresh = finder.castView(view, 2131558511, "field 'mSwipeRefresh'");
    view = finder.findRequiredView(source, 2131558508, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131558508, "field 'mToolbar'");
  }

  @Override public void unbind(T target) {
    target.mProgressBar = null;
    target.mCharactersRecycler = null;
    target.mSwipeRefresh = null;
    target.mToolbar = null;
  }
}
