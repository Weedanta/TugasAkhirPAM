package com.example.tugasteori;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CRUDHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kontak.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;

    public CRUDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + PAPBLcontract.KontakTable.TABLE_NAME + " (" +
                PAPBLcontract.KontakTable.COLUMN_NAME_NAMA + " TEXT, " +
                PAPBLcontract.KontakTable.COLUMN_NAME_TELP + " TEXT PRIMARY KEY)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PAPBLcontract.KontakTable.TABLE_NAME);
        onCreate(db);
    }

    public void open() throws SQLException {
        database = getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public void insert(String nama, String telepon) throws SQLException {
        database.execSQL("INSERT INTO " + PAPBLcontract.KontakTable.TABLE_NAME +
                " VALUES (?, ?)", new Object[]{nama, telepon});
    }

    public Cursor fetch() throws SQLException {
        return database.rawQuery("SELECT * FROM " + PAPBLcontract.KontakTable.TABLE_NAME, null);
    }

    public void update(String oldNama, String oldTelepon, String newNama, String newTelepon) throws SQLException {
        database.execSQL("UPDATE " + PAPBLcontract.KontakTable.TABLE_NAME +
                        " SET " + PAPBLcontract.KontakTable.COLUMN_NAME_NAMA + " = ?, " +
                        PAPBLcontract.KontakTable.COLUMN_NAME_TELP + " = ? WHERE " +
                        PAPBLcontract.KontakTable.COLUMN_NAME_NAMA + " = ? AND " +
                        PAPBLcontract.KontakTable.COLUMN_NAME_TELP + " = ?",
                new Object[]{newNama, newTelepon, oldNama, oldTelepon});
    }

    public void delete(String telepon) throws SQLException {
        database.execSQL("DELETE FROM " + PAPBLcontract.KontakTable.TABLE_NAME +
                        " WHERE " + PAPBLcontract.KontakTable.COLUMN_NAME_TELP + " = ?",
                new Object[]{telepon});
    }
}
