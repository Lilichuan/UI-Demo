package com.example.luffy.ui_demo.ViewPagerWithDot;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luffy.ui_demo.R;

public class DotViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_view_pager);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DemoViewPagerFragment.newInstance())
                .commit();
    }
}
