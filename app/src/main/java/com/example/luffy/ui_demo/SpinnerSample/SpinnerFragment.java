package com.example.luffy.ui_demo.SpinnerSample;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.luffy.ui_demo.R;


public class SpinnerFragment extends Fragment {

    public SpinnerFragment() {
        // Required empty public constructor
    }


    public static SpinnerFragment newInstance() {
        SpinnerFragment fragment = new SpinnerFragment();
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
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

        adapter = new MyAdapter(getActivity(), R.layout.spinner_item);
        createFakeData();
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private MyAdapter adapter;

    private class MyAdapter extends ArrayAdapter<SpinnerItem> implements SpinnerAdapter{

        private LayoutInflater layoutInflater;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return myGetView(position, convertView);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return myGetView(position, convertView);
        }

        private View myGetView(int position, View convertView){
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.spinner_item, null);
            }

            TextView textView = (TextView)convertView;
            textView.setText(getItem(position).getValue());

            return convertView;
        }

    }

    private void createFakeData(){
        adapter.add(new SpinnerItem("AAAAA"));
        adapter.add(new SpinnerItem("BBBBBBB"));
        adapter.add(new SpinnerItem("CCCC"));
        adapter.add(new SpinnerItem("DDDDDDDDDDDDD"));
        adapter.add(new SpinnerItem("EEEE"));
    }

    private class SpinnerItem{

        String value;

        SpinnerItem(String s){
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

}
