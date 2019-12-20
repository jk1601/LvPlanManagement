package com.example.myapplicationffffff;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.myapplicationffffff.ExpandableListView.Constant;
import com.example.myapplicationffffff.ExpandableListView.NormalExpandableListAdapter;
import com.example.myapplicationffffff.ExpandableListView.OnGroupExpandedListener;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Plan_Fragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<View> views;
    List<String> titles;
    //private ExpandingList mExpandingList;
    private RecyclerView weekRecyclerView;
    private Button iv_add_week;
    private RecyclerView dayRecyclerView;
    private Button iv_add_day;
    private WeekRecycleAdapter weekadapter;
    private DayRecycleAdapter dayadapter;
    private List<String> list = new ArrayList<>();
    private ExpandableListView termExpandableListView;
    private List<String> termGroupData = new ArrayList<>();
    private List<List<String>> termChildData = new ArrayList<>();
    View week_plan;
    View term_plan;
    View daily_plan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.plan_page, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.one_view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        week_plan = LayoutInflater.from(view.getContext()).inflate(R.layout.week_plan_recyclerview, null);
        term_plan = LayoutInflater.from(view.getContext()).inflate(R.layout.term_plan_expand, null);
        daily_plan = LayoutInflater.from(view.getContext()).inflate(R.layout.day_plan_recyclerview, null);
        termExpandableListView = term_plan.findViewById(R.id.term_expandable_list);
        //周计划初始化
        initView();
        weekInitRecycle();
        dayInitRecycle();
        weekSetupDefault();
        daySetupDefault();

        iv_add_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加自带默认动画
                weekShowInsertDialog();
                //adapter.addData(list.size(),text.getText().toString());
                //adapter.addData(list.size(),"wfa");

            }
        });
        iv_add_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加自带默认动画
                dayShowInsertDialog();
                //adapter.addData(list.size(),text.getText().toString());
                //adapter.addData(list.size(),"wfa");

            }
        });
        //
        views = new ArrayList<>();
        views.add(term_plan);
        views.add(week_plan);
        views.add(daily_plan);
        titles = new ArrayList<>();
        titles.add("学期计划");
        titles.add("周计划");
        titles.add("日计划");

        ViewPage_Adapter adapter = new ViewPage_Adapter(views, titles);

        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        initTermPage();
    }


    //学期计划内容

    private void initTermPage(){
        initChildData();

        final NormalExpandableListAdapter adapter = new NormalExpandableListAdapter(termGroupData, termChildData);
        termExpandableListView.setAdapter(adapter);
        adapter.setOnGroupExpandedListener(new OnGroupExpandedListener() {
            @Override
            public void onGroupExpanded(int groupPosition) {
                expandOnlyOne(groupPosition);
            }
        });


        //长按点击事件
        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final long packedPosition = termExpandableListView.getExpandableListPosition(position);
                final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

                //子项
//                if (childPosition != -1) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(NormalExpandActivity.this);
//                    builder.setTitle("警告");
//                    builder.setMessage("您生在试图删除该条数据，确定删除吗？");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
////                            Constant.FIGURES[groupPosition][childPosition] = null;
//                            termChildData.get(groupPosition).remove(childPosition);
//                        }
//                    });
//                    builder.setNegativeButton("取消", null);
//                    builder.show();
//                }
                //父项
                if (groupPosition != -1) {

                    final boolean[] isCheck = Constant.getIsCheck(Constant.strings);
                    final android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(termExpandableListView.getContext());
                    alertBuilder.setTitle("添加任务计划");
                    alertBuilder.setMultiChoiceItems(Constant.strings, isCheck, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            isCheck[i] = b;
                        }
                    });
                    alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            List<String> item = new ArrayList<>();
                            for (int j=0;j<Constant.strings.length;j++){
                                if (isCheck[j]){
                                    item.add(Constant.strings[j]);
                                }
                            }
                            termChildData.set(groupPosition,item);
                            Log.i("aa", termChildData.toString());
                            Log.i("ischeck", Arrays.toString(isCheck));
                            adapter.notifyDataSetChanged();
                        }
                    });
                    alertBuilder.setNegativeButton("取消", null);
                    alertBuilder.show();


                }
                return true;
            }
        };

        termExpandableListView.setOnItemLongClickListener(onItemLongClickListener);

        //  设置分组项的点击监听事件
        termExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // 请务必返回 false，否则分组不会展开
                return false;
            }
        });

        //  设置子选项点击监听事件
        termExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(termExpandableListView.getContext(), termChildData.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    // 每次展开一个学期分组后，关闭其他的分组
    private boolean expandOnlyOne(int expandedPosition) {
        boolean result = true;
        int groupLength = termExpandableListView.getExpandableListAdapter().getGroupCount();
        for (int i = 0; i < groupLength; i++) {
            if (i != expandedPosition && termExpandableListView.isGroupExpanded(i)) {
                result &= termExpandableListView.collapseGroup(i);
            }
        }
        return result;
    }

    public  void initChildData(){
        addInfo("第一学期",new String[]{"添加任务"});
        addInfo("第二学期",new String[]{"添加任务"});
        addInfo("第三学期",new String[]{"添加任务"});
        addInfo("第四学期",new String[]{"添加任务"});
    }
    public void addInfo(String p, String[] c) {
        termGroupData.add(p);
        List<String> item = new ArrayList<>(Arrays.asList(c));
        termChildData.add(item);
    }


    //学期计划结束

    //以下是周计划内容



    //插入计划弹出多选框
    private void weekShowInsertDialog() {
        final EditText text = new EditText(week_plan.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(week_plan.getContext());
        builder.setView(text);
        builder.setTitle("插入");
        builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                weekadapter.addData(list.size(),text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    private void weekInitRecycle() {
        // 纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(week_plan.getContext());
        weekRecyclerView.setLayoutManager(linearLayoutManager);
//   获取数据，向适配器传数据，绑定适配器
        list = initData();
        weekadapter = new WeekRecycleAdapter(week_plan.getContext(), list);
        weekRecyclerView.setAdapter(weekadapter);
//   添加动画
        weekRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void initView() {
        iv_add_week = (Button) week_plan.findViewById(R.id.add_button_week);
        weekRecyclerView = (RecyclerView) week_plan.findViewById(R.id.recyclerview_week);

        iv_add_day = (Button) daily_plan.findViewById(R.id.iv_add_day);
        dayRecyclerView = (RecyclerView) daily_plan.findViewById(R.id.recyclerview_day);
    }

    protected ArrayList<String> initData() {
        ArrayList<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mDatas.add("计划" + i);
        }
        return mDatas;
    }


    //选择是第几周
    private void weekSetupDefault() {
        NiceSpinner spinner1 = week_plan.findViewById(R.id.zhou_nice_spinner);
        NiceSpinner spinner2 = week_plan.findViewById(R.id.xueqi_nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        spinner1.attachDataSource(dataset);
        spinner1.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(week_plan.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        spinner2.attachDataSource(dataset);
        spinner2.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(week_plan.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //周计划结束

    //以下是日计划内容


    private void daySetupDefault() {
        NiceSpinner spinner = daily_plan.findViewById(R.id.day_nice_spinner_day);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(daily_plan.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void dayShowInsertDialog() {
        final EditText text = new EditText(daily_plan.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(daily_plan.getContext());
        builder.setView(text);
        builder.setTitle("插入");
        builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dayadapter.addData(list.size(),text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    private void dayInitRecycle() {
        // 纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(daily_plan.getContext());
        dayRecyclerView.setLayoutManager(linearLayoutManager);
//   获取数据，向适配器传数据，绑定适配器
        list = initData();
        dayadapter = new DayRecycleAdapter(daily_plan.getContext(), list);
        dayRecyclerView.setAdapter(dayadapter);
//   添加动画
        dayRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
