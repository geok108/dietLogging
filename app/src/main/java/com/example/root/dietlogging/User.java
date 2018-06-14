package com.example.root.dietlogging;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {

    @NonNull
    @PrimaryKey
    private Integer participant_number;

    @NonNull
    private String full_name;

    @NonNull
    private Integer diet_choice;

    @NonNull
    public Integer getDiet_choice() {
        return diet_choice;
    }

    public Integer getParticipant_number() {
        return participant_number;
    }

    public void setParticipant_number(@NonNull Integer participant_number) {
        this.participant_number = participant_number;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setDiet_choice(@NonNull Integer diet_choice) {
        this.diet_choice = diet_choice;
    }
}
