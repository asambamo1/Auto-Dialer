// Generated code from Butter Knife. Do not modify!
package com.smartlifedigital.autodialer.Activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AboutMeActivity$$ViewBinder<T extends com.smartlifedigital.autodialer.Activities.AboutMeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558540, "method 'openLinkedIn'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.openLinkedIn(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558541, "method 'openGitHub'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.openGitHub(p0);
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
