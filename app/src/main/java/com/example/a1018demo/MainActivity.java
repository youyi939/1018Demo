package com.example.a1018demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private OneFragment oneFragment = new OneFragment();
    private TwoFragment twoFragment = new TwoFragment();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.cj_viewpager);


        fragments.add(oneFragment);
        fragments.add(twoFragment);


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return  fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };


        viewPager.setAdapter(adapter);


    }
}
