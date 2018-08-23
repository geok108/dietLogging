package com.example.root.dietlogging.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.root.dietlogging.Entities.FreqFood;

import java.util.List;

@Dao
public interface FreqFoodDao {

    @Insert
    void insert(FreqFood food);

    @Update
    void update(FreqFood food);

    @Query("SELECT * from freq_food")
    List<FreqFood> getFreqFoods();
}
