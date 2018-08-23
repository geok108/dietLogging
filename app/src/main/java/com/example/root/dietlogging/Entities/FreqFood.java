package com.example.root.dietlogging.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "freq_food")
public class FreqFood implements Comparable<FreqFood> {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private String foodId;

    @NonNull
    private Integer counter;

    @NonNull
    private float grams;

    @NonNull
    public float getGrams() {
        return grams;
    }

    public void setGrams(@NonNull float grams) {
        this.grams = grams;
    }

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
    public Integer getCounter() {
        return counter;
    }

    public void setCounter(@NonNull Integer counter) {
        this.counter = counter;
    }

    public FreqFood(@NonNull Integer id, @NonNull String foodId, @NonNull Integer counter, @NonNull float grams) {
        this.id = id;
        this.foodId = foodId;
        this.counter = counter;
        this.grams = grams;
    }


    @Override
    public int compareTo(@NonNull FreqFood fFood) {

        int cnt =  fFood.getCounter();
        return this.counter - cnt;
    }


}
