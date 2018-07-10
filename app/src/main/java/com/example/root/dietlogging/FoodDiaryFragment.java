package com.example.root.dietlogging;

import android.arch.lifecycle.Observer;
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
import android.widget.TextView;

import java.util.List;


public class FoodDiaryFragment extends Fragment {
    View view;
    private DiaryViewModel mDiaryViewModel;


    public FoodDiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_diary, container, false);
        final TextView time = view.findViewById(R.id.meal_time);
        final TextView foodName = view.findViewById(R.id.food_name);
        final TextView foodGrams = view.findViewById(R.id.food_grams);

        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);



        mDiaryViewModel.getAllEntries().observe(this, new Observer<List<Diary>>(){


            @Override
            public void onChanged(@Nullable List<Diary> diaries) {
                Log.d("diary entries:" , String.valueOf(diaries.get(0).getFoodName()));
                time.setText(diaries.get(0).getDateTime());
                foodName.setText(diaries.get(0).getFoodName());
                //foodGrams.setText(diaries.get(0).getGrams());

            }
        });

        return view;
    }
}
