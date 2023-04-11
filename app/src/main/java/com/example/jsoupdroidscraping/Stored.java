package com.example.jsoupdroidscraping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Stored extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static Stored instance;

    public static synchronized Stored getInstance(Context context) {
        if (instance == null) {
            instance = new Stored(context.getApplicationContext());
        }
        return instance;
    }

    private Stored(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your database schema here
        db.execSQL("CREATE TABLE mytable (name TEXT , description TEXT, listtype INTEGER,PRIMARY KEY (name,listtype) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }

    // Define your database operations here
    public void addData(String name, String description, int listtype) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("listtype",listtype);
        db.insert("mytable", null, values);
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM mytable", null);
    }

    public void deleteData(String name, int listtype) {
    SQLiteDatabase db = getWritableDatabase();
    int rowsDeleted = db.delete("mytable", "name = ? AND listtype = ?", new String[]{name, String.valueOf(listtype)});
    db.close();
        Log.d("Database", "Deleted " + rowsDeleted + " rows from the database");

    }
    public Cursor getAllDataByListType(int listType) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"id", "name", "description"};
        String selection = "listtype=?";
        String[] selectionArgs = {String.valueOf(listType)};
        return db.query("mytable", columns, selection, selectionArgs, null, null, null);
    }

}

