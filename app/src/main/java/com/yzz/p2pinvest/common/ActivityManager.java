package com.yzz.p2pinvest.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Wookeibun on 2017/7/17.
 * 统一应用程序中所有的Activity的栈管理（单例）
  * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 */

public class ActivityManager {
//    单例模式:饿汉式
    private ActivityManager(){

    }
    private static ActivityManager activityManager=new ActivityManager();

    public static ActivityManager getInstance() {
        return activityManager;
    }
//    提供栈的对象
    private Stack<Activity> activityStack=new Stack<>();
//activity的添加
    public void add(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    //    删除指定的activity
    public void remove(Activity activity) {
        if (activity!=null) {
//            for (int i = 0; i < activityStack.size(); i++) {
//                Activity currentActivity = activityStack.get(i);
//                if (currentActivity.getClass().equals(activity.getClass())) {
//                    currentActivity.finish();//销毁当前的activity
//                    activityStack.remove(activity);//从栈空间移除
//                }
//            }
            for (int i=activityStack.size()-1;i>0;i--) {
                Activity currentActivity = activityStack.get(i);
                if (currentActivity.getClass().equals(activity.getClass())) {
                    currentActivity.finish();//销毁当前的activity
                    activityStack.remove(activity);//从栈空间移除
                }
            }
        }
    }

    //    删除当前的activity
    public void removeCurrent() {
//        方法一
//        Activity activity = activityStack.get(activityStack.size() - 1);
//        activity.finish();
//        activityStack.remove(activityStack.size() - 1);

//        方法二
        Activity activity=activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }
}
