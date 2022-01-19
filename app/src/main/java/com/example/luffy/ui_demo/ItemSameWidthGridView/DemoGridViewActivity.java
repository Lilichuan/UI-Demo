package com.example.luffy.ui_demo.ItemSameWidthGridView;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
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
