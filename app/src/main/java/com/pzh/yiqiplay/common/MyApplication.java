package com.pzh.yiqiplay.common;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.mobapi.MobAPI;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by pzh on 16/4/7.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "900024957", false);
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=5721ba86");
        MobAPI.initSDK(this,"ea14a6fbcea8");
    }
}
