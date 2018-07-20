package com.example.fabricebenimana.a7weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fabricebenimana.a7weather.fragment.ForecastWeatherFragment;
import com.example.fabricebenimana.a7weather.fragment.TodayWeatherFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment;
        switch (index) {
            default:
            case 0:
                fragment = TodayWeatherFragment.getInstance();
                break;
            case 1:
                fragment = ForecastWeatherFragment.getInstance();

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return TodayWeatherFragment.TAB_TITLE;
        } else {
            return ForecastWeatherFragment.TAB_TITLE;
        }
    }
}
