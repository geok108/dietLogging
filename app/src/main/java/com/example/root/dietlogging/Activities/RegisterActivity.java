package com.example.root.dietlogging.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.root.dietlogging.R;
import com.example.root.dietlogging.ViewModels.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private EditText mEditParticipantNumber;
    private EditText mEditFullName;
    private RadioGroup mEditDietChoiceGroup;
    private RadioButton mEditDietChoiceButton;
    private UserViewModel mUserViewModel;


    public static final int NEW_USER_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mEditParticipantNumber = findViewById(R.id.participant_number);
        mEditFullName = findViewById(R.id.full_name);
        mEditDietChoiceGroup = findViewById(R.id.diet_choice);

        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditParticipantNumber.getText())) {
                    //setResult(RESULT_CANCELED, replyIntent);
                    mEditParticipantNumber.setError("Please enter participant number");
                } else {

                    int selectedId = mEditDietChoiceGroup.getCheckedRadioButtonId();
                    mEditDietChoiceButton = findViewById(selectedId);
                    int participantNumber = Integer.parseInt(mEditParticipantNumber.getText().toString());
                    String fullName = mEditFullName.getText().toString();
                    String dietChoice = mEditDietChoiceButton.getText().toString();
                    Log.d("diet choice", dietChoice);

                    int dietChoiceValue = 0;
                   switch (dietChoice){

                        case "Control": dietChoiceValue = 0;
                            break;

                        case "LOW SUG": dietChoiceValue = 1;
                            break;

                        case "LOW CHO": dietChoiceValue = 2;
                            break;
                    }

                    replyIntent.putExtra("participantNumber", participantNumber);
                    replyIntent.putExtra("fullName", fullName);
                    replyIntent.putExtra("dietChoice", dietChoiceValue);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }

            }
        });

    }



}
