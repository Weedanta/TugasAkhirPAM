package com.example.tugasteori;

import android.provider.BaseColumns;

public final class PAPBLcontract {

    private PAPBLcontract() {}

    public static class KontakTable implements BaseColumns {
        public static final String TABLE_NAME = "kontak";
        public static final String COLUMN_NAME_NAMA = "nama";
        public static final String COLUMN_NAME_TELP = "telepon";
    }
}