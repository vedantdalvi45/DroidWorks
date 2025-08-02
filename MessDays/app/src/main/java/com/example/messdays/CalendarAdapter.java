package com.example.messdays;

import android.view.LayoutInflater;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private List<LocalDate> events;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, List<LocalDate> events) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.events = events;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        // Ensure the cell is not too wide
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getWidth() / 7 * 0.9); // Make height proportional to width
        return new CalendarViewHolder(view, onItemListener);
    }
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        holder.localDate = events.get(position);

        LocalDate currentDate = LocalDate.now();
        if (holder.localDate != null && holder.localDate.equals(currentDate)) {
            holder.dayOfMonth.setTextSize(30);
            holder.dayOfMonth.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    // Interface for handling clicks on calendar days
    public interface OnItemListener {
        void onItemClick(int position, String dayText,LocalDate localDate,CalendarViewHolder calendarViewHolder);
    }

    // ViewHolder for each calendar cell
    public static class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth;
        public LinearLayout linearLayout;
        private final OnItemListener onItemListener;
        private  LocalDate localDate;


        public CalendarViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.dayOfMonthText);
            this.linearLayout = itemView.findViewById(R.id.calender_item_bg);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText(),localDate,this);
        }
    }
}
