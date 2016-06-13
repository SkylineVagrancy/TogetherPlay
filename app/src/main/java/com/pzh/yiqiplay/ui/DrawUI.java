package com.pzh.yiqiplay.ui;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;
import com.pzh.yiqiplay.control.Shudu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by pzh on 16/6/12.
 */
public class DrawUI extends BaseUI {
    private String baseurl="/sdcard/pzh";
    private long lastClick=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawview_layout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(lastClick!=0 && System.currentTimeMillis()-lastClick<300){
                lastClick=0;
                getBitmap();
                Toast.makeText(context,"截图成功",Toast.LENGTH_SHORT).show();
            }else{
                lastClick= System.currentTimeMillis();
            }
        }
        return super.onTouchEvent(event);
    }

    public void getBitmap(){
        View view=this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap=view.getDrawingCache();
        Rect frame=new Rect();
        String url=baseurl+System.currentTimeMillis()+".png";
        this.getWindow().getDecorView().getGlobalVisibleRect(frame);

        try {
            FileOutputStream out=new FileOutputStream(url);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            if(out!=null){
                out.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        view.setDrawingCacheEnabled(false);
    }
    public void onClick(View v){
        getBitmap();
    }
}
