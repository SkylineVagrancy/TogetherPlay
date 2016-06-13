package com.pzh.yiqiplay.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.pzh.yiqiplay.DesktopUI;
import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;

/**
 * Created by pzh on 16/5/19.
 */
public class NotificationUI extends BaseUI {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        this.context = this;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                showNotification();
                break;
            case R.id.button3:
                showNotification();
                break;
            case R.id.button4:
                showNotification();
                break;
        }
    }

    public void showNotification() {
        RemoteViews contentView = new RemoteViews(context.getPackageName(),
                R.layout.push_nomal_layout);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.compass));
        notification.setAutoCancel(true);        //点击自动消息
        notification.setDefaults(Notification.DEFAULT_ALL);
        Notification no = notification.build();
//        notification.mContentTitle="欧洲杯凌晨开赛";
//        notification.mContentText="欧洲杯揭幕战凌晨打响，罗马尼亚挑战法国东道主，高炉先打速度你撒的你是哪的胜女的时代的实打实大山东省爱的撒旦实打实的三地那是你的三大男撒旦三点三点";
        contentView.setTextViewText(R.id.pushTitle, "欧洲杯凌晨开赛");
        contentView.setTextViewText(R.id.pushContent, "欧洲杯揭幕战凌晨打响，罗马尼亚挑战法国东道主，高炉先打速度你撒的你是哪的胜女的时代的实打实大山东省爱的撒旦实打实的三地那是你的三大男撒旦三点三点");
        notification.setContent(contentView);

        //铃声,振动,呼吸灯
        Intent intent = new Intent(context, DesktopUI.class);    //点击通知进入的界面
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());


//        RemoteViews contentView = new RemoteViews(context.getPackageName(),
//                R.layout.push_nomal_layout);
//        Intent intent = new Intent(NotificationUI.this, DesktopUI.class);
//        contentView.setTextViewText(R.id.pushTitle, "欧洲杯凌晨开赛");
//        contentView.setTextViewText(R.id.pushContent, "欧洲杯揭幕战凌晨打响，罗马尼亚挑战法国东道主，高炉先打速度你撒的你是哪的胜女的时代的实打实大山东省爱的撒旦实打实的三地那是你的三大男撒旦三点三点");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
//        builder.setContent(contentView)
//                .setContentIntent(pendingIntent)
//                .setTicker("test");
//
//        Notification notification=builder.build();
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) this
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        int id = (int) System.currentTimeMillis();
//        notificationManager.notify(id, notification);
    }

}
