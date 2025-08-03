package com.example.messdays.widget;

import static com.example.messdays.MainActivity.monthMap;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.messdays.MainActivity;
import com.example.messdays.R;
import com.example.messdays.data.MessDayEvent;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MyWidget extends AppWidgetProvider {

    public static final String ACTION_TOGGLE_LUNCH = "com.example.TOGGLE_LUNCH";
    public static final String ACTION_TOGGLE_DINNER = "com.example.TOGGLE_DINNER";

    private static boolean lunchChecked ;
    private static boolean dinnerChecked ;

    public static void updateWidget(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context, MyWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widgetComponent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Set counts
        views.setTextViewText(R.id.widget_lunch_count, String.valueOf(MainActivity.counts.get(0)));
        views.setTextViewText(R.id.widget_dinner_count, String.valueOf(MainActivity.counts.get(1)));
        views.setTextViewText(R.id.widget_mess_expenses, "₹" + MainActivity.counts.get(2));
        views.setTextViewText(R.id.widget_date_header,  LocalDate.now().toString());

        String currentMonthKey = MainActivity.monthYearTextView.getText().toString();
        LocalDate localDate = LocalDate.now();
        if (monthMap.containsKey(currentMonthKey)){
            Map<LocalDate, MessDayEvent> monthSpecificEvents = monthMap.get(currentMonthKey);
            MessDayEvent messDayEvent = monthSpecificEvents.get(localDate);
            lunchChecked = messDayEvent.isHasLunch();
            dinnerChecked = messDayEvent.isHasDinner();
        }

        // Set checkbox images based on state
        views.setImageViewResource(R.id.lunchCheckBox, lunchChecked ? R.drawable.meal : R.drawable.meal_gray);
        views.setImageViewResource(R.id.dinnerCheckBox, dinnerChecked ? R.drawable.meal : R.drawable.meal_gray);

        // Set click handlers
        Intent lunchIntent = new Intent(context, MyWidget.class);
        lunchIntent.setAction(ACTION_TOGGLE_LUNCH);
        PendingIntent lunchPendingIntent = PendingIntent.getBroadcast(
                context, 1, lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.lunchCheckBox, lunchPendingIntent);

        Intent dinnerIntent = new Intent(context, MyWidget.class);
        dinnerIntent.setAction(ACTION_TOGGLE_DINNER);
        PendingIntent dinnerPendingIntent = PendingIntent.getBroadcast(
                context, 2, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.dinnerCheckBox, dinnerPendingIntent);

        // Set click handler for the widget root to open MainActivity
        Intent openAppIntent = new Intent(context, MainActivity.class);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(
                context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_root, openAppPendingIntent);

        // Update all widget instances
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidget(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_TOGGLE_LUNCH)) {
                lunchChecked = !lunchChecked;
            } else if (intent.getAction().equals(ACTION_TOGGLE_DINNER)) {
                dinnerChecked = !dinnerChecked;
            }

            String currentMonthKey = MainActivity.monthYearTextView.getText().toString();
            LocalDate localDate = LocalDate.now();
            if (monthMap.containsKey(currentMonthKey)){
                Map<LocalDate, MessDayEvent> monthSpecificEvents = monthMap.get(currentMonthKey);
                MessDayEvent messDayEvent = monthSpecificEvents.get(localDate);
                messDayEvent.setHasLunch(lunchChecked);
                messDayEvent.setHasDinner(dinnerChecked);
                monthSpecificEvents.put(localDate, messDayEvent);
                monthMap.put(currentMonthKey, monthSpecificEvents);
            }else {
                Map<LocalDate, MessDayEvent> monthSpecificEvents = new HashMap<>();
                MessDayEvent messDayEvent = new MessDayEvent(localDate, lunchChecked, dinnerChecked,null);
                monthSpecificEvents.put(localDate, messDayEvent);
                monthMap.put(currentMonthKey, monthSpecificEvents);
            }

            updateWidget(context);
        }
    }
}
