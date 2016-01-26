// Generated code from Butter Knife. Do not modify!
package com.smartlifedigital.autodialer.Models;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FontAwesomeViewHolder$$ViewBinder<T extends com.smartlifedigital.autodialer.Models.FontAwesomeViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558619, "field 'itemIcon'");
    target.itemIcon = finder.castView(view, 2131558619, "field 'itemIcon'");
    view = finder.findRequiredView(source, 2131558620, "field 'itemName'");
    target.itemName = finder.castView(view, 2131558620, "field 'itemName'");
  }

  @Override public void unbind(T target) {
    target.itemIcon = null;
    target.itemName = null;
  }
}
