package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.adapter.RecyclerUserAdapter;
import com.example.recyclerview.data.NewUser;
import com.example.recyclerview.listeners.RemoveButtonListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<NewUser> userList = new ArrayList<>();
    RecyclerView userRecyclerView;
    LinearLayout deteleButtonLayout;
    Button deleteButton;

    @SuppressLint("NotifyDataSetChanged")
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


        userList.add(new NewUser("Ajaya Kumar", 23, "ajaya.kumar@example.com", "employee"));
        userList.add(new NewUser("Ravi Sharma", 28, "ravi.sharma@example.com", "employee"));
        userList.add(new NewUser("Sneha Patel", 25, "sneha.patel@example.com", "employee"));
        userList.add(new NewUser("Vikram Singh", 30, "vikram.singh@example.com", "employee"));
        userList.add(new NewUser("Anjali Mehra", 27, "anjali.mehra@example.com", "employee"));
        userList.add(new NewUser("Deepak Yadav", 29, "deepak.yadav@example.com", "employee"));
        userList.add(new NewUser("Kavita Joshi", 26, "kavita.joshi@example.com", "employee"));
        userList.add(new NewUser("Nikhil Verma", 24, "nikhil.verma@example.com", "employee"));
        userList.add(new NewUser("Pooja Rani", 31, "pooja.rani@example.com", "employee"));
        userList.add(new NewUser("Manoj Tiwari", 32, "manoj.tiwari@example.com", "employee"));
        userList.add(new NewUser("Alok Das", 28, "alok.das@example.com", "employee"));
        userList.add(new NewUser("Meena Kumari", 30, "meena.kumari@example.com", "employee"));
        userList.add(new NewUser("Suresh Babu", 35, "suresh.babu@example.com", "employee"));
        userList.add(new NewUser("Priya Sinha", 29, "priya.sinha@example.com", "employee"));
        userList.add(new NewUser("Rahul Kapoor", 27, "rahul.kapoor@example.com", "employee"));
        userList.add(new NewUser("Neha Gupta", 26, "neha.gupta@example.com", "employee"));
        userList.add(new NewUser("Rajesh Nair", 33, "rajesh.nair@example.com", "employee"));
        userList.add(new NewUser("Divya Rao", 24, "divya.rao@example.com", "employee"));
        userList.add(new NewUser("Amit Thakur", 31, "amit.thakur@example.com", "employee"));
        userList.add(new NewUser("Sonal Jain", 25, "sonal.jain@example.com", "employee"));


        userRecyclerView = findViewById(R.id.user_recycler_view);
        RecyclerUserAdapter recyclerUserAdapter = getRecyclerUserAdapter();
        userRecyclerView.setAdapter(recyclerUserAdapter);

        userRecyclerView.setOnClickListener(v ->{
            Toast.makeText(this,"Item Clicked",Toast.LENGTH_SHORT).show();
        });

        deteleButtonLayout = findViewById(R.id.detele_button_layout);
        deleteButton = findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(v -> {
            Iterator<NewUser> iterator = userList.iterator();
            while (iterator.hasNext()) {
                NewUser user = iterator.next();
                if (user.isChecked()) {
                    iterator.remove();
                }
            }
            recyclerUserAdapter.notifyDataSetChanged();
        });




    }

    @NonNull
    private RecyclerUserAdapter getRecyclerUserAdapter() {
        RecyclerUserAdapter recyclerUserAdapter = new RecyclerUserAdapter(userList,this);

        recyclerUserAdapter.onRemoveButtonClicked((view,position)->{
            Toast.makeText(this,"Removed " + userList.get(position).getName(),Toast.LENGTH_SHORT).show();
            userList.remove(position);
            recyclerUserAdapter.notifyItemRemoved(position);
        });
        recyclerUserAdapter.onCheckBoxChecked((view, isChecked, position) -> {
            userList.get(position).setChecked(isChecked);
            Log.d("Checked",isChecked+" "+userList.get(position).isChecked());
            Toast.makeText(this,"Checked " + userList.get(position).getName()+" "+isChecked,Toast.LENGTH_SHORT).show();
        });
        return recyclerUserAdapter;
    }
}