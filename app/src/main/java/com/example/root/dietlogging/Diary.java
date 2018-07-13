package com.example.root.dietlogging;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "diary")
public class Diary {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private String foodId;

    @NonNull
    private String foodName;

    @NonNull
    private String dateTime;

    @NonNull
    private String meal;

    @NonNull
    private float grams;

    @NonNull
    private int hunger;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(@NonNull String foodId) {
        this.foodId = foodId;
    }

    @NonNull
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(@NonNull String foodName) {
        this.foodName = foodName;
    }

    @NonNull
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(@NonNull String dateTime) {
        this.dateTime = dateTime;
    }

    @NonNull
    public String getMeal() {
        return meal;
    }

    public void setMeal(@NonNull String meal) {
        this.meal = meal;
    }

    @NonNull
    public float getGrams() {
        return grams;
    }

    public void setGrams(@NonNull float grams) {
        this.grams = grams;
    }

    @NonNull
    public int getHunger() {
        return hunger;
    }

    public void setHunger(@NonNull int hunger) {
        this.hunger = hunger;
    }


    public Diary(@NonNull Integer id, @NonNull String foodId, @NonNull String foodName, @NonNull String dateTime, @NonNull String meal, @NonNull float grams, @NonNull int hunger) {
        this.id = id;
        this.foodId = foodId;
        this.foodName = foodName;
        this.dateTime = dateTime;
        this.meal = meal;
        this.grams = grams;
        this.hunger = hunger;
    }
}

