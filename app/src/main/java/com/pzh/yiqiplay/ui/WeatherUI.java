package com.pzh.yiqiplay.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import com.pzh.yiqiplay.DesktopUI;
import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzh on 16/4/29.
 */
public class WeatherUI extends BaseUI implements APICallback {
    private Spinner citys;
    private ListView listView;
    private StringBuffer sbf;
    private SpeechSynthesizer mTts;
    private SynthesizerListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        sbf = new StringBuffer();
        sbf.append("下面播报深圳天气");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        sbf.append(",今天是" + str);
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        myListener = new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                Toast.makeText(context, speechError.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
        api.queryByCityName("深圳", this);
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) map.get("result");
        HashMap<String, Object> weather = results.get(0);
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=oldFormat.parse(weather.get("updateTime").toString());
            String time=newFormat.format(date);
            sbf.append(",更新时间"+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sbf.append("," + weather.get("week"));
        sbf.append("," + weather.get("humidity"));
        sbf.append(",风力" + weather.get("wind"));
        sbf.append(",空气质量" + weather.get("airCondition"));
        sbf.append(",太阳升起时间" + weather.get("sunrise"));
        sbf.append(",太阳落山时间" + weather.get("sunset"));
        sbf.append(",温度" + weather.get("temperature"));
        sbf.append(",天气" + weather.get("weather"));

        mTts.startSpeaking(sbf.toString(), myListener);
        System.out.println(sbf.toString());
    }

    @Override
    public void onError(API api, int i, Throwable throwable) {

    }
    public void onClick(View view){
        Intent intent = new Intent(context, DesktopUI.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Toast.makeText(context,"test",Toast.LENGTH_LONG).show();
        RemoteViews contentView = new RemoteViews(context.getPackageName(),
                R.layout.push_nomal_layout);
        contentView.setTextViewText(R.id.pushContent,"test");
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setContent(contentView)
        .setTicker("youxiaoxi ")
        .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
    }
}
