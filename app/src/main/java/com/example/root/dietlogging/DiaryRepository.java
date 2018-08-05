package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryRepository {


    private DiaryDao mDiaryDao;
    private LiveData<List<Diary>> mAllDiaryEntries;
    private LiveData<List<Diary>> mTodayEntries;
    private LiveData<List<Diary>> mDateEntries;


    DiaryRepository(Application application, String mParam) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        mDiaryDao = db.diaryDao();
        mAllDiaryEntries = mDiaryDao.getAllEntries();

        mTodayEntries = mDiaryDao.getTodayEntries(mParam);
        mDateEntries = mDiaryDao.getDateEntries(mParam);

    }

    LiveData<List<Diary>> getAllEntries() {
        return mAllDiaryEntries;
    }

    LiveData<List<Diary>> getTodayEntries(String date) {
        return mTodayEntries;
    }

    LiveData<List<Diary>> getDateEntries(String date) {
        return mDateEntries;
    }


    public void insert (Diary diary) {
        new insertAsyncTask(mDiaryDao).execute(diary);
    }
    public void update (Diary diary) {
        new updateAsyncTask(mDiaryDao).execute(diary);
    }
    public void delete (Diary diary) {
        new deleteAsyncTask(mDiaryDao).execute(diary);
    }



    private static class insertAsyncTask extends AsyncTask<Diary, Void, Void> {

        private DiaryDao mAsyncTaskDao;

        insertAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Diary... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao mAsyncTaskDao;

        public updateAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Diary... params) {
            mAsyncTaskDao.update(params[0]);

            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao mAsyncTaskDao;

        public deleteAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Diary... params) {
            mAsyncTaskDao.delete(params[0]);

            return null;
        }
    }
}
