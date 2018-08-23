package com.example.root.dietlogging.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private int participant_number;

    private String full_name;

    @NonNull
    private int diet_choice;

    @NonNull
    private int notification_frequency;

    @NonNull
    public int getNotification_frequency() {
        return notification_frequency;
    }

    public void setNotification_frequency(@NonNull int notification_frequency) {
        this.notification_frequency = notification_frequency;
    }


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
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


    public User(@NonNull Integer id, @NonNull int participant_number, String full_name, @NonNull int diet_choice, @NonNull int notification_frequency) {
        this.id = id;
        this.participant_number = participant_number;
        this.full_name = full_name;
        this.diet_choice = diet_choice;
        this.notification_frequency = notification_frequency;
    }
}
