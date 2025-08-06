package com.example.messdays.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.messdays.MainActivity;
import com.example.messdays.R;
import com.example.messdays.data.MessDayEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MyWidget extends AppWidgetProvider {


    public static void updateWidget(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context, MyWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widgetComponent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Set up the intent that starts the MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);

        // Initialize text views
        views.setTextViewText(R.id.monthYearTextView, "N/A");
        views.setTextViewText(R.id.lunch_count  , "N/A");
        views.setTextViewText(R.id.dinner_count, "N/A");








    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidget(context);
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }



}
