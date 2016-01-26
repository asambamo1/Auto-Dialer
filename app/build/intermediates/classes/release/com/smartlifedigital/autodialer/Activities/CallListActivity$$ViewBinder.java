// Generated code from Butter Knife. Do not modify!
package com.smartlifedigital.autodialer.Activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CallListActivity$$ViewBinder<T extends com.smartlifedigital.autodialer.Activities.CallListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558546, "field 'noCalls'");
    target.noCalls = view;
    view = finder.findRequiredView(source, 2131558545, "field 'callsList'");
    target.callsList = finder.castView(view, 2131558545, "field 'callsList'");
  }

  @Override public void unbind(T target) {
    target.noCalls = null;
    target.callsList = null;
  }
}
