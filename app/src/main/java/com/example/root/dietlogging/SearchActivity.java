package com.example.root.dietlogging;

import android.app.SearchManager;
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

    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.foodListView = (ListView) findViewById(R.id.foodListView);


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


        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
