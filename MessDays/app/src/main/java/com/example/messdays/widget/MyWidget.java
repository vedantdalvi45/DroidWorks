package com.example.messdays.widget;

import static com.example.messdays.MainActivity.MONTH_MAP_KEY;
import static com.example.messdays.MainActivity.PREFS_NAME;

import java.lang.reflect.Type;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.messdays.LocalDateAdapter;
import com.example.messdays.MainActivity;
import com.example.messdays.R;
import com.example.messdays.data.MessDayEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyWidget extends AppWidgetProvider {

    public static final String ACTION_LUNCH_CLICK = "com.example.messdays.ACTION_LUNCH_CLICK";
    public static final String ACTION_DINNER_CLICK = "com.example.messdays.ACTION_DINNER_CLICK";

    private static List<Integer> counts = new ArrayList<>();
    public static void updateWidget(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context, MyWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widgetComponent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Set up the intent that starts the MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);



        MessDayEvent messDayEvent = getCurrentMessEvent(context).get(LocalDate.now());


        counts = getCounts(context);

        Log.d("MyWidget", "Updating widget " +counts);

        // Initialize text views
        views.setTextViewText(R.id.widget_date_header, LocalDate.now().toString());
        views.setTextViewText(R.id.widget_lunch_count  , Integer.toString(counts.get(0)));
        views.setTextViewText(R.id.widget_dinner_count, Integer.toString(counts.get(1)));
        views.setTextViewText(R.id.widget_mess_expenses, " ₹" +counts.get(2));

        views.setImageViewResource(R.id.lunchCheckBox, messDayEvent != null && messDayEvent.isHasLunch() ? R.drawable.meal : R.drawable.meal_gray);
        views.setImageViewResource(R.id.dinnerCheckBox, messDayEvent != null && messDayEvent.isHasDinner() ? R.drawable.meal : R.drawable.meal_gray);

        Intent lunchClickIntent = new Intent(context, MyWidget.class);
        lunchClickIntent.setAction(ACTION_LUNCH_CLICK);
        views.setOnClickPendingIntent(R.id.lunchCheckBox, PendingIntent.getBroadcast(context, 0, lunchClickIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        Intent dinnerClickIntent = new Intent(context, MyWidget.class);
        dinnerClickIntent.setAction(ACTION_DINNER_CLICK);
        views.setOnClickPendingIntent(R.id.dinnerCheckBox, PendingIntent.getBroadcast(context, 0, dinnerClickIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));


        appWidgetManager.updateAppWidget(widgetComponent, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidget(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        if (action != null) {
            Map<LocalDate, MessDayEvent> currentMonthEvents = getCurrentMessEvent(context);
            MessDayEvent todayEvent = currentMonthEvents.get(LocalDate.now());

            if (todayEvent == null) {
                todayEvent = new MessDayEvent(LocalDate.now(), false, false,"");
                currentMonthEvents.put(LocalDate.now(), todayEvent);
            }

            boolean needsUpdate = false;

            if (ACTION_LUNCH_CLICK.equals(action)) {
                Log.d("MyWidget", "Lunch checkbox clicked");
                todayEvent.setHasLunch(!todayEvent.isHasLunch());
                // Update background directly for immediate feedback
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                views.setImageViewResource(R.id.lunchCheckBox, todayEvent.isHasLunch() ? R.drawable.meal : R.drawable.meal_gray);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, MyWidget.class), views);
                needsUpdate = true;
            } else if (ACTION_DINNER_CLICK.equals(action)) {
                Log.d("MyWidget", "Dinner checkbox clicked");
                todayEvent.setHasDinner(!todayEvent.isHasDinner());
                needsUpdate = true;
            }

            // Update background directly for immediate feedback if dinner is clicked
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setImageViewResource(R.id.dinnerCheckBox, todayEvent.isHasDinner() ? R.drawable.meal : R.drawable.meal_gray);
            AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, MyWidget.class), views);
            if (needsUpdate) {
                // Save the updated map
                Map<String, Map<LocalDate, MessDayEvent>> monthMap = getMonthMap(context);
                monthMap.put(monthYearFromDate(LocalDate.now()), currentMonthEvents);
                saveMonthMap(context, monthMap);

                // Update the widget UI
                updateWidget(context);
            }
        }
    }

    private static void saveMonthMap(Context context, Map<String, Map<LocalDate, MessDayEvent>> monthMap) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = gson.toJson(monthMap);
        editor.putString(MONTH_MAP_KEY, json);
        editor.apply();
    }

    private static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static Map<String,Map<LocalDate, MessDayEvent>> getMonthMap(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = prefs.getString(MONTH_MAP_KEY, null);

        Type type = new TypeToken<HashMap<String, HashMap<LocalDate, MessDayEvent>>>(){}.getType();
        Map<String, Map<LocalDate, MessDayEvent>> monthMap = gson.fromJson(json, type);
        return monthMap != null ? monthMap : new HashMap<>(); // Return empty map if null
    }

    public static Map<LocalDate, MessDayEvent> getCurrentMessEvent(Context context){
        Map<String, Map<LocalDate, MessDayEvent>> monthMap = getMonthMap(context);
        String currentMonthKey = monthYearFromDate(LocalDate.now());
        // Ensure the current month exists in the map
        return monthMap.computeIfAbsent(currentMonthKey, k -> new HashMap<>());
    }

    public static List<Integer> getCounts(Context context) {
        List<Integer> counts = new ArrayList<>();
        Map<LocalDate, MessDayEvent> currentMonthEvents = getCurrentMessEvent(context);
        long totalLunchCount = 0;
        long totalDinnerCount = 0;


        if (currentMonthEvents != null) {
            for (MessDayEvent event : currentMonthEvents.values()) {

                if (event.isHasLunch()) {
                    totalLunchCount++;
                }
                if (event.isHasDinner()) {
                    totalDinnerCount++;
                }
            }
        }
        long lunchAmount = totalLunchCount * 60;
        long dinnerAmount = totalDinnerCount * 60;
        long messExpenses = lunchAmount + dinnerAmount;
        counts.clear();
        counts.add((int) totalLunchCount);
        counts.add((int) totalDinnerCount);
        counts.add((int) messExpenses);

        return counts;
    }



}
