package com.example.luffy.ui_demo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luffy.ui_demo.ViewPagerWithDot.DotViewPagerActivity;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        createItem();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MAdapter(getActivity()));

        return view;
    }

    private class MAdapter extends RecyclerView.Adapter<MViewHolder>{

        private LayoutInflater layoutInflater;

        public MAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_main, null);
            return new MViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.setText(item.title_str_id);
            holder.setOnclick(item);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }

    //ViewHolder 是一種暫存的View的小物件
    private class MViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private View view;

        public MViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_text);
            view = itemView;
        }

        public void setText(int str_id){
            textView.setText(str_id);
        }

        public void setOnclick(Item item) {
            view.setTag(item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item1 = (Item)v.getTag();
                    startActivity(item1.intent);
                }
            });
        }
    }

    //建立大量重複的數據資料
    private void createItem(){
        Item item = new Item();
        item.title_str_id = R.string.demo_viewpager_dot;
        item.intent = new Intent(getActivity(), DotViewPagerActivity.class);
        itemList.add(item);
    }

    //存放大量重複的數據資料
    List<Item> itemList = new ArrayList<>();

    //大量重複的數據資料
    private class Item{
        public int title_str_id;
        private Intent intent;
    }
}
