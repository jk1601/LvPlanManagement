package com.example.myapplicationffffff;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class DayRecycleAdapter extends RecyclerView.Adapter<DayRecycleAdapter.MyViewHolder> {
    private Context context;
    private List<String> list;
    public DayRecycleAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_day, parent,false));
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position));
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 1) {
                    Snackbar.make(v, "无法删除", Snackbar.LENGTH_SHORT).show();
                } else {
                    //删除自带默认动画
                    removeData(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    // 添加数据
    public void addData(int position,String str) {
        //在list中添加数据，并通知条目加入一条
        list.add(position, str);
        //添加动画
        notifyItemInserted(position);
    }
    // 删除数据
    public void removeData(int position) {
        list.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv_delete;
        NiceSpinner spinner1,spinner2;
        //因为删除有可能会删除中间条目，然后会造成角标越界，所以必须整体刷新一下！
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num_day);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete_day);
            spinner1 = view.findViewById(R.id.wancheng);
            spinner2 = view.findViewById(R.id.shenhe);
            wanchengsetupDefault(view);
            shenhesetupDefault(view);
        }
    }
    //完成
    private void wanchengsetupDefault(View view) {
        NiceSpinner spinner =view.findViewById(R.id.wancheng);
        List<String> dataset = new LinkedList<>(Arrays.asList("周？","One", "Two", "Three", "Four", "Five"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
               //
            }
        });
    }
    //审核
    private void shenhesetupDefault(View view) {
        NiceSpinner spinner = view.findViewById(R.id.shenhe);
        List<String> dataset = new LinkedList<>(Arrays.asList("选择","One", "Two", "Three", "Four", "Five"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                //String item = parent.getItemAtPosition(position).toString();
            }
        });
    }

}