package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FreqFoodRepository {


    private FreqFoodDao mFreqFoodDao;

    FreqFoodRepository(Application application) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        this.mFreqFoodDao = db.freqFoodDao();
    }

    List<FreqFood> getFreqFoods() {
        return this.mFreqFoodDao.getFreqFoods();
    }

    public void insert(FreqFood food) {
        new FreqFoodRepository.insertAsyncTask(mFreqFoodDao).execute(food);
    }

    public void update(FreqFood food) {
        new FreqFoodRepository.updateAsyncTask(mFreqFoodDao).execute(food);
    }


    private static class insertAsyncTask extends AsyncTask<FreqFood, Void, Void> {

        private FreqFoodDao mAsyncTaskDao;

        insertAsyncTask(FreqFoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FreqFood... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<FreqFood, Void, Void> {
        private FreqFoodDao mAsyncTaskDao;

        public updateAsyncTask(FreqFoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(FreqFood... params) {
            mAsyncTaskDao.update(params[0]);

            return null;
        }
    }


}


