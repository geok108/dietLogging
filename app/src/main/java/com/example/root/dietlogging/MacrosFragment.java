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

                   for (int i=0; i<diaries.size(); i++) {

                       Log.d("todays diary:" , diaries.get(i).getFoodName());


                   }
               }
            }


            });


        return view;
    }

}
