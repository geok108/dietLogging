package com.example.root.dietlogging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;


public class MacrosFragment extends Fragment {

    View view;
    private DiaryViewModel mDiaryViewModel;
    private DiaryDao diaryDao;

    public MacrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_macros, container, false);

        final String date = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());

        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);

        LiveData <List<Diary>> todayDiary = mDiaryViewModel.getTodayEntries("");

        todayDiary.observe(this, new Observer<List<Diary>>() {


            @Override
            public void onChanged(@Nullable final List<Diary> diaries) {

               if (diaries.isEmpty()){

                   Log.d("todays diarys empty:" , date);
               }else{
                   float totalProtein = 0;
                   float totalFat = 0;
                   float totalCarbs = 0;
                   float totalSugars = 0;

                   for (int i=0; i<diaries.size(); i++) {
                       float protein = 0;
                       float fat = 0;
                       float carbs = 0;
                       float sugars = 0;

                       protein = getMacroGrams(diaries.get(i).getProtein(), diaries.get(i).getGrams());
                       fat = getMacroGrams(diaries.get(i).getFat(), diaries.get(i).getGrams());
                       carbs = getMacroGrams(diaries.get(i).getCarbohydrates(), diaries.get(i).getGrams());
                       sugars = getMacroGrams(diaries.get(i).getTotalSugars(), diaries.get(i).getGrams());


                       totalProtein += protein;
                       totalFat += fat;
                       totalCarbs += carbs;
                       totalSugars += sugars;

                   }

                   Log.d("tot pro:" , String.valueOf(totalProtein));
                   Log.d("tot fat:" , String.valueOf(totalFat));
                   Log.d("tot carbs:" , String.valueOf(totalCarbs));
                   Log.d("tot sug:" , String.valueOf(totalSugars));

               }
            }


            });


        return view;
    }

    public float getMacroGrams(float macro, float grams){
        float macroGrams;

        macroGrams = (macro * grams) / 100;

        return macroGrams;
    }

}
