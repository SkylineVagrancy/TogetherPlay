package com.pzh.yiqiplay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;

/**
 * Created by pzh on 16/3/3.
 */
public class EmotionUI extends BaseUI implements View.OnClickListener {
    private TextView tvCamera;
    private TextView tvImg;
    private TextView tvVideo;
    private TextView tvFace;
    private EditText etTitle;
    private EditText etContent;
    public static int CAMERA_REQUCODE=110;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_layout);
        tvCamera= (TextView) findViewById(R.id.tv_paizao);
        tvFace= (TextView) findViewById(R.id.tv_face);
        tvVideo= (TextView) findViewById(R.id.tv_video);
        tvImg= (TextView) findViewById(R.id.tv_img);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(this, MediaStore.ACTION_IMAGE_CAPTURE);
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUCODE);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
    }
}
