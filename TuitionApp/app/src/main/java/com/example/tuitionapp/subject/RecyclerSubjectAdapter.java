package com.example.tuitionapp.subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuitionapp.R;

import java.util.ArrayList;

public class RecyclerSubjectAdapter extends RecyclerView.Adapter<RecyclerSubjectAdapter.ViewHolder> {
    Context context;
    ArrayList<Subjects> subject_list;
    public RecyclerSubjectAdapter(Context context, ArrayList<Subjects> subjects) {
        this.context = context;
        this.subject_list = subjects;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subject_image.setImageResource(subject_list.get(position).image);
        holder.subject_name.setText(subject_list.get(position).name);
    }

    @Override
    public int getItemCount() {
        return subject_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView subject_image;
        TextView subject_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject_image = itemView.findViewById(R.id.subject_image);
            subject_name = itemView.findViewById(R.id.subject_name);
        }
    }
}
