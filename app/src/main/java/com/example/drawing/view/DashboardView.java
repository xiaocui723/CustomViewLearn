package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DashboardView extends View {
    // 仪表盘开口度数
    private static final float OPEN_ANGLE = 120f;
    // 刻度宽度
    private static final float DASH_WIDTH = Utils.dp2px(2f);
    // 刻度长度
    private static final float DASH_LENGTH = Utils.dp2px(10f);

    private float radius = Utils.dp2px(100f);

    // Paint.ANTI_ALIAS_FLAG 自动抗锯齿
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path dash = new Path();

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 线条宽度
        paint.setStrokeWidth(Utils.dp2px(3f));
        // 画线模式
        paint.setStyle(Paint.Style.STROKE);
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 画弧
        canvas.drawArc(getWidth() / 2f - Utils.dp2px(150), getHeight() / 2f - Utils.dp2px(150), getWidth() / 2f + Utils.dp2px(150), getHeight() / 2f + Utils.dp2px(150), 90 + OPEN_ANGLE / 2f, 360 - OPEN_ANGLE, false, paint);

        // 画刻度
        paint.setPathEffect(new PathDashPathEffect(dash, 50f, 0f, PathDashPathEffect.Style.ROTATE));
        canvas.drawArc(getWidth() / 2f - Utils.dp2px(150), getHeight() / 2f - Utils.dp2px(150), getWidth() / 2f + Utils.dp2px(150), getHeight() / 2f + Utils.dp2px(150), 90 + OPEN_ANGLE / 2f, 360 - OPEN_ANGLE, false, paint);
        paint.setPathEffect(null);
    }
}
