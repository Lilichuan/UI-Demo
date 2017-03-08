package com.example.luffy.ui_demo.ItemSameWidthGridView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

import com.example.luffy.ui_demo.R;

public class DemoGridViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_grid_view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DemoGridViewFragment.newInstance(metrics.widthPixels))
                .commit();
    }
}
