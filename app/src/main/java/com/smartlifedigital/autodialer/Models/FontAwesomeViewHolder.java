package com.smartlifedigital.autodialer.Models;

import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.smartlifedigital.autodialer.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FontAwesomeViewHolder
{
    @Bind(R.id.item_icon) public FontAwesomeText itemIcon;
    @Bind(R.id.item_name) public TextView itemName;

    public FontAwesomeViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
