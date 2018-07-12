package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DiaryRepository {


    private DiaryDao mDiaryDao;
    private LiveData<List<Diary>> mAllDiaryEntries;

    DiaryRepository(Application application) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        mDiaryDao = db.diaryDao();
        mAllDiaryEntries = mDiaryDao.getAllEntries();

    }

    LiveData<List<Diary>> getAllEntries() {
        return mAllDiaryEntries;
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
        protected Void doInBackground(Diary... diaries) {
            mAsyncTaskDao.update(diaries[0]);

            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao mAsyncTaskDao;

        public deleteAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Diary... diaries) {
            mAsyncTaskDao.delete(diaries[0]);

            return null;
        }
    }
}
