package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText userMessage;
    Button counter;
    CheckBox remember;
    int count = 0;
    String name,message;

    SharedPreferences sharedPreferences;

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

        username = findViewById(R.id.editTextText);
        userMessage = findViewById(R.id.editTextTextMultiLine);
        counter = findViewById(R.id.button);
        remember = findViewById(R.id.checkBox);

        counter.setOnClickListener(v -> counter.setText(String.valueOf(count++)));

        retrieveData();

    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }


    public void saveData(){
        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        name = username.getText().toString();
        message = userMessage.getText().toString();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.putString("message",message);
        editor.putInt("count", Integer.parseInt(counter.getText().toString()));
        editor.putBoolean("rememberMe",remember.isChecked());
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    public void retrieveData(){
        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString("name",""));
        userMessage.setText(sharedPreferences.getString("message",""));
        counter.setText(String.valueOf(sharedPreferences.getInt("count",0)));
        remember.setChecked(sharedPreferences.getBoolean("rememberMe",false));
    }
}