package com.pzh.yiqiplay.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;

/**
 * Created by pzh on 16/4/28.
 */
public class TextToSpeechUI extends BaseUI {
    private SpeechSynthesizer mTts;
    private SynthesizerListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_to_speech);
        mTts = SpeechSynthesizer.createSynthesizer(context, null);

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu");//设置发音人
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
                Toast.makeText(context,speechError.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };


    }

    public void onClick(View v) {
        mTts.startSpeaking("天气阵雨，湿度15，东北风，长袖，星期三，2016年12月10号", myListener);
    }

}
