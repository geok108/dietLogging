package com.example.root.dietlogging.Entities;

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

    private float protein;

    private float carbohydrates;

    private float fat;

    private float energy;

    private float totalSugars;

    @NonNull
    private String date;

    @NonNull
    private String time;

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


    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getTotalSugars() {
        return totalSugars;
    }

    public void setTotalSugars(float totalSugars) {
        this.totalSugars = totalSugars;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
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


    public Diary(@NonNull Integer id, @NonNull String foodId, @NonNull String foodName, float protein, float fat, float carbohydrates, float energy, float totalSugars, @NonNull String date, @NonNull String time, @NonNull String meal, @NonNull float grams, @NonNull int hunger) {
        this.id = id;
        this.foodId = foodId;
        this.foodName = foodName;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.energy = energy;
        this.totalSugars = totalSugars;
        this.date = date;
        this.time = time;
        this.meal = meal;
        this.grams = grams;
        this.hunger = hunger;
    }
}

