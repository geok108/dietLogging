package com.example.root.dietlogging;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MacrosFragment extends Fragment {

    View view;
    private DiaryViewModel mDiaryViewModel;
    private UserViewModel mUserViewModel;
    private DiaryDao diaryDao;
    private PieChart macrosChart;
    private TextView choTarget;
    private TextView fatTarget;
    private TextView proTarget;
    private TextView choCons;
    private TextView fatCons;
    private TextView proCons;
    private TextView choRem;
    private TextView fatRem;
    private TextView proRem;
    private TextView currentDate;
    private String tabPro;
    private String tabFat;
    private String tabCarb;
    private String tabSug;


    public MacrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_macros, container, false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Format formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String today = formatter.format(new Date());

        currentDate = view.findViewById(R.id.curr_date);
        currentDate.setText(today);

        macrosChart = view.findViewById(R.id.macros_chart);

        final String date = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());

        mDiaryViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), date)).get(DiaryViewModel.class);

        LiveData<List<Diary>> todayDiary = mDiaryViewModel.getTodayEntries();

        todayDiary.observe(this, new Observer<List<Diary>>() {


            @Override
            public void onChanged(@Nullable final List<Diary> diaries) {

                if (diaries.isEmpty()) {

                    Log.d("todays diarys empty:", date);

                } else {
                    float totalProtein = 0;
                    float totalFat = 0;
                    float totalCarbs = 0;
                    float totalSugars = 0;

                    for (int i = 0; i < diaries.size(); i++) {
                        float protein;
                        float fat;
                        float carbs;
                        float sugars;

                        protein = getMacroGrams(diaries.get(i).getProtein(), diaries.get(i).getGrams());
                        fat = getMacroGrams(diaries.get(i).getFat(), diaries.get(i).getGrams());
                        carbs = getMacroGrams(diaries.get(i).getCarbohydrates(), diaries.get(i).getGrams());
                        sugars = getMacroGrams(diaries.get(i).getTotalSugars(), diaries.get(i).getGrams());

                        Log.d("pro:", String.valueOf(diaries.get(i).getProtein() + "    " + diaries.get(i).getGrams()));
                        Log.d("fat:", String.valueOf(diaries.get(i).getFat() + "    " + diaries.get(i).getGrams()));
                        Log.d("carbs:", String.valueOf(diaries.get(i).getCarbohydrates() + "    " + diaries.get(i).getGrams()));
                        Log.d("size:", String.valueOf(diaries.get(i).getGrams()));

                        totalProtein += protein;
                        totalFat += fat;
                        totalCarbs += carbs;
                        totalSugars += sugars;

                        Log.w("here", "here");


                    }

                    ArrayList<Integer> colors = new ArrayList<Integer>();
                    ;
                    colors.add(getResources().getColor(R.color.colorPrimary));
                    colors.add(getResources().getColor(R.color.colorRed));
                    colors.add(getResources().getColor(R.color.colorAccent));

                    Log.d("tot pro:", String.valueOf(totalProtein));
                    Log.d("tot fat:", String.valueOf(totalFat));
                    Log.d("tot carbs:", String.valueOf(totalCarbs));
                    Log.d("tot sug:", String.valueOf(totalSugars));

                    float totals = totalProtein + totalFat + totalCarbs;
                    tabPro = String.valueOf((totalProtein * 100) / totals);
                    tabFat = String.valueOf((totalFat * 100) / totals);
                    tabCarb = String.valueOf((totalCarbs * 100) / totals);
                    tabSug = String.valueOf((totalSugars * 100) / totals);

                    List<PieEntry> entries = new ArrayList<PieEntry>();
                    entries.add(new PieEntry(Float.parseFloat(tabPro), "protein"));
                    entries.add(new PieEntry(Float.parseFloat(tabFat), "fat"));
                    entries.add(new PieEntry(Float.parseFloat(tabCarb), "carbs"));
                    PieDataSet dataset = new PieDataSet(entries, "data");
                    dataset.setColors(colors);
                    dataset.setValueTextSize(20);

                    PieData pieData = new PieData(dataset);
                    macrosChart.setHoleRadius(20);
                    macrosChart.setTransparentCircleRadius(25);
                    macrosChart.setData(pieData);
                    macrosChart.invalidate();


                    final TableLayout tableLayout = view.findViewById(R.id.table_macros);

                    choTarget = view.findViewById(R.id.cho_target);
                    choCons = view.findViewById(R.id.cho_cons);
                    choRem = view.findViewById(R.id.cho_rem);

                    fatTarget = view.findViewById(R.id.fat_target);
                    fatCons = view.findViewById(R.id.fat_cons);
                    fatRem = view.findViewById(R.id.fat_rem);

                    proTarget = view.findViewById(R.id.pro_target);
                    proCons = view.findViewById(R.id.pro_cons);
                    proRem = view.findViewById(R.id.pro_rem);

                    mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

                    mUserViewModel.getUser().observe(getActivity(), new Observer<List<User>>() {
                        @Override
                        public void onChanged(@Nullable final List<User> user) {

                            if (!user.isEmpty()) {
                                int dietChoice = user.get(0).getDiet_choice();

                                switch (dietChoice) {
                                    case 0:
                                        choTarget.setText("50[20]");
                                        fatTarget.setText("35");
                                        proTarget.setText("15");

                                        choCons.setText(Math.round(Float.parseFloat(tabCarb)) + "[" + Math.round(Float.parseFloat(tabSug)) + "]");
                                        fatCons.setText(Math.round(Float.parseFloat(tabFat)) + "");
                                        proCons.setText(Math.round(Float.parseFloat(tabPro)) + "");

                                        float cho_rem = 50 - Float.parseFloat(tabCarb);
                                        float sug_rem = 20 - Float.parseFloat(tabSug);
                                        float fat_rem = 35 - Float.parseFloat(tabFat);
                                        float pro_rem = 15 - Float.parseFloat(tabPro);

                                        if (cho_rem > 0 && cho_rem < 5) {
                                            //green
                                            choRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (cho_rem < 0) {
                                            //red
                                            choRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        if (fat_rem > 0 && fat_rem < 5) {
                                            //green
                                            fatRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (fat_rem < 0) {
                                            //red
                                            fatRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        if (pro_rem > 0 && pro_rem < 5) {
                                            //green
                                            proRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (pro_rem < 0) {
                                            //red
                                            proRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        choRem.setText((Math.round(cho_rem)) + "[" + Math.round(sug_rem) + "]");
                                        fatRem.setText(Math.round(fat_rem) + "");
                                        proRem.setText(Math.round(pro_rem) + "");

                                        break;
                                    case 1:
                                        choTarget.setText("50[<5]");
                                        fatTarget.setText("35");
                                        proTarget.setText("15");

                                        choCons.setText(Math.round(Float.parseFloat(tabCarb)) + "[" + Math.round(Float.parseFloat(tabSug)) + "]");
                                        fatCons.setText(Math.round(Float.parseFloat(tabFat)) + "");
                                        proCons.setText(Math.round(Float.parseFloat(tabPro)) + "");

                                        float cho_rem_ls = 50 - Float.parseFloat(tabCarb);
                                        float sug_rem_ls = 5 - Float.parseFloat(tabSug);
                                        float fat_rem_ls = 35 - Float.parseFloat(tabFat);
                                        float pro_rem_ls = 15 - Float.parseFloat(tabPro);

                                        if (cho_rem_ls > 0 && cho_rem_ls < 5) {
                                            //green
                                            choRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (cho_rem_ls < 0) {
                                            //red
                                            choRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        if (fat_rem_ls > 0 && fat_rem_ls < 5) {
                                            //green
                                            fatRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (fat_rem_ls < 0) {
                                            //red
                                            fatRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        if (pro_rem_ls > 0 && pro_rem_ls < 5) {
                                            //green
                                            proRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (pro_rem_ls < 0) {
                                            //red
                                            proRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        choRem.setText((Math.round(cho_rem_ls)) + "[" + Math.round(sug_rem_ls) + "]");
                                        fatRem.setText(Math.round(fat_rem_ls) + "");
                                        proRem.setText(Math.round(pro_rem_ls) + "");


                                        break;
                                    case 2:
                                        choTarget.setText("8[<5]");
                                        fatTarget.setText("77");
                                        proTarget.setText("15");

                                        choCons.setText(Math.round(Float.parseFloat(tabCarb)) + "[" + Math.round(Float.parseFloat(tabSug)) + "]");
                                        fatCons.setText(Math.round(Float.parseFloat(tabFat)) + "");
                                        proCons.setText(Math.round(Float.parseFloat(tabPro)) + "");

                                        float cho_rem_lc = 8 - Float.parseFloat(tabCarb);
                                        float sug_rem_lc = 5 - Float.parseFloat(tabSug);
                                        float fat_rem_lc = 77 - Float.parseFloat(tabFat);
                                        float pro_rem_lc = 15 - Float.parseFloat(tabPro);

                                        if (cho_rem_lc > 0 && cho_rem_lc < 5) {
                                            //green
                                            choRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (cho_rem_lc < 0) {
                                            //red
                                            choRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        if (fat_rem_lc > 0 && fat_rem_lc < 5) {
                                            //green
                                            fatRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (fat_rem_lc < 0) {
                                            //red
                                            fatRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        if (pro_rem_lc > 0 && pro_rem_lc < 5) {
                                            //green
                                            proRem.setBackgroundColor(getResources().getColor(R.color.green));
                                        } else if (pro_rem_lc < 0) {
                                            //red
                                            proRem.setBackgroundColor(getResources().getColor(R.color.lightRed));

                                        }

                                        choRem.setText((Math.round(cho_rem_lc)) + "[" + Math.round(sug_rem_lc) + "]");
                                        fatRem.setText(Math.round(fat_rem_lc) + "");
                                        proRem.setText(Math.round(pro_rem_lc) + "");
                                        break;
                                    default:
                                        break;

                                }
                            }
                        }
                    });


                }
            }


        });
    }

    public float getMacroGrams(float macro, float grams) {
        float macroGrams;

        macroGrams = (macro * grams) / 100;

        return macroGrams;
    }


    static class ViewLifecycleOwner implements LifecycleOwner {
        private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

        @Override
        public LifecycleRegistry getLifecycle() {
            return lifecycleRegistry;
        }
    }

    @Nullable
    private FoodDiaryFragment.ViewLifecycleOwner viewLifecycleOwner;

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
        viewLifecycleOwner = new FoodDiaryFragment.ViewLifecycleOwner();
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

}
