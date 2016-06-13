package com.pzh.yiqiplay.ui;

import android.app.Dialog;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;
import com.pzh.yiqiplay.view.ShuduView;

/**
 * Created by pzh on 16/6/7.
 */
public class ShuduUI extends BaseUI {
    private Keyboard k2;
    private KeyboardView keyboardView;
    private ShuduView shuduView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shudu_game);
        shuduView = (ShuduView) findViewById(R.id.shudu_view);
        shuduView.setOnGameFinish(new ShuduView.OnGameFinish() {
            @Override
            public void gameFinish() {
                showDialog();
            }
        });

        selsectLevel();
        initKeyBoard();

    }

    public void initKeyBoard() {
        k2 = new Keyboard(this, R.xml.symbols);
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k2);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                System.out.println("pzh onPress=" + primaryCode);
            }

            @Override
            public void onRelease(int primaryCode) {
                System.out.println("pzh onRelese=" + primaryCode);
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                if (primaryCode == -2) {
                    shuduView.reStart(); //重新开始
                } else if (primaryCode == -3) {
                    //再来一局
                    shuduView.playAgain();
                } else {
                    shuduView.setText(primaryCode);
                }
            }

            @Override
            public void onText(CharSequence text) {
            }

            @Override
            public void swipeLeft() {
            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(context, R.style.transparent_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.shudu_finish_dialog, null);
        Button btnReplay = (Button) view.findViewById(R.id.play_again);
        Button btnFinish = (Button) view.findViewById(R.id.finish);
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuduView.reStart();
                dialog.dismiss();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


    public void selsectLevel(){
        final Dialog dialog = new Dialog(context, R.style.transparent_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.game_level, null);
        Button btn1 = (Button) view.findViewById(R.id.level1);
        Button btn2 = (Button) view.findViewById(R.id.level2);
        Button btn3 = (Button) view.findViewById(R.id.level3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(0);
                dialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1);
                dialog.dismiss();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(2);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();

    }

    public void startGame(int type){
        shuduView.setType(type);
        shuduView.reStart();
    }
}
