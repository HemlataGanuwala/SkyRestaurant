package com.flavourheights.apple.skyrestaurantapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentlist = new ArrayList<>();
    private final List<String> FragmentListTitels = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return FragmentListTitels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitels.get(position);
    }

    public void AddFragment(Fragment fragment,String Titles)
    {
        fragmentlist.add(fragment);
        FragmentListTitels.add(Titles);
    }


}
