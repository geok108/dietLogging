package com.example.root.dietlogging.Fragments;


import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.dietlogging.Activities.UpdateActivity;
import com.example.root.dietlogging.Entities.Diary;
import com.example.root.dietlogging.MyViewModelFactory;
import com.example.root.dietlogging.R;
import com.example.root.dietlogging.Entities.User;
import com.example.root.dietlogging.ViewModels.DiaryViewModel;
import com.example.root.dietlogging.ViewModels.UserViewModel;
import com.opencsv.CSVWriter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.root.dietlogging.R.*;


public class FoodDiaryFragment extends Fragment implements View.OnClickListener {
    View view;
    private DiaryViewModel mDiaryViewModel;
    private TextView todayDate;
    private ImageButton backArrow;
    private ImageButton forArrow;
    private TableLayout tableLayout;
    private RecyclerView recyclerView;
    private LiveData<List<Diary>> diaryList;
    private LiveData<List<Diary>> diaryInitList;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 51;
    public static final int UPDATE_FOOD_REQUEST_CODE = 3;
    public static final int DELETE_FOOD_REQUEST_CODE = 1;
    public static final int NOT_DELETE_FOOD_REQUEST_CODE = 0;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Calendar c = Calendar.getInstance();
    private String date;
    private String chosenDate;


