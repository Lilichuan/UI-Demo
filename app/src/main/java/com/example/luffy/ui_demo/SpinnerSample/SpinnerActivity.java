package com.example.luffy.ui_demo.SpinnerSample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.luffy.ui_demo.R;

public class SpinnerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SpinnerFragment.newInstance())
                .commit();
    }
}
