package com.pzh.yiqiplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.bean.ShuduBean;
import com.pzh.yiqiplay.config.AppConfig;
import com.pzh.yiqiplay.control.Shudu;


/**
 * Created by pzh on 16/6/7.
 */
public class ShuduView extends View {
    private ShuduBean[][] shudu;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;  //0:简单   1：中等   2：困难
    private int uncheckNum;
    private int itemWidth;
    private Paint numPaint;
    private Paint linePaintLight;
    private Paint linePaintDark;
    private Paint rectPaint;
    private Paint linePaintHit;
    private Paint linePaintConflict;
    private Paint nomalPaint;
    private int textBaseLine;
    private int topMargin = 100;
    private int currentXIndex = -1;
    private int currentYIndex = -1;
    private int conflictX = -1;
    private int conflictY = -1;
    private boolean touchAble = true;
    private int unCheckCount;
    public Context context;

    public OnGameFinish getOnGameFinish() {
        return onGameFinish;
    }

    public void setOnGameFinish(OnGameFinish onGameFinish) {
        this.onGameFinish = onGameFinish;
    }

    private OnGameFinish onGameFinish;

    public ShuduView(Context context) {
        this(context, null);
    }

    public ShuduView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShuduView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void initView() {
        itemWidth = (int) (AppConfig.Src_width / 9);
        linePaintLight = new Paint();
        linePaintLight.setAntiAlias(true);
        linePaintLight.setColor(getResources().getColor(R.color.shudu_light));
        linePaintLight.setStrokeWidth(5f);

        linePaintHit = new Paint();
        linePaintHit.setAntiAlias(true);
        linePaintHit.setColor(getResources().getColor(R.color.shudu_hit));
        linePaintHit.setStrokeWidth(5f);
        linePaintDark = new Paint();
        linePaintDark.setAntiAlias(true);
        linePaintDark.setColor(getResources().getColor(R.color.shudu_dark));
        linePaintDark.setStrokeWidth(5f);
        nomalPaint = new Paint();
        nomalPaint.setAntiAlias(true);
        nomalPaint.setColor(Color.WHITE);
        nomalPaint.setTextSize(itemWidth * 0.75f);
        nomalPaint.setTextAlign(Paint.Align.CENTER);
        nomalPaint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics front = nomalPaint.getFontMetrics();
        nomalPaint.getFontMetrics(front);
        textBaseLine = (int) ((itemWidth - front.descent - front.ascent) / 2);
        numPaint = new Paint();
        numPaint.setAntiAlias(true);
        numPaint.setColor(getResources().getColor(R.color.num));
        numPaint.setTextSize(itemWidth * 0.75f);
        numPaint.setTextAlign(Paint.Align.CENTER);
        numPaint.setStyle(Paint.Style.FILL);
        numPaint.getFontMetrics(front);

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setColor(getResources().getColor(R.color.select_background));
        rectPaint.setStyle(Paint.Style.FILL);


        linePaintConflict = new Paint();
        linePaintConflict.setAntiAlias(true);
        linePaintConflict.setColor(Color.RED);
        linePaintConflict.setTextSize(itemWidth * 0.75f);
        linePaintConflict.setTextAlign(Paint.Align.CENTER);
        linePaintConflict.setStyle(Paint.Style.FILL);
//        initShudu();

    }