    public static FoodDiaryFragment newInstance(String date) {
        // Required empty public constructor
        Bundle bundle = new Bundle();

        bundle.putString("date", date);
        Log.w("date from activity: ", date);

        FoodDiaryFragment fragment = new FoodDiaryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void readBundle(Bundle bundle) {
        if (bundle != null) {
            date = bundle.getString("date");

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cDate = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());

        Fragment fragmentFor = FoodDiaryFragment.newInstance(cDate);
        readBundle(fragmentFor.getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(layout.fragment_food_diary, container, false);
        //final TextView time = view.findViewById(R.id.meal_time);
        //final TextView foodName = view.findViewById(R.id.food_name);
        //final TextView foodGrams = view.findViewById(R.id.food_grams);


        Button btn_export_csv = view.findViewById(id.export_csv);
        btn_export_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                51);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }

                } else {

                    exportDiary();

                }


            }
        });


        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Log.w(" perm request code: ", String.valueOf(requestCode));
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    exportDiary();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    public void exportDiary() {
        mDiaryViewModel.getAllDiaries().observe(getActivity(), new Observer<List<Diary>>() {
            @Override
            public void onChanged(@Nullable final List<Diary> diary) {

                FileWriter fileWriter = null;
                try {

                    UserViewModel mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
                    User u = mUserViewModel.getUser().getValue().get(0);

                    fileWriter = new FileWriter(Environment.getExternalStorageDirectory() + "/diet_log_participant_" + u.getParticipant_number() + ".csv");

                    CSVWriter csvWriter = new CSVWriter(fileWriter,
                            CSVWriter.DEFAULT_SEPARATOR,
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END);

                    //CSV header
                    String[] data = {"DATE/TIME", "FOOD NAME", "GRAMS", "MEAL", "HUNGER"};
                    csvWriter.writeNext(data);

                    for (int i = 0; i < diary.size(); i++) {

                        String date = diary.get(i).getDate();
                        String time = diary.get(i).getTime();
                        String foodName = diary.get(i).getFoodName();
                        String grams = String.valueOf(diary.get(i).getGrams() + "g");
                        String meal = diary.get(i).getMeal();
                        String appetite = String.valueOf(diary.get(i).getHunger()) + "%";

                        //replace commas in food names with dashes since since CSV is a comma separated values format
                        foodName = foodName.replaceAll(",", " -");

                        String[] rec = {date + ' ' + time + ',' + foodName + ',' + grams + ',' + meal + ',' + appetite};
                        csvWriter.writeNext(rec);

                    }

                    csvWriter.flush();
                    csvWriter.close();
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            R.string.csv_exported,
                            Toast.LENGTH_LONG).show();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        backArrow = view.findViewById(id.date_arrow_back);
        forArrow = view.findViewById(id.date_arrow_for);


        Format formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String today = formatter.format(new Date());
        Log.d("today", today);
        todayDate = view.findViewById(id.date);

        Date currentDate = new Date();
        dateFormat.format(currentDate);
        // convert date to calendar
        c.setTime(currentDate);
        Log.w("cal: ", dateFormat.format(c.getTime()));
        todayDate.setText(formatter.format(c.getTime()));

        backArrow.setOnClickListener(this);
        forArrow.setOnClickListener(this);

        tableLayout = view.findViewById(id.table_food);

        tableLayout.setGravity(Gravity.CENTER);

        TableRow row = new TableRow(getActivity());

        final TextView timeTitle = new TextView(getActivity());
        final TextView foodNameTitle = new TextView(getActivity());
        final TextView foodGramsTitle = new TextView(getActivity());

        timeTitle.setText(string.time);
        foodNameTitle.setText(string.food);
        foodGramsTitle.setText(string.grams);

        timeTitle.setTextSize(18);
        foodNameTitle.setTextSize(18);
        foodGramsTitle.setTextSize(18);

        tableLayout.addView(row);

        row.addView(timeTitle);
        row.addView(foodNameTitle);
        row.addView(foodGramsTitle);

        listDiary();

    }


    public void listDiary() {

        /*recyclerView = view.findViewById(R.id.recyclerview);

        final DiaryListAdapter adapter = new DiaryListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDiaryViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), date)).get(DiaryViewModel.class);

        diaryList = mDiaryViewModel.getDateEntries(date);
        diaryList.observe(this, new Observer<List<Diary>>() {

            @Override
            public void onChanged(@Nullable final List<Diary> diary) {
                // Update the cached copy of the words in the adapter.
                Log.w("date on listdiary:", date);

                adapter.setDiary(diary);
                adapter.notifyDataSetChanged();


                for (int i = 0; i < diary.size(); i++) {

                    Log.w("record in onchanged: ", diary.get(i).getFoodName());

                }

            }

        });
*/

        mDiaryViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), date)).get(DiaryViewModel.class);
        diaryInitList = mDiaryViewModel.getDiaryByDate(date);
        diaryInitList.observe(this, new Observer<List<Diary>>() {


            @Override
            public void onChanged(@Nullable final List<Diary> diaries) {

                tableLayout.removeAllViews();
                if (!diaries.isEmpty()) {
                    TableRow titleRow = new TableRow(getActivity());
                    TextView timeTitle = new TextView(getActivity());
                    TextView foodNameTitle = new TextView(getActivity());
                    TextView foodGramsTitle = new TextView(getActivity());
                    timeTitle.setText("TIME");
                    timeTitle.setTypeface(null, Typeface.BOLD);
                    foodNameTitle.setText("FOOD");
                    foodNameTitle.setTypeface(null, Typeface.BOLD);
                    foodGramsTitle.setText("GRAMS");
                    foodGramsTitle.setTypeface(null, Typeface.BOLD);
                    tableLayout.addView(titleRow);
                    titleRow.addView(timeTitle);
                    titleRow.addView(foodNameTitle);
                    titleRow.addView(foodGramsTitle);
                    timeTitle.setTextSize(18);
                    timeTitle.setTextColor(getResources().getColor(color.colorBlack));
                    foodNameTitle.setTextSize(18);
                    foodNameTitle.setTextColor(getResources().getColor(color.colorBlack));
                    foodGramsTitle.setTextSize(18);
                    foodGramsTitle.setTextColor(getResources().getColor(color.colorBlack));
                    tableLayout.setBackgroundColor(getResources().getColor(color.colorGrey));


                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT
                    );
                    params.setMargins(1, 1, 1, 1);
                    titleRow.setLayoutParams(params);

                    timeTitle.setBackgroundColor(getResources().getColor(color.colorWhite));
                    foodNameTitle.setBackgroundColor(getResources().getColor(color.colorWhite));
                    foodGramsTitle.setBackgroundColor(getResources().getColor(color.colorWhite));

                    timeTitle.setLayoutParams(params);
                    foodNameTitle.setLayoutParams(params);
                    foodGramsTitle.setLayoutParams(params);


                    for (int i = 0; i < diaries.size(); i++) {
                        TableRow row = new TableRow(getActivity());
                        ViewGroup parent = (ViewGroup) row.getParent();
                        if (parent != null) {
                            parent.removeAllViews();
                        }

                        final TextView time = new TextView(getActivity());
                        TextView foodName = new TextView(getActivity());
                        TextView foodGrams = new TextView(getActivity());

                        time.setTextSize(18);
                        foodName.setTextSize(18);
                        foodGrams.setTextSize(18);

                        String foodTime = diaries.get(i).getTime();
                        String foodTimeN = foodTime.substring(0, 2) + ':' + foodTime.substring(3);

                        time.setText(foodTimeN);

                        String foodNameMinLen = diaries.get(i).getFoodName();
                        foodNameMinLen = foodNameMinLen.substring(0, Math.min(foodNameMinLen.length(), 25));
                        if (foodNameMinLen.length() == 25) {
                            foodNameMinLen = foodNameMinLen.concat("...");
                        }

                        foodName.setText(foodNameMinLen);

                        String grams = Float.toString(diaries.get(i).getGrams());
                        foodGrams.setText(grams + "");

                        row.setLayoutParams(params);

                        time.setBackgroundColor(getResources().getColor(color.colorWhite));
                        foodName.setBackgroundColor(getResources().getColor(color.colorWhite));
                        foodGrams.setBackgroundColor(getResources().getColor(color.colorWhite));

                        time.setLayoutParams(params);
                        foodName.setLayoutParams(params);
                        foodGrams.setLayoutParams(params);

                        tableLayout.addView(row);

                        row.addView(time);
                        row.addView(foodName);
                        row.addView(foodGrams);

                        final int finalI = i;
                        row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent updateIntent = new Intent(getActivity(), UpdateActivity.class);
                                updateIntent.putExtra("diaryId", diaries.get(finalI).getId());
                                updateIntent.putExtra("foodCode", diaries.get(finalI).getFoodId());
                                updateIntent.putExtra("foodName", diaries.get(finalI).getFoodName());

                                updateIntent.putExtra("protein", diaries.get(finalI).getProtein());
                                updateIntent.putExtra("carbohydrate", diaries.get(finalI).getCarbohydrates());
                                updateIntent.putExtra("fat", diaries.get(finalI).getFat());
                                updateIntent.putExtra("totalSugars", diaries.get(finalI).getTotalSugars());
                                updateIntent.putExtra("energy", diaries.get(finalI).getEnergy());

                                updateIntent.putExtra("date", diaries.get(finalI).getDate());
                                updateIntent.putExtra("time", diaries.get(finalI).getTime());
                                updateIntent.putExtra("meal", diaries.get(finalI).getMeal());
                                updateIntent.putExtra("hunger", diaries.get(finalI).getHunger());
                                updateIntent.putExtra("grams", diaries.get(finalI).getGrams());

                                startActivityForResult(updateIntent, UPDATE_FOOD_REQUEST_CODE);
                            }
                        });

                    }
                }
            }


        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.date_arrow_back:
                c.add(Calendar.DATE, -1);
                Format formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                todayDate.setText(formatter.format(c.getTime()));
                chosenDate = dateFormat.format(c.getTime());

                Fragment fragmentBack = FoodDiaryFragment.newInstance(chosenDate);
                readBundle(fragmentBack.getArguments());

                listDiary();

                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.detach(FoodDiaryFragment.this).attach(FoodDiaryFragment.this).commit();
                break;
            case id.date_arrow_for:
                c.add(Calendar.DATE, 1);
                Format formatterm = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                todayDate.setText(formatterm.format(c.getTime()));
                chosenDate = dateFormat.format(c.getTime());

                Fragment fragmentFor = FoodDiaryFragment.newInstance(chosenDate);
                readBundle(fragmentFor.getArguments());

                listDiary();
                //FragmentTransaction fnt = getFragmentManager().beginTransaction();
                //fnt.detach(FoodDiaryFragment.this).attach(FoodDiaryFragment.this).commit();

                break;
        }
    }


    static class ViewLifecycleOwner implements LifecycleOwner {
        private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

        @Override
        public LifecycleRegistry getLifecycle() {
            return lifecycleRegistry;
        }
    }

    @Nullable
    private ViewLifecycleOwner viewLifecycleOwner;

    /**
     * @return the Lifecycle owner of the current view hierarchy,
     * or null if there is no current view hierarchy.
     */
    @Nullable
    public LifecycleOwner getViewLifecycleOwner() {
        return viewLifecycleOwner;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewLifecycleOwner = new ViewLifecycleOwner();
        viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_START);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        }
    }

    @Override
    public void onPause() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            viewLifecycleOwner = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_FOOD_REQUEST_CODE && resultCode == RESULT_OK
                && data.getExtras().getInt("DELETE CODE") == NOT_DELETE_FOOD_REQUEST_CODE) {

            int diaryId = data.getExtras().getInt("diaryId");
            String foodCode = data.getStringExtra("foodCode");
            String foodName = data.getStringExtra("foodName");
            float protein = data.getExtras().getFloat("protein");
            float fat = data.getExtras().getFloat("fat");
            float carbohydrates = data.getExtras().getFloat("carbohydrate");
            float energy = data.getExtras().getFloat("energy");
            float totalSugars = data.getExtras().getFloat("totalSugars");

            String date = data.getStringExtra("date_new");
            String time = data.getStringExtra("time");
            String meal = data.getStringExtra("meal");
            float grams = data.getExtras().getFloat("grams");
            Integer hunger = data.getExtras().getInt("hunger");

            Log.w("date failed: ", date);

            Diary diary = new Diary(diaryId, foodCode, foodName, protein, fat, carbohydrates, energy, totalSugars, date, time, meal, grams, hunger);

            mDiaryViewModel.update(diary);

        } else if (requestCode == UPDATE_FOOD_REQUEST_CODE && resultCode == RESULT_OK
                && data.getExtras().getInt("DELETE CODE") == DELETE_FOOD_REQUEST_CODE) {

            int diaryId = data.getExtras().getInt("diaryId");
            String foodCode = data.getStringExtra("foodCode");
            String foodName = data.getStringExtra("foodName");
            float protein = data.getExtras().getFloat("protein");
            float fat = data.getExtras().getFloat("fat");
            float carbohydrates = data.getExtras().getFloat("carbohydrate");
            float energy = data.getExtras().getFloat("energy");
            float totalSugars = data.getExtras().getFloat("totalSugars");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String meal = data.getStringExtra("meal");
            float grams = data.getExtras().getFloat("grams");
            Integer hunger = data.getExtras().getInt("hunger");

            Diary diary = new Diary(diaryId, foodCode, foodName, protein, fat, carbohydrates, energy, totalSugars, date, time, meal, grams, hunger);

            mDiaryViewModel.delete(diary);

        } else {

            Toast.makeText(getContext(),
                    string.not_action_performed,
                    Toast.LENGTH_LONG).show();
        }

    }
}
