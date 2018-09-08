package com.example.root.dietlogging.Activities;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.root.dietlogging.DatabaseAccess;
import com.example.root.dietlogging.Entities.Diary;
import com.example.root.dietlogging.ViewModels.DiaryViewModel;
import com.example.root.dietlogging.Entities.Food;
import com.example.root.dietlogging.Adapters.FoodListAdapter;
import com.example.root.dietlogging.Entities.FreqFood;
import com.example.root.dietlogging.ViewModels.FreqFoodViewModel;
import com.example.root.dietlogging.MyViewModelFactory;
import com.example.root.dietlogging.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private TableLayout freqAddedView;
    private ListView foodListView;
    private FoodListAdapter foodAdapter;
    private DiaryViewModel mDiaryViewModel;
    private FreqFoodViewModel mFreqFoodViewModel;
    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodListView = (ListView) findViewById(R.id.foodListView);

        freqAddedView = findViewById(R.id.freq_added);
        mFreqFoodViewModel = ViewModelProviders.of(this).get(FreqFoodViewModel.class);

        new Thread(new Runnable() {
            public void run() {
                List<FreqFood> freqFoodList = mFreqFoodViewModel.getFreqFoods();

                DatabaseAccess dbAccess = DatabaseAccess.getInstance(getApplicationContext());

                Collections.sort(freqFoodList, Collections.reverseOrder());

                if (freqFoodList.size() > 7) {
                    freqFoodList = freqFoodList.subList(0, 8);
                }
                for (final FreqFood fd : freqFoodList) {

                    dbAccess.open();
                    final ArrayList<Food> results = dbAccess.getFoodByCode(fd.getFoodId());
                    dbAccess.close();


                    final TableRow row = new TableRow(getApplicationContext());
                    ViewGroup parent = (ViewGroup) row.getParent();
                    if (parent != null) {
                        parent.removeAllViews();
                    }

                    final TextView fFoodName = new TextView(getApplicationContext());

                    fFoodName.setTextSize(22);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT
                    );
                    params.setMargins(0, 0, 0, 12);
                    row.setLayoutParams(params);
                    fFoodName.setLayoutParams(params);

                    String foodNameMinLen = results.get(0).getFoodName();
                    foodNameMinLen = foodNameMinLen.substring(0, Math.min(foodNameMinLen.length(), 25));
                    if (foodNameMinLen.length() == 25) {
                        foodNameMinLen = foodNameMinLen.concat("...");
                    }
                    fFoodName.setText(foodNameMinLen);
                    fFoodName.setTextColor(getResources().getColor(R.color.colorBlack));


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            freqAddedView.addView(row);

                            row.addView(fFoodName);
                            row.setGravity(Gravity.CENTER);

                        }
                    });


                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SearchActivity.this, AddActivity.class);

                            intent.putExtra("foodCode", results.get(0).getFoodCode());
                            intent.putExtra("foodName", results.get(0).getFoodName());
                            intent.putExtra("protein", results.get(0).getProtein());
                            intent.putExtra("fat", results.get(0).getFat());
                            intent.putExtra("carbohydrate", results.get(0).getCarbohydrate());
                            intent.putExtra("energy", results.get(0).getEnergy());
                            intent.putExtra("totalSugars", results.get(0).getTotalSugars());
                            intent.putExtra("grams", fd.getGrams());
                            intent.putExtra("from", "freqAdded");

                            startActivityForResult(intent, NEW_FOOD_ACTIVITY_REQUEST_CODE);

                        }
                    });
                }

            }
        }).start();


        mDiaryViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), "")).get(DiaryViewModel.class);


        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = findViewById(R.id.search_view);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                q = searchView.getQuery().toString();

                databaseAccess.open();
                ArrayList<Food> results = databaseAccess.getFoodResults(q);
                databaseAccess.close();

                showResults(results);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String q) {
                q = searchView.getQuery().toString();

                databaseAccess.open();
                ArrayList<Food> results = databaseAccess.getFoodResults(q);
                //databaseAccess.close();

                showResults(results);
                return true;
            }


        });

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, AddActivity.class);

                intent.putExtra("foodCode", foodAdapter.getItem(position).getFoodCode());
                intent.putExtra("foodName", foodAdapter.getItem(position).getFoodName());
                intent.putExtra("protein", foodAdapter.getItem(position).getProtein());
                intent.putExtra("fat", foodAdapter.getItem(position).getFat());
                intent.putExtra("carbohydrate", foodAdapter.getItem(position).getCarbohydrate());
                intent.putExtra("energy", foodAdapter.getItem(position).getEnergy());
                intent.putExtra("totalSugars", foodAdapter.getItem(position).getTotalSugars());
                intent.putExtra("from", "search");

                Log.d("food macros", foodAdapter.getItem(position).getProtein());
                Log.d("food macros", foodAdapter.getItem(position).getFat());
                Log.d("food macros", foodAdapter.getItem(position).getCarbohydrate());
                Log.d("food macros", foodAdapter.getItem(position).getEnergy());


                startActivityForResult(intent, NEW_FOOD_ACTIVITY_REQUEST_CODE);


            }
        });


        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }


    }

    public void showResults(ArrayList results) {

        foodAdapter = new FoodListAdapter(this, results);
        foodListView.setAdapter(foodAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            final String foodCode = data.getStringExtra("foodCode");
            String foodName = data.getStringExtra("foodName");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            float protein;
            if (data.getStringExtra("protein").equals("Tr") || data.getStringExtra("protein").equals("N")) {
                protein = 0;
            } else {
                protein = Float.parseFloat(data.getStringExtra("protein"));

            }

            float fat;
            if (data.getStringExtra("fat").equals("Tr") || data.getStringExtra("fat").equals("N")) {
                fat = 0;
            } else {
                fat = Float.parseFloat(data.getStringExtra("fat"));

            }

            float carbohydrates;
            if (data.getStringExtra("carbohydrate").equals("Tr") || data.getStringExtra("carbohydrate").equals("N")) {
                carbohydrates = 0;
            } else {
                carbohydrates = Float.parseFloat(data.getStringExtra("carbohydrate"));

            }

            float energy;
            if (data.getStringExtra("energy").equals("Tr") || data.getStringExtra("energy").equals("N")) {
                energy = 0;
            } else {
                energy = Float.parseFloat(data.getStringExtra("energy"));

            }

            float totalSugars;
            if (data.getStringExtra("totalSugars").equals("Tr") || data.getStringExtra("totalSugars").equals("N")) {
                totalSugars = 0;
            } else {
                totalSugars = Float.parseFloat(data.getStringExtra("totalSugars"));

            }

            String meal = data.getStringExtra("meal");
            final float grams = Float.parseFloat(data.getStringExtra("grams"));
            int hunger = Integer.parseInt(data.getStringExtra("hunger"));

            Integer id = null;
            Diary diary = new Diary(id, foodCode, foodName, protein, fat, carbohydrates, energy, totalSugars, date, time, meal, grams, hunger);

            new Thread(new Runnable() {
                public void run() {
                    List<FreqFood> fFoodList = mFreqFoodViewModel.getFreqFoods();

                    ArrayList<String> fdList = new ArrayList<String>();
                    for (FreqFood fd : fFoodList) {
                        fdList.add(fd.getFoodId());

                    }

                    if (fdList.contains(foodCode)) {

                        for (FreqFood fd : fFoodList) {

                            if (fd.getFoodId().equals(foodCode)) {
                                //update freqFood entry, add 1 to counter
                                Integer fFoodId = fd.getId();
                                String freqFoodId = fd.getFoodId();
                                Integer count = fd.getCounter() + 1;
                                float freqFoodGrams = fd.getGrams();

                                FreqFood fFood = new FreqFood(fFoodId, freqFoodId, count, freqFoodGrams);
                                mFreqFoodViewModel.update(fFood);
                                break;

                            }


                        }

                    } else {
                        //insert new entry
                        Integer fFoodId = null;
                        Integer count = 1;
                        FreqFood fFood = new FreqFood(fFoodId, foodCode, count, grams);
                        mFreqFoodViewModel.insert(fFood);

                    }


                }
            }).start();


            mDiaryViewModel.insert(diary);


            Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

}
