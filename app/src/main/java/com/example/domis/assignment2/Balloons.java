package com.example.domis.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

class Balloons extends View {

    private Paint piePaint;
    private RectF rectF;
    private int[] data;
    private Random rnd;


    public Balloons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        piePaint.setColor(0x0050ff);

    }

    public void setData(int[] data)
    {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        if (data != null)
        {
            rnd = new Random();

            int top = 196;
            int left = (getWidth() / 4) + 64;
            int endBottom = getHeight() - 64;
            int endRight = getHeight() + 64;
            rectF = new RectF(left, top, endRight, endBottom);

            //float[] segment = pieSegment();
            float segStartPoint = 0;

            /**
            for(int i = 0; i < segment.length; i++)
            {
                int color = Color.argb(255, (int) segment[i], rnd.nextInt(256), rnd.nextInt(256));
                piePaint.setColor(color);
                canvas.drawArc(rectF, segStartPoint, segment[i], true, piePaint);
                canvas.drawCircle(data[0], data[1], data[2], piePaint);
                segStartPoint += segment[i];
            }
             */
        }
    }

}

