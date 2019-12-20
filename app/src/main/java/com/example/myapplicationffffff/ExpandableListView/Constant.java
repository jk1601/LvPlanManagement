package com.example.myapplicationffffff.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 *         常量类
 */
public final class Constant {


    public static String[] TermName = {"第一学期","第二学期","第三学期","第四学期"};
    public static String[] strings = {"任务一", "任务二", "任务三", "任务四", "任务五", "任务六", "任务日"};


    public static boolean[] getIsCheck(String[] a){
        boolean[] ischecked = new boolean[a.length];
        for (int i = 0;i<a.length;i++) ischecked[i] = false;
        return ischecked;
    }
    public static boolean[] isChecked = { false, false, false, false, false, false, false };

}
