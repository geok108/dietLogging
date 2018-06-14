package com.example.root.dietlogging;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1)
public abstract class DietLoggingRoomDatabase extends RoomDatabase {

        public abstract UserDao userDao();

        private static DietLoggingRoomDatabase INSTANCE;

        public static DietLoggingRoomDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (DietLoggingRoomDatabase.class) {
                    if (INSTANCE == null) {
                        // Create database here
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                DietLoggingRoomDatabase.class, "diet_logging_database")
                                .build();


                    }
                }
            }
            return INSTANCE;
        }
    }




