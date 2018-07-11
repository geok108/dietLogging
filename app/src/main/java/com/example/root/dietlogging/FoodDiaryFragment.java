package com.example.root.dietlogging;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.root.dietlogging.R.*;


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
        view = inflater.inflate(layout.fragment_food_diary, container, false);
        //final TextView time = view.findViewById(R.id.meal_time);
        //final TextView foodName = view.findViewById(R.id.food_name);
        //final TextView foodGrams = view.findViewById(R.id.food_grams);
        final TableLayout tableLayout = view.findViewById(id.table_food);

        tableLayout.setGravity(Gravity.CENTER);

        TableRow row = new TableRow(getActivity());

        final TextView timeTitle = new TextView(getActivity());
        final TextView foodNameTitle = new TextView(getActivity());
        final TextView foodGramsTitle = new TextView(getActivity());

        timeTitle.setText(string.time);
        foodNameTitle.setText(string.food);
        foodGramsTitle.setText(string.grams);

        timeTitle.setTextSize(18);
        foodNameTitle.setTextSize(18);
        foodGramsTitle.setTextSize(18);

        tableLayout.addView(row);

        row.addView(timeTitle);
        row.addView(foodNameTitle);
        row.addView(foodGramsTitle);


        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);



        mDiaryViewModel.getAllEntries().observe(this, new Observer<List<Diary>>(){


            @Override
            public void onChanged(@Nullable List<Diary> diaries) {

               if (!diaries.isEmpty()) {

                   for (int i=0; i<diaries.size(); i++) {
                       TableRow row = new TableRow(getActivity());
                       View lineSep = new View(getActivity());

                       ViewGroup parent = (ViewGroup) row.getParent();
                       if (parent != null) {
                           parent.removeAllViews();
                       }


                       TextView time = new TextView(getActivity());
                       TextView foodName = new TextView(getActivity());
                       TextView foodGrams = new TextView(getActivity());

                       time.setTextSize(15);
                       foodName.setTextSize(15);
                       foodGrams.setTextSize(15);

                       //Log.d("diary entries:", String.valueOf(diaries.get(0).getFoodName()));

                       String originalDate = diaries.get(i).getDateTime();
                       Date date = null;
                       try {
                           date = new SimpleDateFormat("dd.MM.yyyy.HH.mm").parse(originalDate);
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }
                       String foodTime = new SimpleDateFormat("HH:mm").format(date);

                       time.setText(foodTime);
                       foodName.setText(diaries.get(i).getFoodName());

                       String grams = Float.toString(diaries.get(i).getGrams());
                       foodGrams.setText(grams + " g");

                       tableLayout.addView(row);

                       row.addView(time);
                       row.addView(foodName);
                       row.addView(foodGrams);

                   }
               }
            }
        });

        return view;
    }
}