    public void initShudu() {
        int count = 0;
        shudu = Shudu.initShuduArray();
        unCheckCount = -1;
        currentYIndex = -1;
        currentXIndex = -1;
        conflictX = -1;
        conflictY = -1;
        if (type == 0) {
            uncheckNum = 30;
        } else if (type == 1) {
            uncheckNum = 40;
        } else if (type == 2) {
            uncheckNum = 50;
        }else{
            unCheckCount=30;
        }
        while (count < uncheckNum) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);
            if (x >= 0 && x < 9 && y >= 0 && y < 9) {
                if (!shudu[x][y].isCheckable()) {
                    shudu[x][y].setCheckable(true);
                    shudu[x][y].setNum(-1);
                    count++;
                }
            }

        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                canvas.drawLine(0, topMargin + i * itemWidth, itemWidth * 9, topMargin + i * itemWidth, linePaintDark);
                canvas.drawLine(0, topMargin + i * itemWidth + 5, itemWidth * 9, topMargin + i * itemWidth + 5, linePaintHit);
                canvas.drawLine(i * itemWidth + 5, topMargin + 0, i * itemWidth + 5, topMargin + 9 * itemWidth, linePaintHit);
                canvas.drawLine(i * itemWidth, topMargin + 0, i * itemWidth, topMargin + 9 * itemWidth, linePaintDark);
            }

            canvas.drawLine(0, topMargin + i * itemWidth, itemWidth * 9, topMargin + i * itemWidth, linePaintLight);
            canvas.drawLine(0, topMargin + i * itemWidth + 5, itemWidth * 9, topMargin + i * itemWidth + 5, linePaintHit);
            canvas.drawLine(i * itemWidth, topMargin + 0, i * itemWidth, topMargin + 9 * itemWidth, linePaintLight);
            canvas.drawLine(i * itemWidth + 5, topMargin + 0, i * itemWidth + 5, topMargin + 9 * itemWidth, linePaintHit);
        }
        if (shudu != null) {
            for (int i = 0; i < shudu.length; i++) {
                for (int j = 0; j < shudu[i].length; j++) {
                    if (i == currentXIndex && j == currentYIndex && shudu[i][j].isCheckable()) {
                        canvas.drawRect(i * itemWidth, j * itemWidth + topMargin, (i + 1) * itemWidth, (j + 1) * itemWidth + topMargin, rectPaint);
                    }
                }
            }
        }
        drawText(canvas);
        if (unCheckCount == 0 && conflictX == -1 && conflictY == -1 && shudu != null) {
            onGameFinish.gameFinish();
            touchAble = false;
        }
        super.onDraw(canvas);
    }


    public void drawText(Canvas canvas) {
        unCheckCount = 0;
        if (shudu != null) {
            for (int i = 0; i < shudu.length; i++) {
                for (int j = 0; j < shudu[i].length; j++) {
                    if (shudu[i][j].getNum() != -1) {
                        if (i == conflictX && j == conflictY) {
                            canvas.drawText(shudu[i][j].getNum() + "", itemWidth / 2 + i * itemWidth, topMargin + textBaseLine + j * itemWidth, linePaintConflict);
                        } else if (conflictX != -1 && conflictY != -1 && i == currentXIndex && j == currentYIndex) {
                            canvas.drawText(shudu[i][j].getNum() + "", itemWidth / 2 + i * itemWidth, topMargin + textBaseLine + j * itemWidth, linePaintConflict);
                        } else if (shudu[i][j].isCheckable()) {
                            canvas.drawText(shudu[i][j].getNum() + "", itemWidth / 2 + i * itemWidth, topMargin + textBaseLine + j * itemWidth, numPaint);
                        } else {
                            canvas.drawText(shudu[i][j].getNum() + "", itemWidth / 2 + i * itemWidth, topMargin + textBaseLine + j * itemWidth, nomalPaint);
                        }

                    } else {
                        unCheckCount++;
                    }
                }
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchAble) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int xTem;
                int yTem;
                xTem = x / itemWidth;
                yTem = (y - topMargin) / itemWidth;
                if (shudu != null) {
                    if (shudu[xTem][yTem].isCheckable()) {
                        currentYIndex = yTem;
                        currentXIndex = xTem;
                    }
                }
                invalidate();
                return true;
            }
        } else {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void setText(int num) {
        if (currentXIndex != -1 && currentYIndex != -1) {
            checkConflict(num);
            shudu[currentXIndex][currentYIndex].setNum(num);
            invalidate();
        }

    }

    public void reStart() {
        initShudu();
        postInvalidate();
    }

    public void playAgain() {
        if(shudu!=null){
            for (int i = 0; i < shudu.length; i++) {
                for (int j = 0; j < shudu[i].length; j++) {
                    if (shudu[i][j].isCheckable()) {
                        shudu[i][j].setNum(-1);
                    }
                }
            }
        }
        currentXIndex = -1;
        currentYIndex = -1;
        conflictY = -1;
        conflictX = -1;
        touchAble = true;
        postInvalidate();
    }

    public void checkConflict(int num) {
        for (int i = 0; i < 9; i++) {
            if (shudu[currentXIndex][i].getNum() == num && i != currentYIndex) {
                conflictX = currentXIndex;
                conflictY = i;
                touchAble = false;
                return;
            }
            if (shudu[i][currentYIndex].getNum() == num && i != currentXIndex) {
                conflictX = i;
                conflictY = currentYIndex;
                touchAble = false;
                return;
            }
            conflictY = -1;
            conflictY = -1;
            touchAble = true;
        }
    }


    public interface OnGameFinish {
        public void gameFinish();
    }

}
