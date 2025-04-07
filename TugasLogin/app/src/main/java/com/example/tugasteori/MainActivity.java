package com.example.tugasteori;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menyiapkan data
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Lily", "+1 234", "lily@aol.com", "avatar1"));
        contacts.add(new Contact("John", "+1 235", "john@aol.com", "avatar2"));
        // Tambahkan kontak lainnya...

        // Mendapatkan RecyclerView dari layout
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        ContactAdapter adapter = new ContactAdapter(this, contacts);

        // Menetapkan Adapter dan LayoutManager
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}