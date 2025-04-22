package com.example.tugasteori;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CrudActivity extends AppCompatActivity {

    private CRUDHelper crudHelper;
    private EditText etNama, etTelepon, etOldTelepon, etNewNama, etNewTelepon, etDeleteTelepon;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        etNama = findViewById(R.id.etNama);
        etTelepon = findViewById(R.id.etTelepon);
        etOldTelepon = findViewById(R.id.etOldTelepon);
        etNewNama = findViewById(R.id.etNewNama);
        etNewTelepon = findViewById(R.id.etNewTelepon);
        etDeleteTelepon = findViewById(R.id.etDeleteTelepon);
        tvStatus = findViewById(R.id.tvStatus);

        crudHelper = new CRUDHelper(this);

        try {
            crudHelper.open();
        } catch (SQLException e) {
            showStatus("Error membuka database: " + e.getMessage());
        }

        findViewById(R.id.btnInsert).setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String telepon = etTelepon.getText().toString().trim();

            if (nama.isEmpty() || telepon.isEmpty()) {
                showStatus("Nama dan Telepon harus diisi!");
                return;
            }

            try {
                crudHelper.insert(nama, telepon);
                showStatus("Insert berhasil!");
                clearInsertFields();
            } catch (SQLException e) {
                showStatus("Insert gagal: " + e.getMessage());
            }
        });

        findViewById(R.id.btnUpdate).setOnClickListener(v -> {
            String oldTelepon = etOldTelepon.getText().toString().trim();
            String newNama = etNewNama.getText().toString().trim();
            String newTelepon = etNewTelepon.getText().toString().trim();

            if (oldTelepon.isEmpty() || newNama.isEmpty() || newTelepon.isEmpty()) {
                showStatus("Semua field update harus diisi!");
                return;
            }

            try {
                Cursor cursor = crudHelper.fetch();
                String oldNama = "";
                if (cursor.moveToFirst()) {
                    do {
                        String currentTelepon = cursor.getString(cursor.getColumnIndexOrThrow(PAPBLcontract.KontakTable.COLUMN_NAME_TELP));
                        if (currentTelepon.equals(oldTelepon)) {
                            oldNama = cursor.getString(cursor.getColumnIndexOrThrow(PAPBLcontract.KontakTable.COLUMN_NAME_NAMA));
                            break;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();

                if (oldNama.isEmpty()) {
                    showStatus("Telepon tidak ditemukan!");
                    return;
                }

                crudHelper.update(oldNama, oldTelepon, newNama, newTelepon);
                showStatus("Update berhasil!");
                clearUpdateFields();

            } catch (SQLException e) {
                showStatus("Update gagal: " + e.getMessage());
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            String telepon = etDeleteTelepon.getText().toString().trim();
            if (telepon.isEmpty()) {
                showStatus("Telepon harus diisi!");
                return;
            }

            try {
                crudHelper.delete(telepon);
                showStatus("Delete berhasil!");
                clearDeleteField();
            } catch (SQLException e) {
                showStatus("Delete gagal: " + e.getMessage());
            }
        });
    }

    private void showStatus(String message) {
        tvStatus.setText(message);
    }

    private void clearInsertFields() {
        etNama.setText("");
        etTelepon.setText("");
    }

    private void clearUpdateFields() {
        etOldTelepon.setText("");
        etNewNama.setText("");
        etNewTelepon.setText("");
    }

    private void clearDeleteField() {
        etDeleteTelepon.setText("");
    }

    @Override
    protected void onDestroy() {
        crudHelper.close();
        super.onDestroy();
    }
}
