package com.example.messdays.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.messdays.MainActivity;
import com.example.messdays.R;

public class MyWidget extends AppWidgetProvider {
    TextView lunchCountTextView, dinnerCountTextView,messExpensesTextView;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_lunch_count, String.valueOf(MainActivity.counts.get(0)));
        views.setTextViewText(R.id.widget_dinner_count, String.valueOf(MainActivity.counts.get(1)));
        views.setTextViewText(R.id.widget_mess_expenses, String.valueOf(MainActivity.counts.get(2)));

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

