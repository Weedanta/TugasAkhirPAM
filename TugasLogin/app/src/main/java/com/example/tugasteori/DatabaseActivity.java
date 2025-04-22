package com.example.tugasteori;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    private ListView lvKontak;
    private CRUDHelper crudHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        lvKontak = findViewById(R.id.lvKontak);
        crudHelper = new CRUDHelper(this);
        Button btnEdit = findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrudActivity.class);
            startActivity(intent);
        });

        try {
            crudHelper.open();
            tampilkanData();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal membuka database", Toast.LENGTH_SHORT).show();
        }
    }

    private void tampilkanData() {
        Cursor cursor = crudHelper.fetch();
        ArrayList<String> daftarKontak = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String nama = cursor.getString(cursor.getColumnIndexOrThrow(PAPBLcontract.KontakTable.COLUMN_NAME_NAMA));
                String telepon = cursor.getString(cursor.getColumnIndexOrThrow(PAPBLcontract.KontakTable.COLUMN_NAME_TELP));
                daftarKontak.add(nama + " - " + telepon);
            } while (cursor.moveToNext());
        } else {
            daftarKontak.add("Data kosong");
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftarKontak);
        lvKontak.setAdapter(adapter);
    }

    protected void onResume() {
        super.onResume();
        tampilkanData();
    }

    @Override
    protected void onDestroy() {
        crudHelper.close();
        super.onDestroy();
    }
}
