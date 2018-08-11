package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FreqFoodViewModel extends AndroidViewModel {
    private FreqFoodRepository mRepository;

    public FreqFoodViewModel(Application application) {
        super(application);
        mRepository = new FreqFoodRepository(application);
    }

    List<FreqFood> getAll() { return mRepository.getAll(); }

    public void insert(FreqFood food) { mRepository.insert(food); }
    public void update(FreqFood food) { mRepository.update(food); }


}
