package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

    // Paint.ANTI_ALIAS_FLAG 自动抗锯齿
    private final Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path arcPath = new Path();
    private final Path dashPath = new Path();
    private final Path textPath = new Path();
    private PathDashPathEffect pathEffect;

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 线条宽度
        arcPaint.setStrokeWidth(Utils.dp2px(3f));
        // 画线模式
        arcPaint.setStyle(Paint.Style.STROKE);
        dashPath.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW);

        textPaint.setTextSize(Utils.dp2px(16f));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        arcPath.reset();
        arcPath.addArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS, 90 + OPEN_ANGLE / 2f, 360 - OPEN_ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arcPath, false);
        // 刻度位置与间隔
        pathEffect = new PathDashPathEffect(dashPath, (pathMeasure.getLength() - DASH_WIDTH) / 20f, 0f, PathDashPathEffect.Style.ROTATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 画弧
        canvas.drawPath(arcPath, arcPaint);

        // 画刻度
        arcPaint.setColor(Color.parseColor("#FF5733"));
        arcPaint.setPathEffect(pathEffect);
        canvas.drawPath(arcPath, arcPaint);
        arcPaint.setPathEffect(null);
        arcPaint.setColor(Color.BLACK);

        // 画刻度对应数字
        float textLength = textPaint.measureText("20");
        textPath.addArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS, (float) (90 + OPEN_ANGLE / 2f - Math.cos(textLength / 2f)), (float) (360 - OPEN_ANGLE + Math.sin(textLength)));
        PathMeasure pathMeasure = new PathMeasure(textPath, false);
        float arcLength = pathMeasure.getLength();
        float textAdvance = (arcLength - textLength) / 20f;
        float hOffset = 0f;
        for (int i = 0; i <= 20; i++) {
            canvas.drawTextOnPath(String.valueOf(i), textPath, hOffset, -Utils.dp2px(8f), textPaint);
            hOffset += textAdvance;
        }

        // 正弦
        float cos = (float) Math.cos(markToRadians(8));
        // 余弦
        float sin = (float) Math.sin(markToRadians(8));
        // 画指针
        canvas.drawLine(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f + LENGTH * cos, getHeight() / 2f + LENGTH * sin, arcPaint);
    }

    private double markToRadians(int mark) {
        return Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * mark));
    }
}
