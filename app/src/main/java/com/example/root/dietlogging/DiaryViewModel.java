package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {

    private DiaryRepository mRepository;

    public DiaryViewModel(Application application) {
        super(application);
        mRepository = new DiaryRepository(application);
    }

    LiveData<List<Diary>> getAllDiaries() { return mRepository.getAllDiaries(); }
    LiveData<List<Diary>> getTodayDiary() { return mRepository.getTodayDiary(); }
    LiveData<List<Diary>> getDiaryByDate(String date) { return mRepository.getDiaryByDate(date); }


    public void insert(Diary diary) { mRepository.insert(diary); }

    public void update(Diary diary) { mRepository.update(diary); }

    public void delete(Diary diary) { mRepository.delete(diary); }

}
