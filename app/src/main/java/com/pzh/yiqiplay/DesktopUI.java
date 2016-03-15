package com.pzh.yiqiplay;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.pzh.yiqiplay.bean.InfoBean;
import com.pzh.yiqiplay.bean.ViewBean;
import com.pzh.yiqiplay.common.BaseUI;
import com.pzh.yiqiplay.config.AppConfig;
import com.pzh.yiqiplay.control.AllUiAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

public class DesktopUI extends BaseUI {
    private ListView demoList;
    private List<ViewBean> viewLists = new ArrayList<ViewBean>();
    private AllUiAdapter adapter;
    private LinearLayout content;
    private DrawerLayout drawerLayout;
    private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desktop);
        initDisplay();
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.argb(0x99, 0x00, 0x00, 0x00));
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        demoList= (ListView) findViewById(R.id.left_drawer);
        icon= (ImageView) findViewById(R.id.user_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        content= (LinearLayout) findViewById(R.id.content_frame);
        queryData();
    }

    public void initDisplay(){
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        AppConfig.Src_height=metrics.heightPixels;
        AppConfig.Src_width=metrics.widthPixels;
        AppConfig.density=metrics.densityDpi;
    }
    public void queryData(){
        BmobQuery query=new BmobQuery("ViewList");
        query.findObjects(this, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("TAG",jsonArray.toString());
                viewLists=JSON.parseArray(jsonArray.toString(),ViewBean.class);
                Log.d("TAG",viewLists.size()+"");
                adapter = new AllUiAdapter(context, viewLists);
                demoList.setAdapter(adapter);
            }
            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

}
