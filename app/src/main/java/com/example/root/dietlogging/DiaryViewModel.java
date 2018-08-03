package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {

    private DiaryRepository mRepository;

    private LiveData<List<Diary>> mAllEntries;

    private LiveData<List<Diary>> mTodayEntries;



    public DiaryViewModel(Application application, String mParam) {
        super(application);
        mRepository = new DiaryRepository(application, mParam);
        mAllEntries = mRepository.getAllEntries();
        mTodayEntries = mRepository.getTodayEntries(mParam);
        Log.w("mparam", mParam);

    }

    LiveData<List<Diary>> getAllEntries() { return mAllEntries; }
    LiveData<List<Diary>> getTodayEntries(String date) { return mTodayEntries; }

    public void insert(Diary diary) { mRepository.insert(diary); }

    public void update(Diary diary) { mRepository.update(diary); }

    public void delete(Diary diary) { mRepository.delete(diary); }

}
