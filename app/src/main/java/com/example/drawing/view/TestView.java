package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class TestView extends View {
    private final static float RADIUS = 200f;

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // Paint.ANTI_ALIAS_FLAG 自动抗锯齿
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(100f, 100f, 200f, 200f, paint);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, Utils.dp2px(100f), paint);
    }
}
