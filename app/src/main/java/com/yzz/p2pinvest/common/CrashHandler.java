package com.yzz.p2pinvest.common;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.yzz.p2pinvest.utils.UIUtils;

/**
 * Created by Wookeibun on 2017/7/17.
 * 程序中未捕获的全局异常的捕获(单例)
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //一旦系统出现未捕获的异常,就会出现 如下的毁掉方法
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
//单例模式:懒汉式
    private CrashHandler(){

    }
    private static CrashHandler crashHandler = null;
    public static CrashHandler getInstance(){
        if (crashHandler == null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }
    public void init() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前类设置为未出现未捕获的异常
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        Log.e("TAG","亲,出现了未捕获的异常!");
       new Thread(){
           @Override
           public void run() {
               Looper.prepare();
               Toast.makeText(UIUtils.getContext(),"亲,出现了未捕获的异常!",Toast.LENGTH_SHORT).show();
               Looper.loop();
           }
       }.start();
        //收集异常信息
        collectionException(ex);
        try {
            Thread.sleep(2000);
            //移除当前activity
            ActivityManager.getInstance().removeCurrent();
            //结束当前的进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //结束虚拟机
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void collectionException(Throwable ex) {
        final String exMessage = ex.getMessage();

        //收集具体的用户的手机，系统的信息
        final String message = Build.DEVICE+":"+Build.MODEL+":"+Build.PRODUCT+":"+Build.VERSION.SDK_INT;
        //发送给后台此异常信息
        new Thread(){
            @Override
            public void run() {
                //需要按照指定的url,访问后台的sevlet,将异常信息发送过
                Log.e("TAG","exception:"+exMessage);
                Log.e("TAG","exception:"+message);
            }
        }.start();
    }
}
