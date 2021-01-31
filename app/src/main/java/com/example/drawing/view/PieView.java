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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PieView extends View {
    private static final float RADIUS = Utils.dp2px(150f);
    private static final float[] ANGLES = new float[]{60f, 90f, 150f, 60f};
    private static final int[] COLORS = new int[]{Color.parseColor("#1976D2"), Color.parseColor("#009688"), Color.parseColor("#FFC107"), Color.parseColor("#7C4DFF")};

    private float radius = Utils.dp2px(100f);

    // Paint.ANTI_ALIAS_FLAG 自动抗锯齿
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 画扇形
        float startAngle = 0f;
        for (int i = 0; i < ANGLES.length; i++) {
            paint.setColor(COLORS[i]);
            canvas.drawArc(getWidth() / 2f - Utils.dp2px(150), getHeight() / 2f - Utils.dp2px(150), getWidth() / 2f + Utils.dp2px(150), getHeight() / 2f + Utils.dp2px(150), startAngle, ANGLES[i], true, paint);
            startAngle += ANGLES[i];
        }

    }
}
