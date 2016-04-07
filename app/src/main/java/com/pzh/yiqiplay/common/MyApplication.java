package com.pzh.yiqiplay.common;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by pzh on 16/4/7.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "900024957", false);
    }
}
