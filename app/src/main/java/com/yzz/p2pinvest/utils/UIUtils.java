package com.yzz.p2pinvest.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.yzz.p2pinvest.common.MyApplication;


/**
 * Created by Wookeibun on 2017/7/16.
 * 专门提供为处理一些UI相关的问题而创建的工具类，提供资源获取的通用方法，避免每次都写重复的代码获取结果。
 */

public class UIUtils {
    private static boolean inMainThread;

    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);

    }

    public static View getView(int viewId) {
        View view = View.inflate(getContext(), viewId, null);
        return view;
    }

    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    //与屏幕分辨率相关的
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);//实现四舍五入
    }
    //与屏幕分辨率相关的
    public static int dp2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density + 0.5);
    }

    //保证runOnUiThread中的造作在主线程中执行
    public static void runOnUiThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            UIUtils.getHandler().post(runnable);
        }
    }
//判断当前线程是否是主线程
    public static boolean isInMainThread() {
        int currentThreadId = Process.myTid();
        return MyApplication.mainThreadId ==currentThreadId;
    }
}
