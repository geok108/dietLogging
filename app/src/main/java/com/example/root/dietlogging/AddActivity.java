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

import java.text.SimpleDateFormat;

public class AddActivity extends AppCompatActivity {

    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;

    private EditText dateTime;
    private EditText chosenFood;
    private Spinner mealDropdown;
    private EditText grams;
    private RangeBar hunger;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateTime = findViewById(R.id.date_time);
        chosenFood = findViewById(R.id.food);
        mealDropdown = findViewById(R.id.meal_dropdown);
        grams = findViewById(R.id.grams);
        hunger = findViewById(R.id.hunger);
        final Button button = findViewById(R.id.button_save);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mealDropdown.setAdapter(adapter);

        final Intent receivedIntent = getIntent();

        if (receivedIntent.getExtras() != null) {
            Log.d("received data:", receivedIntent.getStringExtra("foodName"));

            String food_name = receivedIntent.getStringExtra("foodName");


            final String date = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
            final String time = new SimpleDateFormat("HH.mm").format(new java.util.Date());
            dateTime.setText(date + " " + time);
            chosenFood.setText(receivedIntent.getStringExtra("foodName"));
            chosenFood.setEnabled(false);

            if (receivedIntent.getExtras().getString("from").equals("freqAdded")) {
                String fGrams = String.valueOf(receivedIntent.getExtras().getFloat("grams"));
                grams.setText(fGrams);
            }

            button.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    Intent replyIntent = new Intent();
                    if (TextUtils.isEmpty(grams.getText())) {
                        //setResult(RESULT_CANCELED, replyIntent);
                        grams.setError("Please enter amount of grams");
                    } else {

                        String food_name = receivedIntent.getStringExtra("foodName");

                        String food_code = receivedIntent.getStringExtra("foodCode");

                        String food_grams = grams.getText().toString();
                        String protein = receivedIntent.getStringExtra("protein");
                        String fat = receivedIntent.getStringExtra("fat");
                        String carbohydrate = receivedIntent.getStringExtra("carbohydrate");
                        String energy = receivedIntent.getStringExtra("energy");
                        String totalSugars = receivedIntent.getStringExtra("totalSugars");
                        String meal = mealDropdown.getSelectedItem().toString();
                        String hunger_now = hunger.getRightPinValue();

                        Log.d("food: ", food_name);
                        Log.d("carbohydrate: ", carbohydrate);
                        Log.d("grams:", food_grams);
                        Log.d("meal: ", meal);
                        Log.d("hunger", hunger_now);


                        replyIntent.putExtra("foodCode", food_code);
                        replyIntent.putExtra("foodName", food_name);
                        replyIntent.putExtra("date", date);
                        replyIntent.putExtra("time", time);
                        replyIntent.putExtra("grams", food_grams);
                        replyIntent.putExtra("protein", protein);
                        replyIntent.putExtra("fat", fat);
                        replyIntent.putExtra("carbohydrate", carbohydrate);
                        replyIntent.putExtra("energy", energy);
                        replyIntent.putExtra("totalSugars", totalSugars);
                        replyIntent.putExtra("meal", meal);
                        replyIntent.putExtra("hunger", hunger_now);

                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }

                }
            });

        }

    }


}