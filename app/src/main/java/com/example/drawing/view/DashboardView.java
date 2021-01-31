package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DashboardView extends View {
    // 仪表盘开口度数
    private static final float OPEN_ANGLE = 120f;
    private static final float RADIUS = Utils.dp2px(150f);
    private static final float LENGTH = Utils.dp2px(120f);
    // 刻度宽度
    private static final float DASH_WIDTH = Utils.dp2px(2f);
    // 刻度长度
    private static final float DASH_LENGTH = Utils.dp2px(10f);

    private final float radius = Utils.dp2px(100f);

    // Paint.ANTI_ALIAS_FLAG 自动抗锯齿
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private final Path dash = new Path();
    private PathDashPathEffect pathEffect;

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 线条宽度
        paint.setStrokeWidth(Utils.dp2px(3f));
        // 画线模式
        paint.setStyle(Paint.Style.STROKE);
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        path.reset();
        path.addArc(getWidth() / 2f - Utils.dp2px(150), getHeight() / 2f - Utils.dp2px(150), getWidth() / 2f + Utils.dp2px(150), getHeight() / 2f + Utils.dp2px(150), 90 + OPEN_ANGLE / 2f, 360 - OPEN_ANGLE);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        // 刻度位置与间隔
        pathEffect = new PathDashPathEffect(dash, (pathMeasure.getLength() - DASH_WIDTH) / 20f, 0f, PathDashPathEffect.Style.ROTATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 画弧
        canvas.drawPath(path, paint);

        // 画刻度
        paint.setPathEffect(pathEffect);
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);

        // 正弦
        float cos = (float) Math.cos(markToRadians(8));
        // 余弦
        float sin = (float) Math.sin(markToRadians(8));
        // 画指针
        canvas.drawLine(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f + LENGTH * cos, getHeight() / 2f + LENGTH * sin, paint);
    }

    private double markToRadians(int mark) {
        return Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * mark));
    }
}
