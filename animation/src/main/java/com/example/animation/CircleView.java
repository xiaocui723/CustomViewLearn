package com.example.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius = Utils.dp2px(50f);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.parseColor("#00796B"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, paint);
    }

    // 使用 ObjectAnimator 的属性需要提供 set 方法
    public void setRadius(float radius) {
        this.radius = radius;
        // 值被改变后让 View 被重绘
        invalidate();
    }
}
