package com.example.root.dietlogging;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {User.class, Diary.class, FreqFood.class}, version = 9)
public abstract class DietLoggingRoomDatabase extends RoomDatabase {

        public abstract UserDao userDao();

        public abstract DiaryDao diaryDao();

        public abstract FreqFoodDao freqFoodDao();

        private static DietLoggingRoomDatabase INSTANCE;

        public static DietLoggingRoomDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (DietLoggingRoomDatabase.class) {
                    if (INSTANCE == null) {
                        // Create database here
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                DietLoggingRoomDatabase.class, "diet_logging_database")
                                .addCallback(sRoomDatabaseCallback)
                                .build();


                    }
                }
            }
            return INSTANCE;
        }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao uDao;

        PopulateDbAsync(DietLoggingRoomDatabase db) {
            uDao = db.userDao();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            //uDao.deleteAll();
            //User user = new User(874,"John", 2);
            //uDao.insert(user);

            return null;
        }
    }


    }






