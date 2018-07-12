package com.example.root.dietlogging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.appyvet.materialrangebar.RangeBar;

public class UpdateActivity extends AppCompatActivity {

    private EditText dateTime;
    private EditText chosenFood;
    private Spinner mealDropdown;
    private EditText grams;
    private RangeBar hunger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dateTime = findViewById(R.id.date_time);
        chosenFood = findViewById(R.id.food);
        mealDropdown = findViewById(R.id.meal_dropdown);
        grams = findViewById(R.id.grams);
        hunger = findViewById(R.id.hunger);

        final Button button_update = findViewById(R.id.button_update);
        final Button button_delete = findViewById(R.id.button_delete);

        Intent receivedIntent = getIntent();
        Log.d("received food : ", receivedIntent.getStringExtra("diaryId"));
        Log.d("received food : ", receivedIntent.getStringExtra("foodName"));
        Log.d("received food : ", receivedIntent.getStringExtra("dateTime"));
        Log.d("received food : ", receivedIntent.getStringExtra("meal"));
        Log.d("received food : ", String.valueOf(receivedIntent.getExtras().getFloat("grams")));

        dateTime.setText(receivedIntent.getStringExtra("dateTime"));
        chosenFood.setText(receivedIntent.getStringExtra("foodName"));



        String mealItem = receivedIntent.getStringExtra("meal");
        int mealNo = 0;

        switch (mealItem){

            case "Breakfast":
                mealNo = 0;
                break;
            case "Lunch":
                mealNo = 1;
                break;
            case "Dinner":
                mealNo = 2;
                break;
            case "Snack":
                mealNo = 3;
                break;
            default:
                break;
        }



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mealDropdown.setAdapter(adapter);
        mealDropdown.setSelection(mealNo);

        grams.setText(String.valueOf(receivedIntent.getExtras().getFloat(   "grams")));
        hunger.setSeekPinByValue((receivedIntent.getExtras().getInt("hunger")));


        button_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });


        button_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });




    }
}
