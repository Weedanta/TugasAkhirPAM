package com.example.tugasteori;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Hasbi", "+62 812 3456 7890", "hasbi@gmail.com", "hasbi"));
        contacts.add(new Contact("Hasbi2", "+62 813 2345 6789", "hasbi@gmail.com", "hasbi"));
        contacts.add(new Contact("Hasbi3", "+62 821 2345 6789", "hasbi@gmail.com", "hasbi"));

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        ContactAdapter adapter = new ContactAdapter(this, contacts);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText filterEditText = findViewById(R.id.filterEditText);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}