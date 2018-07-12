package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {

    private DiaryRepository mRepository;

    private LiveData<List<Diary>> mAllEntries;

    public DiaryViewModel(Application application) {
        super(application);
        mRepository = new DiaryRepository(application);
        mAllEntries = mRepository.getAllEntries();
    }

    LiveData<List<Diary>> getAllEntries() { return mAllEntries; }

    public void insert(Diary diary) { mRepository.insert(diary); }

    public void update(Diary diary) { mRepository.update(diary); }

    public void delete(Diary diary) { mRepository.delete(diary); }

}
