// Generated code from Butter Knife. Do not modify!
package com.smartlifedigital.autodialer.Activities;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AboutUsActivity$$ViewBinder<T extends com.smartlifedigital.autodialer.Activities.AboutUsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558563, "field 'parent'");
    target.parent = finder.castView(view, 2131558563, "field 'parent'");
    view = finder.findRequiredView(source, 2131558647, "field 'settingsOptions' and method 'onItemClick'");
    target.settingsOptions = finder.castView(view, 2131558647, "field 'settingsOptions'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p0, p1, p2, p3);
        }
      });
    Resources res = finder.getContext(source).getResources();
    target.playStoreError = res.getString(2131099708);
    target.feedbackSubject = res.getString(2131099694);
    target.sendEmail = res.getString(2131099713);
  }

  @Override public void unbind(T target) {
    target.parent = null;
    target.settingsOptions = null;
  }
}
