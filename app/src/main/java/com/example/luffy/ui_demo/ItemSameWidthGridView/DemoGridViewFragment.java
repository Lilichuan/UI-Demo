package com.example.luffy.ui_demo.ItemSameWidthGridView;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luffy.ui_demo.R;

import java.util.ArrayList;
import java.util.List;


public class DemoGridViewFragment extends Fragment {

    //橫要有幾個元件(column)
    private final int COLUMN_COUNT = 3;

    //圖片檔案高度
    private int pic_h;

    //fragment接收參數所需的字串key
    private static final String KEY_SCREEN_W = "screen_w";


    public DemoGridViewFragment() {
        // Required empty public constructor
    }


    //需要傳入螢幕寬度，以便計算圖片高度
    public static DemoGridViewFragment newInstance(int screen_w) {
        DemoGridViewFragment fragment = new DemoGridViewFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_SCREEN_W, screen_w);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //從螢幕寬度計算圖片高度
        if (getArguments() != null) {
            int screen_w = getArguments().getInt(KEY_SCREEN_W);
            pic_h = screen_w / (COLUMN_COUNT + 1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demo_grid_view, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.grid_view);

        createFakeData();

        //像GridView一樣表格排列清單
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(new MAdapter());

        return root;
    }




    /*
    *
    * 給 RecyclerView 的 Adapter
    *
    * */
    private class MAdapter extends RecyclerView.Adapter<MViewHolder>{

        public MAdapter(){

        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_text, null);
            return new MViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            PictureItem item = list.get(position);
            holder.getImageView().setBackgroundResource(item.getPic_resource());
            holder.getTextView().setText(item.getPic_str());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



    /*
    *
    * 存在於列表中的單一UI欄位
    * */
    private class MViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public MViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.pic);
            imageView.getLayoutParams().height = pic_h;
            textView = (TextView)itemView.findViewById(R.id.text);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }




    //資料列表
    private List<PictureItem> list = new ArrayList<>();


    /*
    *
    * 單一數據資料
    *
    * */
    private class PictureItem{

        public String pic_str;
        public int pic_resource;

        public void setPicResource(int pic_resource) {
            this.pic_resource = pic_resource;
        }

        public int getPic_resource() {
            return pic_resource;
        }

        public void setPic_str(String pic_str) {
            this.pic_str = pic_str;
        }

        public String getPic_str() {
            return pic_str;
        }
    }

    //製造假資料
    private void createFakeData(){
        PictureItem item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花一號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花三十五號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花8876766544號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("草草一號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花IV號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花XXXXXXXX號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花三十五號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花8876766544號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花IV號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花XXXXXXXX號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花三十五號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("花花8876766544號");
        list.add(item);

        item = new PictureItem();
        item.setPicResource(R.drawable.flower);
        item.setPic_str("草草一號");
        list.add(item);
    }

}
