package com.example.root.dietlogging.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.root.dietlogging.Entities.Diary;
import com.example.root.dietlogging.Daos.DiaryDao;
import com.example.root.dietlogging.DietLoggingRoomDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DiaryRepository {

    private DiaryDao mDiaryDao;

    public DiaryRepository(Application application) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        this.mDiaryDao = db.diaryDao();
    }

    public LiveData<List<Diary>> getAllDiaries() {
        return this.mDiaryDao.getAllDiaries();
    }

    public LiveData<List<Diary>> getTodayDiary() {
        String dt = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        return this.mDiaryDao.getTodayDiary(dt);
    }

    public LiveData<List<Diary>> getDiaryByDate(String date) {
        return this.mDiaryDao.getDiaryByDate(date);
    }


    public void insert(Diary diary) {
        new insertAsyncTask(mDiaryDao).execute(diary);
    }

    public void update(Diary diary) {
        new updateAsyncTask(mDiaryDao).execute(diary);
    }

    public void delete(Diary diary) {
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
