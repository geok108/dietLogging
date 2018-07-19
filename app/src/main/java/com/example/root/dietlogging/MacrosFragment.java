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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MacrosFragment extends Fragment {

    View view;
    private DiaryViewModel mDiaryViewModel;
    private DiaryDao diaryDao;
    private PieChart macrosChart;

    public MacrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_macros, container, false);

        macrosChart = view.findViewById(R.id.macros_chart);

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

                   ArrayList<Integer> colors = new ArrayList<Integer>();;
                   colors.add(getResources().getColor(R.color.colorPrimary));
                   colors.add(getResources().getColor(R.color.colorRed));
                   colors.add(getResources().getColor(R.color.colorWhite));

                   Log.d("tot pro:" , String.valueOf(totalProtein));
                   Log.d("tot fat:" , String.valueOf(totalFat));
                   Log.d("tot carbs:" , String.valueOf(totalCarbs));
                   Log.d("tot sug:" , String.valueOf(totalSugars));

                   List<PieEntry> entries = new ArrayList<PieEntry>();
                   entries.add(new PieEntry(totalProtein, "protein"));
                   entries.add(new PieEntry(totalFat, "fat"));
                   entries.add(new PieEntry(totalCarbs, "carbs"));
                   PieDataSet dataset = new PieDataSet(entries, "data");
                   dataset.setColors(colors);

                   PieData pieData = new PieData(dataset);
                   macrosChart.setData(pieData);
                   macrosChart.invalidate();

               }
            }


            });



        final TableLayout tableLayout = view.findViewById(R.id.table_macros);

        tableLayout.setGravity(Gravity.CENTER);

        TableRow row = new TableRow(getActivity());

        final TextView timeTitle = new TextView(getActivity());
        final TextView foodNameTitle = new TextView(getActivity());
        final TextView foodGramsTitle = new TextView(getActivity());

        timeTitle.setText(R.string.time);
        foodNameTitle.setText(R.string.food);
        foodGramsTitle.setText(R.string.grams);

        timeTitle.setTextSize(18);
        foodNameTitle.setTextSize(18);
        foodGramsTitle.setTextSize(18);

        tableLayout.addView(row);

        row.addView(timeTitle);
        row.addView(foodNameTitle);
        row.addView(foodGramsTitle);



        return view;
    }

    public float getMacroGrams(float macro, float grams){
        float macroGrams;

        macroGrams = (macro * grams) / 100;

        return macroGrams;
    }

}
