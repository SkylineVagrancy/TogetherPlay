package com.pzh.yiqiplay.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pzh.yiqiplay.DesktopUI;

/**
 * Created by pzh on 16/5/3.
 */
public class BootupReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_BOOT.equals(intent.getAction()))
        {
            Toast.makeText(context,"jiantinfdaole",Toast.LENGTH_LONG).show();
            Intent StartIntent=new Intent(context,DesktopUI.class); //接收到广播后，跳转到MainActivity
            StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(StartIntent);
        }
    }
}
