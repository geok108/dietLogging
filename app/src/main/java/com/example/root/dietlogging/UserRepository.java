package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mUser;

    UserRepository(Application application) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mUser = mUserDao.getUser();

    }

    LiveData<List<User>> getUser() {
        return mUser;
    }

    public void insert (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }
    public void update (User user) {
        new updateAsyncTask(mUserDao).execute(user);
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

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        updateAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

}
