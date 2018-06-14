package com.example.root.dietlogging;

import android.app.Application;
import android.os.AsyncTask;

public class UserRepository {

    private UserDao userDao;

    UserRepository(Application application) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public void insert (User user) {
        new insertAsyncTask(userDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
