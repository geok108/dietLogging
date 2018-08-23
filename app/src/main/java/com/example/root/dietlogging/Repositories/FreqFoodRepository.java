package com.example.root.dietlogging.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.root.dietlogging.DietLoggingRoomDatabase;
import com.example.root.dietlogging.Entities.FreqFood;
import com.example.root.dietlogging.Daos.FreqFoodDao;

import java.util.List;

public class FreqFoodRepository {


    private FreqFoodDao mFreqFoodDao;

    public FreqFoodRepository(Application application) {
        DietLoggingRoomDatabase db = DietLoggingRoomDatabase.getDatabase(application);
        this.mFreqFoodDao = db.freqFoodDao();
    }

    public List<FreqFood> getFreqFoods() {
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


