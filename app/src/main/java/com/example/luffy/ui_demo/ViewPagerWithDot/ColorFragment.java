package com.example.luffy.ui_demo.ViewPagerWithDot;


import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luffy.ui_demo.R;

/**
 *
 * 只顯示色塊的Fragment
 *
 */
public class ColorFragment extends Fragment {

    private static final String ARG_COLOR = "param1";

    private String m_color;


    public ColorFragment() {
        // Required empty public constructor
    }


    public static ColorFragment newInstance(String color_value) {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, color_value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            m_color = getArguments().getString(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color, container, false);
        v.setBackgroundColor(Color.parseColor(m_color));
        return v;
    }

}
