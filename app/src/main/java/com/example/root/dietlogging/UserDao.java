package com.example.root.dietlogging;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * from user")
    LiveData<List<User>> getUser();

    @Query("DELETE from user")
    void deleteAll();

    @Insert
    void insert(User user);

    @Update
    void update(User user);

}
