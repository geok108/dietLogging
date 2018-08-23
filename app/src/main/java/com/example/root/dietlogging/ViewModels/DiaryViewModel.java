package com.example.root.dietlogging.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.root.dietlogging.Entities.Diary;
import com.example.root.dietlogging.Repositories.DiaryRepository;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {

    private DiaryRepository mRepository;

    public DiaryViewModel(Application application) {
        super(application);
        mRepository = new DiaryRepository(application);
    }

    public LiveData<List<Diary>> getAllDiaries() { return mRepository.getAllDiaries(); }
    public LiveData<List<Diary>> getTodayDiary() { return mRepository.getTodayDiary(); }
    public LiveData<List<Diary>> getDiaryByDate(String date) { return mRepository.getDiaryByDate(date); }


    public void insert(Diary diary) { mRepository.insert(diary); }

    public void update(Diary diary) { mRepository.update(diary); }

    public void delete(Diary diary) { mRepository.delete(diary); }

}
