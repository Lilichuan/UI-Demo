package com.example.luffy.ui_demo.ViewPagerWithDot;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luffy.ui_demo.R;


public class DemoViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    public DemoViewPagerFragment() {
        // Required empty public constructor
    }

    public static DemoViewPagerFragment newInstance() {
        DemoViewPagerFragment fragment = new DemoViewPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demo_view_pager, container, false);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager());
        viewPager = (ViewPager)root.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout)root.findViewById(R.id.dot_tab);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    /*
    *
    * Adapter
    * 製造四頁大顏色頁面的Fragment
    *
    * */
    private class MyFragmentAdapter extends FragmentPagerAdapter {

        String[] color_array;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            color_array = new String[]{"#1751c3", "#b11120", "#b29000", "#4a804c"};
        }

        @Override
        public Fragment getItem(int position) {
            return ColorFragment.newInstance(color_array[position]);
        }

        @Override
        public int getCount() {
            return color_array.length;
        }
    }

}
