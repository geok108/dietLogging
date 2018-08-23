package com.example.root.dietlogging.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.root.dietlogging.Entities.FreqFood;
import com.example.root.dietlogging.Repositories.FreqFoodRepository;

import java.util.List;

public class FreqFoodViewModel extends AndroidViewModel {
    private FreqFoodRepository mRepository;

    public FreqFoodViewModel(Application application) {
        super(application);
        mRepository = new FreqFoodRepository(application);
    }

    public List<FreqFood> getFreqFoods() { return mRepository.getFreqFoods(); }

    public void insert(FreqFood food) { mRepository.insert(food); }
    public void update(FreqFood food) { mRepository.update(food); }


}
