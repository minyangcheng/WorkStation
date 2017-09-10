// Generated code from Butter Knife. Do not modify!
package com.hitherejoe.androidboilerplate.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailActivity$$ViewBinder<T extends com.hitherejoe.androidboilerplate.ui.activity.DetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558509, "field 'mTabLayout'");
    target.mTabLayout = finder.castView(view, 2131558509, "field 'mTabLayout'");
    view = finder.findRequiredView(source, 2131558508, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131558508, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131558510, "field 'mBeaconDetailViewPager'");
    target.mBeaconDetailViewPager = finder.castView(view, 2131558510, "field 'mBeaconDetailViewPager'");
  }

  @Override public void unbind(T target) {
    target.mTabLayout = null;
    target.mToolbar = null;
    target.mBeaconDetailViewPager = null;
  }
}
