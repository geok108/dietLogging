package com.example.root.dietlogging;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {

    @NonNull
    @PrimaryKey
    private int participant_number;

    private String full_name;

    @NonNull
    private int diet_choice;

    public User(@NonNull int participant_number, String full_name, int diet_choice) {
        this.participant_number = participant_number;
        this.full_name = full_name;
        this.diet_choice = diet_choice;

    }


    @NonNull
    public int getDiet_choice() {
        return diet_choice;
    }

    public int getParticipant_number() {
        return participant_number;
    }

    public void setParticipant_number(@NonNull int participant_number) {
        this.participant_number = participant_number;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setDiet_choice(@NonNull int diet_choice) {
        this.diet_choice = diet_choice;
    }


}
