package com.example.root.dietlogging.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.root.dietlogging.MyBroadcastReceiver;
import com.example.root.dietlogging.ViewModels.DiaryViewModel;
import com.example.root.dietlogging.Fragments.FoodDiaryFragment;
import com.example.root.dietlogging.Fragments.MacrosFragment;
import com.example.root.dietlogging.Adapters.PagerAdapter;
import com.example.root.dietlogging.R;
import com.example.root.dietlogging.Entities.User;
import com.example.root.dietlogging.Adapters.UserListAdapter;
import com.example.root.dietlogging.ViewModels.UserViewModel;

import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    //used for register alarm manager
    PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    BroadcastReceiver mReceiver;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final int NEW_USER_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_USER_ACTIVITY_REQUEST_CODE = 2;
    private static final String CHANNEL_ID = "ch";

    private UserViewModel mUserViewModel;

    private DiaryViewModel mDiaryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final UserListAdapter adapter = new UserListAdapter(this);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> user) {
                // Update the cached copy of the words in the adapter.
                adapter.setUser(user);

                if (user.isEmpty()) {

                    Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                    startActivityForResult(intent, NEW_USER_ACTIVITY_REQUEST_CODE);
                } else {


                    /* Retrieve a PendingIntent that will perform a broadcast */
                    Intent alarmIntent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                    alarmIntent.putExtra("notificationValue", user.get(0).getNotification_frequency());
                    Log.d("gonna send to broad >", String.valueOf(user.get(0).getNotification_frequency()));

                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, 14);

                    switch (user.get(0).getNotification_frequency()) {

                        case 0:
                            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                                    AlarmManager.INTERVAL_DAY, pendingIntent);
                            Log.d("notif > ", "> > 0");

                            break;
                        case 1:
                            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 3,
                                    AlarmManager.INTERVAL_DAY * 3, pendingIntent);
                            Log.d("notif > ", "> > 1");

                            break;
                        case 2:
                            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 7,
                                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                            Log.d("notif > ", "> > 2");

                            break;
                        case 3:
                            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 30,
                                    AlarmManager.INTERVAL_DAY * 30, pendingIntent);
                            Log.d("notif > ", "> > 3");

                            break;


                    }


                }


            }
        });


        tabLayout = findViewById(R.id.tabs);

        appBarLayout = findViewById(R.id.appbar);

        viewPager = findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.AddFragment(new MacrosFragment(), "MACROS");
        pagerAdapter.AddFragment(new FoodDiaryFragment(), "FOOD DIARY");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab_settings = (FloatingActionButton) findViewById(R.id.fab_settings);
        fab_settings.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivityForResult(intent, UPDATE_USER_ACTIVITY_REQUEST_CODE);
            }
        });


        createNotificationChannel();


    }

    /**
     * Create the NotificationChannel, but only on API 26+ because
     * the NotificationChannel class is new and not in the support library
     */
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "chann";
            String description = "not channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = new User(0, data.getExtras().getInt("participantNumber"),
                    data.getStringExtra("fullName"),
                    data.getExtras().getInt("dietChoice"), 0);

            mUserViewModel.insert(user);
        } else if (requestCode == UPDATE_USER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Log.d("got in update", String.valueOf(data.getExtras().getInt("participantNumber")));
            User user = new User(0, data.getExtras().getInt("participantNumber"),
                    data.getStringExtra("fullName"),
                    data.getExtras().getInt("dietChoice"), data.getExtras().getInt("notificationValue"));

            mUserViewModel.update(user);


        } else {

            /** Toast.makeText(
             getApplicationContext(),
             R.string.empty_not_saved,
             Toast.LENGTH_LONG).show();*/
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


}
