package com.example.contactsclonningapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsclonningapp.contacts.Contacts;
import com.example.contactsclonningapp.contacts.RecyclerContactsAdapter;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CONTACT = 0;
    ArrayList<Contacts> contactList = new ArrayList<>();
    RecyclerView contactListRecycler;
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactListRecycler = findViewById(R.id.contact_list);
        contactListRecycler.setLayoutManager(linearLayoutManager);
        getContacts();
//        contactList.add(new Contacts(R.drawable.ic_launcher_background,"ajay",23131));
        contactListRecycler.setAdapter(new RecyclerContactsAdapter(this, contactList));


    }

    void getContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_CONTACT);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        Log.d("Contacts", "Contacts:"+cursor.getCount());

        if (cursor.getColumnCount() > 0){
            while (cursor.moveToNext()) {
                int image =
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String contact = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contacts contacts = new Contacts(R.drawable.ic_launcher_background, name, contact);
                contactList.add(contacts);
            }
        }

    }


}