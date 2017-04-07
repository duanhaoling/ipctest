package com.example.ipctest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import java.util.List;

/**
 * Created by ldh on 2016/8/4 0004.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private boolean isBackground;//app是否在后台，使用官方的Api监听

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName();
        Log.d(TAG, "application start , process name:" + processName);
        Log.d(TAG, "application start , processPid:" + Process.myPid() + " myUid: " + Process.myUid() + " myTid: " + Process.myTid());
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    /**
     * 终止进程时调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Log.d(TAG, "app切换到后台");
            isBackground = true;
        }
    }

    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            //------------app切换到前台------------
            if (isBackground) {
                isBackground = false;
                Log.d(TAG, "app切换到前台");
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public String getProcessName() {
        int pid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
