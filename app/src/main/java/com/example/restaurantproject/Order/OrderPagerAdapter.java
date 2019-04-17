package com.example.restaurantproject.Order;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*Adapter for the Pager object in OrderActivity, to support changing between Current & History tabs*/
public class OrderPagerAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;
    public OrderPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    /*Creates fragment once a tab position has been selected in OrderActivity*/
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OrderCurrentFragment();
            case 1:
                return new OrderHistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}