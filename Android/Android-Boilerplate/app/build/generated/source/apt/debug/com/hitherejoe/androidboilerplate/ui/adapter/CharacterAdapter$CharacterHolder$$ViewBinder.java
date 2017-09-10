// Generated code from Butter Knife. Do not modify!
package com.hitherejoe.androidboilerplate.ui.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CharacterAdapter$CharacterHolder$$ViewBinder<T extends com.hitherejoe.androidboilerplate.ui.adapter.CharacterAdapter.CharacterHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558523, "field 'characterContainer'");
    target.characterContainer = view;
    view = finder.findRequiredView(source, 2131558525, "field 'nameText'");
    target.nameText = finder.castView(view, 2131558525, "field 'nameText'");
    view = finder.findRequiredView(source, 2131558526, "field 'descriptionText'");
    target.descriptionText = finder.castView(view, 2131558526, "field 'descriptionText'");
    view = finder.findRequiredView(source, 2131558524, "field 'characterImage'");
    target.characterImage = finder.castView(view, 2131558524, "field 'characterImage'");
    view = finder.findRequiredView(source, 2131558528, "field 'viewText'");
    target.viewText = finder.castView(view, 2131558528, "field 'viewText'");
    view = finder.findRequiredView(source, 2131558529, "field 'tabText'");
    target.tabText = finder.castView(view, 2131558529, "field 'tabText'");
  }

  @Override public void unbind(T target) {
    target.characterContainer = null;
    target.nameText = null;
    target.descriptionText = null;
    target.characterImage = null;
    target.viewText = null;
    target.tabText = null;
  }
}
