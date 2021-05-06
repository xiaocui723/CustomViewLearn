package com.example.customviewlayoutsize.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customviewlayoutsize.Utils;

public class CircleView extends View {
    private final static Float RADIUS = Utils.dp2px(100f);
    private final static Float PADDING = Utils.dp2px(100f);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (int) ((PADDING + RADIUS) * 2);
        // 根据父 View 的规格参数对参数进行修正
        int width = resolveSize(size, widthMeasureSpec);
        int height = resolveSize(size, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint);
    }
}
