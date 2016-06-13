package com.pzh.yiqiplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pzh.yiqiplay.R;

/**
 * Created by pzh on 16/6/12.
 */
public class DrawView extends View {
    private Paint mPaint;
    private Path path;
    private int minX=0,maxX=0;
    private int minY=0,maxY=0;
    private Context context;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setStrokeWidth(6);
        mPaint.setColor(getResources().getColor(R.color.green));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        int y= (int) event.getY();
        if(x<minX){
            minX=x;
        }
        if(x>maxX)
            maxX=x;
        if(y>maxY)
            maxY=y;
        if(y<minY)
            minY=y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(x,y);
                System.out.println("pzh down");
                return false;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                System.out.println("pzh move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("pzh up");
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("pzh ondraw");
        if (path != null) {
            canvas.drawPath(path, mPaint);
        }
        super.onDraw(canvas);
    }

    public void getBitmap(){

    }
}
