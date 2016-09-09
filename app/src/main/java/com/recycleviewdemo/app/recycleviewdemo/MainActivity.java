package com.recycleviewdemo.app.recycleviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView id_recycleview;
    private MyAdapter myAdapter;
    private List mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        id_recycleview = (RecyclerView) findViewById(R.id.id_recycleview);
        //设置布局管理器
        id_recycleview.setLayoutManager(new LinearLayoutManager(this));
        id_recycleview.setAdapter(myAdapter = new MyAdapter());
        //设置分隔线
        id_recycleview.addItemDecoration(new DividerItemDecration(MainActivity.this,LinearLayoutManager.VERTICAL));
        //设置添加删除动画
        id_recycleview.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mData = new ArrayList();
        for (int i = 1; i < 20; i++) {
            mData.add(i);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {//将RecycleView.ViewHolder改为MyAdapter.MyViewHolder

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.recycleview_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.list_item.setText("item:"+mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView list_item;

            public MyViewHolder(View itemView) {
                super(itemView);
                list_item = (TextView) itemView.findViewById(R.id.list_item);//必须是view.findViewById否者会显示为空，切记
            }
        }

    }
}
