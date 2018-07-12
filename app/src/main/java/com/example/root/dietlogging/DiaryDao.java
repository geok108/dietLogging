package com.example.root.dietlogging;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DiaryDao {


    @Insert
    void insert(Diary diary);

    @Update
    void update(Diary diary);

    @Delete
    void delete(Diary diary);

    @Query("DELETE FROM diary")
    void deleteAll();

    @Query("SELECT * from diary ORDER BY id ASC")
    LiveData<List<Diary>> getAllEntries();


}
