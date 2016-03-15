package com.pzh.yiqiplay.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import cn.bmob.v3.Bmob;

/**
 * Created by pzh on 16/3/1.
 */
public class BaseUI extends Activity {
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        Bmob.initialize(this, "ad5012eb79bb87245ad1624c43ffd08b");
    }
}
