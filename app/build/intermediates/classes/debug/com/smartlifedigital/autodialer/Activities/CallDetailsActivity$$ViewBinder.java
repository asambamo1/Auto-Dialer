// Generated code from Butter Knife. Do not modify!
package com.smartlifedigital.autodialer.Activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CallDetailsActivity$$ViewBinder<T extends com.smartlifedigital.autodialer.Activities.CallDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558565, "field 'edtName'");
    target.edtName = finder.castView(view, 2131558565, "field 'edtName'");
    view = finder.findRequiredView(source, 2131558566, "field 'edtNumber'");
    target.edtNumber = finder.castView(view, 2131558566, "field 'edtNumber'");
    view = finder.findRequiredView(source, 2131558568, "field 'edtTimer'");
    target.edtTimer = finder.castView(view, 2131558568, "field 'edtTimer'");
    view = finder.findRequiredView(source, 2131558571, "field 'chkWeekly'");
    target.chkWeekly = finder.castView(view, 2131558571, "field 'chkWeekly'");
    view = finder.findRequiredView(source, 2131558574, "field 'chkSunday'");
    target.chkSunday = finder.castView(view, 2131558574, "field 'chkSunday'");
    view = finder.findRequiredView(source, 2131558575, "field 'chkMonday'");
    target.chkMonday = finder.castView(view, 2131558575, "field 'chkMonday'");
    view = finder.findRequiredView(source, 2131558576, "field 'chkTuesday'");
    target.chkTuesday = finder.castView(view, 2131558576, "field 'chkTuesday'");
    view = finder.findRequiredView(source, 2131558577, "field 'chkWednesday'");
    target.chkWednesday = finder.castView(view, 2131558577, "field 'chkWednesday'");
    view = finder.findRequiredView(source, 2131558578, "field 'chkThursday'");
    target.chkThursday = finder.castView(view, 2131558578, "field 'chkThursday'");
    view = finder.findRequiredView(source, 2131558579, "field 'chkFriday'");
    target.chkFriday = finder.castView(view, 2131558579, "field 'chkFriday'");
    view = finder.findRequiredView(source, 2131558580, "field 'chkSaturday'");
    target.chkSaturday = finder.castView(view, 2131558580, "field 'chkSaturday'");
    view = finder.findRequiredView(source, 2131558584, "field 'txtToneSelection'");
    target.txtToneSelection = finder.castView(view, 2131558584, "field 'txtToneSelection'");
    view = finder.findRequiredView(source, 2131558586, "field 't2'");
    target.t2 = finder.castView(view, 2131558586, "field 't2'");
    view = finder.findRequiredView(source, 2131558563, "field 'snackbar'");
    target.snackbar = finder.castView(view, 2131558563, "field 'snackbar'");
    view = finder.findRequiredView(source, 2131558587, "method 'saveCall'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.saveCall(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.edtName = null;
    target.edtNumber = null;
    target.edtTimer = null;
    target.chkWeekly = null;
    target.chkSunday = null;
    target.chkMonday = null;
    target.chkTuesday = null;
    target.chkWednesday = null;
    target.chkThursday = null;
    target.chkFriday = null;
    target.chkSaturday = null;
    target.txtToneSelection = null;
    target.t2 = null;
    target.snackbar = null;
  }
}
