package com.example.root.dietlogging;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {

    @NonNull
    private Integer participant_number;

    private String full_name;

    @NonNull
    private Integer diet_choice;

    public User(Integer diet_choice) {this.diet_choice = diet_choice;}

    public Integer getDiet_choice() {return this.diet_choice;}
}
