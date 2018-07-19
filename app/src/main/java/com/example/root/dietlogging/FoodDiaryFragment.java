package com.example.root.dietlogging;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.root.dietlogging.R.*;


public class FoodDiaryFragment extends Fragment {
    View view;
    private DiaryViewModel mDiaryViewModel;

    public static final int  UPDATE_FOOD_REQUEST_CODE = 3;
    public static final int  DELETE_FOOD_REQUEST_CODE = 1;
    public static final int NOT_DELETE_FOOD_REQUEST_CODE = 0;


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
            public void onChanged(@Nullable final List<Diary> diaries) {
                tableLayout.removeAllViews();
               if (!diaries.isEmpty()) {
                   TableRow titleRow = new TableRow(getActivity());
                   TextView timeTitle = new TextView(getActivity());
                   TextView foodNameTitle = new TextView(getActivity());
                   TextView foodGramsTitle = new TextView(getActivity());
                   timeTitle.setText("TIME");
                   foodNameTitle.setText("FOOD");
                   foodGramsTitle.setText("GRAMS");
                   tableLayout.addView(titleRow);
                   titleRow.addView(timeTitle);
                   titleRow.addView(foodNameTitle);
                   titleRow.addView(foodGramsTitle);
                   timeTitle.setTextSize(18);
                   foodNameTitle.setTextSize(18);
                   foodGramsTitle.setTextSize(18);
                   tableLayout.setBackgroundColor(getResources().getColor(color.colorPrimary));


                   TableRow.LayoutParams params = new TableRow.LayoutParams(
                           TableRow.LayoutParams.WRAP_CONTENT,
                           TableRow.LayoutParams.WRAP_CONTENT
                   );
                   params.setMargins(2, 2, 2, 2);
                   titleRow.setLayoutParams(params);

                   timeTitle.setBackgroundColor(getResources().getColor(color.colorWhite));
                   foodNameTitle.setBackgroundColor(getResources().getColor(color.colorWhite));
                   foodGramsTitle.setBackgroundColor(getResources().getColor(color.colorWhite));

                   timeTitle.setLayoutParams(params);
                   foodNameTitle.setLayoutParams(params);
                   foodGramsTitle.setLayoutParams(params);


                   for (int i=0; i<diaries.size(); i++) {
                       TableRow row = new TableRow(getActivity());
                       ViewGroup parent = (ViewGroup) row.getParent();
                       if (parent != null) {
                           parent.removeAllViews();
                       }


                       final TextView time = new TextView(getActivity());
                       TextView foodName = new TextView(getActivity());
                       TextView foodGrams = new TextView(getActivity());

                       time.setTextSize(18);
                       foodName.setTextSize(18);
                       foodGrams.setTextSize(18);

                       //Log.d("diary entries:", String.valueOf(diaries.get(0).getFoodName()));

                       String foodTime = diaries.get(i).getTime();
                       /**Date date = null;
                       try {
                           date = new SimpleDateFormat("dd.MM.yyyy.HH.mm").parse(originalDate);
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }
                       String foodTime = new SimpleDateFormat("HH:mm").format(date);*/

                       time.setText(foodTime);

                       String foodNameMinLen = diaries.get(i).getFoodName();
                       foodNameMinLen = foodNameMinLen.substring(0, Math.min(foodNameMinLen.length(), 33));
                       if(foodNameMinLen.length() == 33){
                           foodNameMinLen = foodNameMinLen.concat("...");
                       }

                       foodName.setText(foodNameMinLen);

                       String grams = Float.toString(diaries.get(i).getGrams());
                       foodGrams.setText(grams + " g");


                       row.setLayoutParams(params);

                       time.setBackgroundColor(getResources().getColor(color.colorWhite));
                       foodName.setBackgroundColor(getResources().getColor(color.colorWhite));
                       foodGrams.setBackgroundColor(getResources().getColor(color.colorWhite));

                       time.setLayoutParams(params);
                       foodName.setLayoutParams(params);
                       foodGrams.setLayoutParams(params);


                       tableLayout.addView(row);

                       row.addView(time);
                       row.addView(foodName);
                       row.addView(foodGrams);

                       final int finalI = i;
                       row.setOnClickListener(new View.OnClickListener()
                       {
                           @Override
                           public void onClick(View v)
                           {
                               v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));

                               Intent updateIntent = new Intent(getActivity(), UpdateActivity.class);
                               updateIntent.putExtra("diaryId", diaries.get(finalI).getId());
                               updateIntent.putExtra("foodCode", diaries.get(finalI).getFoodId());
                               updateIntent.putExtra("foodName", diaries.get(finalI).getFoodName());
                               updateIntent.putExtra("date", diaries.get(finalI).getDate());
                               updateIntent.putExtra("time", diaries.get(finalI).getTime());
                               updateIntent.putExtra("meal", diaries.get(finalI).getMeal());
                               updateIntent.putExtra("hunger", diaries.get(finalI).getHunger());
                               updateIntent.putExtra("grams", diaries.get(finalI).getGrams());


                               Log.d("diaryId", String.valueOf(diaries.get(finalI).getId()));
                               Log.d("foodCode", diaries.get(finalI).getFoodId());
                               Log.d("foodName", diaries.get(finalI).getFoodName());
                               Log.d("date", diaries.get(finalI).getDate());
                               Log.d("protein", String.valueOf(diaries.get(finalI).getProtein()));
                               Log.d("fat", String.valueOf(diaries.get(finalI).getFat()));
                               Log.d("carbohydrate", String.valueOf(diaries.get(finalI).getCarbohydrates()));
                               Log.d("energy", String.valueOf(diaries.get(finalI).getEnergy()));
                               Log.d("totalSugars", String.valueOf(diaries.get(finalI).getTotalSugars()));
                               Log.d("time", diaries.get(finalI).getTime());
                               Log.d("meal", diaries.get(finalI).getMeal());
                               Log.d("hunger", String.valueOf(diaries.get(finalI).getHunger()));
                               Log.d("grams", String.valueOf(diaries.get(finalI).getGrams()));

                               startActivityForResult(updateIntent, UPDATE_FOOD_REQUEST_CODE);
                           }
                       });

                   }
               }
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == UPDATE_FOOD_REQUEST_CODE && resultCode == RESULT_OK
                 && data.getExtras().getInt("DELETE CODE") == NOT_DELETE_FOOD_REQUEST_CODE) {

            int diaryId = data.getExtras().getInt("diaryId");
            String foodCode = data.getStringExtra("foodCode");
            String foodName = data.getStringExtra("foodName");
            float protein= data.getExtras().getFloat("protein");
            float fat = data.getExtras().getFloat("fat");
            float carbohydrates = data.getExtras().getFloat("carbohydrates");
            float energy = data.getExtras().getFloat("energy");
            float totalSugars = data.getExtras().getFloat("totalSugars");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String meal = data.getStringExtra("meal");
            float grams = data.getExtras().getFloat("grams");
            Integer hunger = data.getExtras().getInt("hunger");

            Diary diary = new Diary(diaryId, foodCode, foodName, protein, fat, carbohydrates, energy, totalSugars , date, time, meal, grams, hunger);

            mDiaryViewModel.update(diary);

        } else if (requestCode == UPDATE_FOOD_REQUEST_CODE && resultCode == RESULT_OK
                && data.getExtras().getInt("DELETE CODE") == DELETE_FOOD_REQUEST_CODE){

            int diaryId = data.getExtras().getInt("diaryId");
            String foodCode = data.getStringExtra("foodCode");
            String foodName = data.getStringExtra("foodName");
            float protein= data.getExtras().getFloat("protein");
            float fat = data.getExtras().getFloat("fat");
            float carbohydrates = data.getExtras().getFloat("carbohydrates");
            float energy = data.getExtras().getFloat("energy");
            float totalSugars = data.getExtras().getFloat("totalSugars");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String meal = data.getStringExtra("meal");
            float grams = data.getExtras().getFloat("grams");
            Integer hunger = data.getExtras().getInt("hunger");

            Diary diary = new Diary(diaryId, foodCode, foodName, protein, fat, carbohydrates, energy, totalSugars , date, time, meal, grams, hunger);

            mDiaryViewModel.delete(diary);

        } else {

             Toast.makeText(getContext(),
                     string.not_action_performed,
                     Toast.LENGTH_LONG).show();         }

    }
}
