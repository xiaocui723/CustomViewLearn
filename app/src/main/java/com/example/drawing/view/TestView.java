package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestView extends View {
    private final float RADIUS = Utils.dp2px(100f);
    // Paint.ANTI_ALIAS_FLAG 自动抗锯齿
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addCircle(getWidth() / 2f, getHeight() / 2f, RADIUS, Path.Direction.CW);
        path.addRect(getWidth() / 2f - RADIUS, getHeight() / 2f, getWidth() / 2f + RADIUS, getHeight() / 2f + 2 * RADIUS, Path.Direction.CW);
        path.addCircle(getWidth() / 2f, getHeight() / 2f, RADIUS * 1.5f, Path.Direction.CCW);
        path.setFillType(Path.FillType.EVEN_ODD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
