package com.example.root.dietlogging.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitles = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);

    }

    @Override
    public int getCount() {
        return fragmentListTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){

       return fragmentListTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String title){

        fragmentList.add(fragment);
        fragmentListTitles.add(title);
    }
}