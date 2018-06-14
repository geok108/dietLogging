package com.example.root.dietlogging;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

}
