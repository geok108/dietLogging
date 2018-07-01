package com.example.root.dietlogging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private EditText mEditParticipantNumber;
    private EditText mEditFullName;
    private RadioGroup mEditDietChoiceGroup;
    private RadioButton mEditDietChoiceButton;
    private UserViewModel mUserViewModel;
    private UserRepository userRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mEditParticipantNumber = findViewById(R.id.participant_number);
        mEditFullName = findViewById(R.id.full_name);
        mEditDietChoiceGroup = findViewById(R.id.diet_choice);

        final Button button_update = findViewById(R.id.button_update);


        final UserListAdapter adapter = new UserListAdapter(this);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> user) {
                // Update the cached copy of the words in the adapter.
                adapter.setUser(user);
                Log.d("user:", String.valueOf(user.get(0).getFull_name()));
                mEditParticipantNumber.setText(Integer.toString(user.get(0).getParticipant_number()));
                mEditFullName.setText(user.get(0).getFull_name());

            }
        });

    }
}
