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

    LiveData<List<Diary>> getAllEntries() { return mRepository.getAllEntries(); }
    LiveData<List<Diary>> getTodayEntries() { return mRepository.getTodayEntries(); }
    LiveData<List<Diary>> getDateEntries(String date) { return mRepository.getDateEntries(date); }


    public void insert(Diary diary) { mRepository.insert(diary); }

    public void update(Diary diary) { mRepository.update(diary); }

    public void delete(Diary diary) { mRepository.delete(diary); }

}
