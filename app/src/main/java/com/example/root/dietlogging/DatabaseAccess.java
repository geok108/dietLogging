package com.example.root.dietlogging;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all foods from the database.
     *
     * @return a List of foods
     */
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM food", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Search the food database based on the user query
     * @return a list of results
     */
    public List<String> getFoodResults(String query) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM food WHERE 'Food Name' LIKE ?", new String[] {query});
        //Log.d("CURSOR: ",cursor.getString(0));
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(String.valueOf(cursor.getString(0)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}