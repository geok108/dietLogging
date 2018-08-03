package com.example.root.dietlogging;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView foodListView;
    private FoodListAdapter foodAdapter;
    private DiaryViewModel mDiaryViewModel;


    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.foodListView = (ListView) findViewById(R.id.foodListView);

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
                databaseAccess.close();

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

    public void showResults(ArrayList results){

        //FoodListAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
        //this.foodListView.setAdapter(adapter);

        foodAdapter = new FoodListAdapter(this, results);
        foodListView.setAdapter(foodAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String foodCode = data.getStringExtra("foodCode");
            String foodName = data.getStringExtra("foodName");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            float protein;
            if(data.getStringExtra("protein").equals("Tr") || data.getStringExtra("protein").equals("N")){
                protein = 0;
            }else{
                protein = Float.parseFloat(data.getStringExtra("protein"));

            }

            float fat;
            if(data.getStringExtra("fat").equals("Tr") || data.getStringExtra("fat").equals("N")){
                fat = 0;
            }else{
                fat = Float.parseFloat(data.getStringExtra("fat"));

            }

            float carbohydrates;
            if(data.getStringExtra("carbohydrate").equals("Tr") || data.getStringExtra("carbohydrate").equals("N")){
                carbohydrates = 0;
            }else{
                carbohydrates = Float.parseFloat(data.getStringExtra("carbohydrate"));

            }

            float energy;
            if(data.getStringExtra("energy").equals("Tr") || data.getStringExtra("energy").equals("N")){
                energy = 0;
            }else{
                energy = Float.parseFloat(data.getStringExtra("energy"));

            }

            float totalSugars;
            if(data.getStringExtra("totalSugars").equals("Tr") || data.getStringExtra("totalSugars").equals("N")){
                totalSugars = 0;
            }else{
                totalSugars = Float.parseFloat(data.getStringExtra("totalSugars"));

            }

            String meal = data.getStringExtra("meal");
            float grams = Float.parseFloat(data.getStringExtra("grams"));
            int hunger = Integer.parseInt(data.getStringExtra("hunger"));

            Integer id = null;
            Diary diary = new Diary(id, foodCode, foodName, protein, fat, carbohydrates, energy, totalSugars, date, time, meal, grams, hunger);

            Log.d("received food data", diary.getDate());
            Log.d("received food data", diary.getFoodId());
            Log.d("received food data", diary.getFoodName());
            Log.d("received food data", diary.getMeal());
            Log.d("received food data", String.valueOf(diary.getGrams()));
            Log.d("received food data", String.valueOf(diary.getHunger()));


            mDiaryViewModel.insert(diary);


            Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

}
