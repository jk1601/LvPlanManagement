package com.example.myapplicationffffff.ExpandableListView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.example.myapplicationffffff.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 普通 ExpandableListView，支持只展开一个子项
 */
public class NormalExpandActivity extends AppCompatActivity {
    private ExpandableListView termExpandableListView;
    private List<String> termGroupData = new ArrayList<>();
    private List<List<String>> termChildData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_plan_expand);

        initChildData();

        termExpandableListView = findViewById(R.id.term_expandable_list);
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
                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NormalExpandActivity.this);
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

                Toast.makeText(NormalExpandActivity.this, termChildData.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();
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
    //    public  void initGroupData(){
//
//        termGroupData.add("第一学期");
//        termGroupData.add("第二学期");
//        termGroupData.add("第三学期");
//        termGroupData.add("第四学期");
//    }
    public  void initChildData(){
        addInfo("第一学期",new String[]{"添加任务"});
        addInfo("第二学期",new String [] {"添加任务"});
        addInfo("第三学期",new String[]{"添加任务"});
        addInfo("第四学期",new String[]{"添加任务"});
    }
    public void addInfo(String p, String[] c) {
        termGroupData.add(p);
        List<String> item = new ArrayList<>(Arrays.asList(c));
        termChildData.add(item);
    }

//    public void addChild(String[] c){
//        List<String> item = new ArrayList<>();
//        for (int i = 0; i < c.length; i++) {
//            item.add(c[i]);
//        }
//        termChildData.add(item);
//    }

}

