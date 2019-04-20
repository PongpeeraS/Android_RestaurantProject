package com.example.restaurantproject;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.example.restaurantproject.Order.OrderPagerAdapter;

/*Activity to view the user's current & previous orders (2 separate tabs)*/
public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        TabLayout tabLayout = findViewById(R.id.tabLayout_order);
        final ViewPager viewPager = findViewById(R.id.pager);

        //Creating the 2 tabs in the activity
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_current));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_history));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Creating and setting the adapter for the pager
        final OrderPagerAdapter adapter = new OrderPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Listeners for the the selected tab changes
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}