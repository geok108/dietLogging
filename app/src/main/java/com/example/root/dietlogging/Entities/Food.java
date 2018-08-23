package com.example.root.dietlogging.Entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "food")
public class Food {

    private String foodCode;

    private String foodName;

    private String protein;

    private String fat;

    private String carbohydrate;

    private String energy;

    private String totalSugars;


    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getTotalSugars() {
        return totalSugars;
    }

    public void setTotalSugars(String totalSugars) {
        this.totalSugars = totalSugars;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public Food(String foodId, String foodName, String protein, String fat, String carbohydrate, String energy, String totalSugars) {
        this.foodCode = foodId;
        this.foodName = foodName;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.energy = energy;
        this.totalSugars = totalSugars;
    }
}
