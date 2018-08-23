package com.example.root.dietlogging.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.root.dietlogging.R;
import com.example.root.dietlogging.Entities.User;
import com.example.root.dietlogging.Adapters.UserListAdapter;
import com.example.root.dietlogging.Repositories.UserRepository;
import com.example.root.dietlogging.ViewModels.UserViewModel;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private EditText mEditParticipantNumber;
    private EditText mEditFullName;
    private RadioGroup mEditDietChoiceGroup;
    private RadioButton mEditDietChoiceButton;
    private Spinner mNotificationDrop;
    private UserViewModel mUserViewModel;
    private UserRepository userRepository;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditParticipantNumber = findViewById(R.id.participant_number);
        mEditFullName = findViewById(R.id.full_name);
        mEditDietChoiceGroup = findViewById(R.id.diet_choice);
        mNotificationDrop = findViewById(R.id.notification_dropdown);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.notif_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mNotificationDrop.setAdapter(arrAdapter);

        final Button button_update = findViewById(R.id.button_update);

        final UserListAdapter adapter = new UserListAdapter(this);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> user) {
                // Update the cached copy of the words in the adapter.
                adapter.setUser(user);
                //Log.d("user:", String.valueOf(user.get(0).getFull_name()));
                mEditParticipantNumber.setText(Integer.toString(user.get(0).getParticipant_number()));
                mEditFullName.setText(user.get(0).getFull_name());

                Integer dietChoice = null;
                dietChoice = user.get(0).getDiet_choice();
                //Log.d("diet choice", String.valueOf(dietChoice));
                switch (dietChoice) {

                    case 0:
                        mEditDietChoiceButton = findViewById(R.id.diet_choice_1);
                        mEditDietChoiceButton.toggle();
                        break;
                    case 1:
                        mEditDietChoiceButton = findViewById(R.id.diet_choice_2);
                        mEditDietChoiceButton.toggle();
                        break;
                    case 2:
                        mEditDietChoiceButton = findViewById(R.id.diet_choice_3);
                        mEditDietChoiceButton.toggle();
                        break;
                    default:
                        break;

                }


                Integer notifChoice = null;
                notifChoice = user.get(0).getNotification_frequency();

                mNotificationDrop.setSelection(notifChoice);



            }
        });


        button_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditParticipantNumber.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {

                    int selectedId = mEditDietChoiceGroup.getCheckedRadioButtonId();
                    mEditDietChoiceButton = findViewById(selectedId);
                    int participantNumber = Integer.parseInt(mEditParticipantNumber.getText().toString());
                    String fullName = mEditFullName.getText().toString();
                    String dietChoice = mEditDietChoiceButton.getText().toString();
                    String notificationFreq = mNotificationDrop.getSelectedItem().toString();
                    Log.d("part no", String.valueOf(participantNumber));

                    Log.d("diet choice", dietChoice);

                    int dietChoiceValue = 0;
                    switch (dietChoice) {

                        case "Control":
                            dietChoiceValue = 0;
                            break;

                        case "LOW SUG":
                            dietChoiceValue = 1;
                            break;

                        case "LOW CHO":
                            dietChoiceValue = 2;
                            break;
                    }

                    int notificationValue = 0;
                    switch (notificationFreq) {

                        case "Daily":
                            notificationValue = 0;
                            break;

                        case "Every three days":
                            notificationValue = 1;
                            break;

                        case "Weekly":
                            notificationValue = 2;
                            break;
                        case "Monthly":
                            notificationValue = 3;
                            break;
                    }

                    replyIntent.putExtra("participantNumber", participantNumber);
                    replyIntent.putExtra("fullName", fullName);
                    replyIntent.putExtra("dietChoice", dietChoiceValue);
                    replyIntent.putExtra("notificationValue", notificationValue);

                    setResult(RESULT_OK, replyIntent);

                }
                finish();
            }
        });

    }
}
