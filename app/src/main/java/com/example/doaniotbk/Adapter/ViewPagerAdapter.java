package com.example.doaniotbk.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doaniotbk.Fragment.Fragment_chart;
import com.example.doaniotbk.Fragment.Fragment_history;
import com.example.doaniotbk.Fragment.Fragment_today;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int pagerNumber;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.pagerNumber=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Fragment_today();
            case 1: return new Fragment_chart();
            case 2: return new Fragment_history();
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.pagerNumber;
    }
}
