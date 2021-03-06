package com.example.root.dietlogging.Activities;

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
import com.example.root.dietlogging.R;

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

        final Button button_update = findViewById(R.id.button_update_food);
        final Button button_delete = findViewById(R.id.button_delete);

        final Intent receivedIntent = getIntent();


        dateTime.setText(receivedIntent.getStringExtra("date") + " " + (receivedIntent.getStringExtra("time")));
        chosenFood.setText(receivedIntent.getStringExtra("foodName"));
        chosenFood.setEnabled(false);


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

        grams.setText(String.valueOf(receivedIntent.getExtras().getFloat("grams")));
        hunger.setSeekPinByValue((receivedIntent.getExtras().getInt("hunger")));


        button_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(grams.getText())) {
                    //setResult(RESULT_CANCELED, replyIntent);
                    grams.setError("Please enter amount of grams");
                } else {


                    int diaryId = receivedIntent.getExtras().getInt("diaryId");
                    String food_name = receivedIntent.getStringExtra("foodName");
                    float protein = receivedIntent.getExtras().getFloat("protein");
                    float fat = receivedIntent.getExtras().getFloat("fat");
                    float carbs = receivedIntent.getExtras().getFloat("carbohydrate");
                    float tot_sugars = receivedIntent.getExtras().getFloat("totalSugars");
                    float energy = receivedIntent.getExtras().getFloat("energy");
                    String time_new = dateTime.getText().toString().substring(11);
                    String date_new = dateTime.getText().toString().substring(0, 10);
                    String food_code = receivedIntent.getStringExtra("foodCode");
                    String food_grams = grams.getText().toString();
                    Log.w("grams_upd: ", food_grams);

                    String meal = mealDropdown.getSelectedItem().toString();
                    Integer hunger_now = Integer.valueOf(hunger.getRightPinValue());

                    replyIntent.putExtra("diaryId", diaryId);
                    replyIntent.putExtra("foodCode", food_code);
                    replyIntent.putExtra("foodName", food_name);
                    replyIntent.putExtra("protein", protein);
                    replyIntent.putExtra("fat", fat);
                    replyIntent.putExtra("carbohydrate", carbs);
                    replyIntent.putExtra("totalSugars", tot_sugars);
                    replyIntent.putExtra("energy", energy);
                    replyIntent.putExtra("date_new", date_new);
                    replyIntent.putExtra("time", time_new);
                    replyIntent.putExtra("grams", Float.valueOf(food_grams));
                    replyIntent.putExtra("meal", meal);
                    replyIntent.putExtra("hunger", hunger_now);
                    replyIntent.putExtra("DELETE CODE", 0);
                    Log.w("date failed: ", date_new);



                    setResult(RESULT_OK, replyIntent);
                    finish();
                }



            }
        });


        button_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(grams.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {


                    int diaryId = receivedIntent.getExtras().getInt("diaryId");
                    String food_name = receivedIntent.getStringExtra("foodName");
                    String date = receivedIntent.getStringExtra("date");
                    String time = receivedIntent.getStringExtra("time");
                    String food_code = receivedIntent.getStringExtra("foodCode");
                    String food_grams = grams.getText().toString();
                    String meal = mealDropdown.getSelectedItem().toString();
                    Integer hunger_now = Integer.valueOf(hunger.getRightPinValue());

                    replyIntent.putExtra("diaryId", diaryId);
                    replyIntent.putExtra("foodCode", food_code);
                    replyIntent.putExtra("foodName", food_name);
                    replyIntent.putExtra("date", date);
                    replyIntent.putExtra("time", time);
                    replyIntent.putExtra("grams", Float.valueOf(food_grams));
                    replyIntent.putExtra("meal", meal);
                    replyIntent.putExtra("hunger", hunger_now);
                    replyIntent.putExtra("DELETE CODE", 1);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();



            }
        });




    }
}
