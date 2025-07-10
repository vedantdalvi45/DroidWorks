package com.example.tuitionapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuitionapp.subject.RecyclerSubjectAdapter;
import com.example.tuitionapp.subject.Subjects;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView subjects_recyclerview;
    ArrayList<Subjects> subjects = new ArrayList<>();
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

        subjects_recyclerview = findViewById(R.id.subjects_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        subjects_recyclerview.setLayoutManager(linearLayoutManager);

        subjects.add(new Subjects(R.drawable.maths, "Maths"));
        subjects.add(new Subjects(R.drawable.subject, "Subject"));
        subjects.add(new Subjects(R.drawable.science, "Science"));
        subjects.add(new Subjects(R.drawable.english, "English"));

        RecyclerSubjectAdapter recyclerSubjectAdapter =  new RecyclerSubjectAdapter(this, subjects);
        subjects_recyclerview.setAdapter(recyclerSubjectAdapter);
    }
}