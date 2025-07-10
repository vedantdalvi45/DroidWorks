package com.example.recyclerview.listeners;

import android.view.View;


@FunctionalInterface
public interface CheckBoxCheckedListener {
    void onItemChecked(View view,boolean isChecked,int position);
}
