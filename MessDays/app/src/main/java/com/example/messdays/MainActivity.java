package com.example.messdays;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messdays.data.MessDayEvent;
import com.example.messdays.widget.MyWidget;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Declare your views and variables
    public static TextView monthYearTextView;
    private TextView  lunchCountTextView, dinnerCountTextView,messExpensesTextView;
    private RecyclerView calendarRecyclerView;
    public LocalDate selectedDate;
    public static Map<String,Map<LocalDate, MessDayEvent>> monthMap;

    private CalendarAdapter calendarAdapter;

    public static final String PREFS_NAME = "MessDaysPrefs";
    public static final String MONTH_MAP_KEY = "monthMap";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        monthMap = loadMonthMap();


        // Initialize views
        lunchCountTextView = findViewById(R.id.lunch_count);
        dinnerCountTextView = findViewById(R.id.dinner_count);
        messExpensesTextView = findViewById(R.id.mess_expenses);

        // Initialize views
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearTextView = findViewById(R.id.monthYearTextView);
        ImageView previousMonthButton = findViewById(R.id.previousMonthButton);
        ImageView nextMonthButton = findViewById(R.id.nextMonthButton);

        // Set the initial date to the current date
        selectedDate = LocalDate.now();

        // Set up the calendar view
        setMonthView();

        setTotalCount(); // Initialize total count for the current month
        // Set listeners for month navigation
        previousMonthButton.setOnClickListener(v -> previousMonthAction(v));
        nextMonthButton.setOnClickListener(v -> nextMonthAction(v));

        MyWidget.updateWidget(this);

    }

    private void setMonthView() {
        // Set the month and year in the header
        monthYearTextView.setText(monthYearFromDate(selectedDate));

        // Get the days in the month
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        // Create the adapter and set it to the RecyclerView
        calendarAdapter = new CalendarAdapter(daysInMonth, getItemListener(),daysInMonthArrayWithDates(selectedDate));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // Add empty strings for the days before the first of the month
        // We use 'dayOfWeek % 7' because in Java Time, Sunday is 7, not 1.
        for (int i = 1; i < (dayOfWeek == 7 ? 0 : dayOfWeek) + 1; i++) {
            daysInMonthArray.add("");
        }

        // Add the days of the month
        for (int i = 1; i <= daysInMonth; i++) {
            daysInMonthArray.add(String.valueOf(i));
        }
        return daysInMonthArray;
    }

    private List<LocalDate> daysInMonthArrayWithDates(LocalDate date) {
        List<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // Add nulls for the days before the first of the month
        // We use 'dayOfWeek % 7' because in Java Time, Sunday is 7, not 1.
        for (int i = 1; i < (dayOfWeek == 7 ? 0 : dayOfWeek) + 1; i++) {
            daysInMonthArray.add(null);
        }

        // Add the LocalDate objects for the days of the month
        for (int i = 1; i <= daysInMonth; i++) {
            daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i));
        }
        return daysInMonthArray;
    }


    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
        setTotalCount();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
        setTotalCount();
    }

    @SuppressLint("SetTextI18n")
    public CalendarAdapter.OnItemListener getItemListener(){
        return  (position, dayText,localDate,calendarViewHolder) -> {
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.calender_day_dialog, null);
            TextView dialogDateText = dialogView.findViewById(R.id.dialogDateText);
            EditText eventEditText = dialogView.findViewById(R.id.eventEditText);
            CheckBox lunchCheckBox = dialogView.findViewById(R.id.lunchCheckBox);
            CheckBox dinnerCheckBox = dialogView.findViewById(R.id.dinnerCheckBox);
            dialogDateText.setText("Date: " + dayText + " " + monthYearFromDate(selectedDate));

            if (MainActivity.monthMap.containsKey(monthYearFromDate(selectedDate))) {
                Map<LocalDate, MessDayEvent> eventsForMonth = monthMap.get(monthYearFromDate(selectedDate));
                // Check if there's an event for the specific localDate (the clicked day)
                if (eventsForMonth != null && eventsForMonth.containsKey(localDate)) {
                    MessDayEvent messDayEvent = eventsForMonth.get(localDate);
                    Log.d("MainActivity", messDayEvent.toString());
                    eventEditText.setText(messDayEvent.getEvent());
                    lunchCheckBox.setChecked(messDayEvent.isHasLunch());
                    dinnerCheckBox.setChecked(messDayEvent.isHasDinner());
                }
            }

//            Toast.makeText(this, "Selected Date " + localDate, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView).
                    setPositiveButton("Save", (dialog, which) -> {
                        String currentMonthKey = monthYearFromDate(selectedDate);
                        Map<LocalDate, MessDayEvent> monthSpecificEvents = monthMap.getOrDefault(currentMonthKey, new HashMap<>());
                        MessDayEvent messDayEvent = new MessDayEvent(localDate, lunchCheckBox.isChecked(), dinnerCheckBox.isChecked(), eventEditText.getText().toString());
                        monthSpecificEvents.put(localDate, messDayEvent);
                        monthMap.put(currentMonthKey, monthSpecificEvents);

                            setTotalCount();
                        if (messDayEvent.getMealPrice() == 60){
                            calendarViewHolder.linearLayout.setBackgroundResource(R.drawable.circular_background);
                        } else if (messDayEvent.getMealPrice() == 120) {
                            calendarViewHolder.linearLayout.setBackgroundResource(R.drawable.circular_background_2);
                        }
                            Toast.makeText(this, "Event Updated", Toast.LENGTH_SHORT).show();

                        Log.d("MainActivity", monthMap.toString());

                        if (!messDayEvent.isHasDinner() && !messDayEvent.isHasLunch())
                            calendarViewHolder.linearLayout.setBackgroundResource(0);

                        dialog.dismiss();
                    }).
                    setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setCancelable(true);

            AlertDialog dialog = builder.create();
            dialog.show();
//            Toast.makeText(this, "Selected Date " + dayText + " " + monthYearFromDate(selectedDate), Toast.LENGTH_SHORT).show();
        };
    }

    public  void setTotalCount() {
        String currentMonthName = monthYearFromDate(selectedDate);
        Map<LocalDate, MessDayEvent> currentMonthEvents = monthMap.get(currentMonthName);
        long totalLunchCount = 0;
        long totalDinnerCount = 0;
        long totalMessExpenses = 0;

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


        MyWidget.updateWidget(this);

        lunchCountTextView.setText(String.format("%d : ₹%d", totalLunchCount, lunchAmount));
        dinnerCountTextView.setText(String.format("%d : ₹%d", totalDinnerCount, dinnerAmount));
        messExpensesTextView.setText(String.format("₹%d", messExpenses));

    }

    private void saveMonthMap() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = gson.toJson(monthMap);
        editor.putString(MONTH_MAP_KEY, json);
        editor.apply();
        Log.d("MainActivity1", "MonthMap saved to SharedPreferences" +loadMonthMap());
    }

    private Map<String,Map<LocalDate, MessDayEvent>> loadMonthMap() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = prefs.getString(MONTH_MAP_KEY, null);
        Type type = new TypeToken<HashMap<String, HashMap<LocalDate, MessDayEvent>>>(){}.getType();

        if (json != null) {
            Map<String, Map<LocalDate, MessDayEvent>> loadedMap = gson.fromJson(json, type);
            Log.d("MainActivity1", "MonthMap loaded from SharedPreferences"+ loadedMap.toString());
            return loadedMap;
        } else {
            Log.d("MainActivity1", "No MonthMap found in SharedPreferences, returning new HashMap");
            return new HashMap<>();
        }
    }
    @Override
    protected void onPause() {
        saveMonthMap();
        MyWidget.updateWidget(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // It's crucial that monthMap is loaded before setMonthView or setTotalCount is called.
        // If loadMonthMap might re-assign monthMap, ensure it's called before dependent methods.
        setMonthView();
        monthMap = loadMonthMap();
        calendarAdapter.notifyDataSetChanged();
        setTotalCount();
        super.onResume();
    }
}
