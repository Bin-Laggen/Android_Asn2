package com.example.domis.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BarChart extends View {

    private Paint paint;
    private float[] data;
    private RectF rectF;

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init()
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.CYAN);
    }

    public void setData(float[] data)
    {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        if (data != null)
        {
            int top = 85;
            int left = (getWidth() / 2) + 90;

            for(int i = 0; i < data.length; i++)
            {
                canvas.drawRect(left, top, left + (int) data[i], top + 66, paint);
                top += 74;
            }
        }
    }
}
