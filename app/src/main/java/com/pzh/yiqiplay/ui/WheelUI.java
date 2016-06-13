package com.pzh.yiqiplay.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;
import com.pzh.yiqiplay.common.CustemDialog;
import com.pzh.yiqiplay.config.AppConfig;
import com.pzh.yiqiplay.view.WheelView;

import java.util.ArrayList;

/**
 * Created by pzh on 16/5/25.
 */
public class WheelUI extends BaseUI {
    private WheelView wheelView;
    private WindowManager mWindowsManager;
    private WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_layout);
        wheelView = (WheelView) findViewById(R.id.item_pick);
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("items" + i);
        }
        wheelView.setCurrentIndex(4);
        wheelView.setWheelLists(items);
        wheelView.setOnWheelViewSelected(new WheelView.OnWheelViewSelected() {
            @Override
            public void onSelected(int index, String value) {
                Toast.makeText(WheelUI.this, value, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View v) {
        final Dialog dia = new Dialog(context,R.style.transparent_dialog);
        View view = LayoutInflater.from(WheelUI.this).inflate(R.layout.dialog_layout, null);
        view.findViewById(R.id.ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.cancel();
            }
        });
        dia.setContentView(view);
        dia.setCanceledOnTouchOutside(true);
        dia.show();
    }


}
