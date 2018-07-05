package com.example.root.dietlogging;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView foodListView;


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
                //Log.d("QUERY: ", q);


                databaseAccess.open();
                List<String> results = databaseAccess.getFoodResults(q);
                databaseAccess.close();

               Log.d("foods: ", String.valueOf(results));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }



        });

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }

    }



}
