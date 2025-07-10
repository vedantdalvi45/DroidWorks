package com.example.recyclerview.listeners;

import android.view.View;

@FunctionalInterface
public interface RemoveButtonListener {
    public void onRemoveButtonClicked(View view,int position);
}
