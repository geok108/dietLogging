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
     * @return the instance of DatabaseAccess
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
    public ArrayList<Food> getFoodResults(String query) {
        ArrayList<Food> foodList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM food WHERE food_name LIKE '" + query + "%'", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String foodId = cursor.getString(0);
            String foodName = cursor.getString(1);
            String protein = cursor.getString(2);
            String fat = cursor.getString(3);
            String carbohydrate = cursor.getString(4);
            String energy = cursor.getString(5);
            String totalSugars = cursor.getString(6);

            foodList.add(new Food(foodId, foodName, protein, fat, carbohydrate, energy, totalSugars));

            cursor.moveToNext();
        }
        cursor.close();

        return foodList;
    }
}