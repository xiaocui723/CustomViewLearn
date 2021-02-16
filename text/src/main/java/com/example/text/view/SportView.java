package com.example.text.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

import com.example.text.R;
import com.example.text.Utils;

public class SportView extends View {
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    private static final float RING_WIDTH = Utils.dp2px(20f);
    private static final float RADIUS = Utils.dp2px(150f);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect bounds = new Rect();
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, RADIUS, paint);

        // 绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS, -90f, 225f, false, paint);

        // 绘制文字
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(Utils.dp2px(100f));
        // 设置字体
        paint.setTypeface(ResourcesCompat.getFont(getContext(), R.font.font));
        // 设置假粗体
        paint.setFakeBoldText(false);
        // 字体对齐方式
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getTextBounds("abap", 0, "abap".length(), bounds);
        paint.getFontMetrics(fontMetrics);
        canvas.drawText("abap", getWidth() / 2f, getHeight() / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f, paint);

        // 绘制文字 Rect 基线
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2f));
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawLine(getWidth() / 2f - bounds.right / 2f, getHeight() / 2f - (bounds.top + bounds.bottom) / 2f, getWidth() / 2f + bounds.right / 2f, getHeight() / 2f - (bounds.top + bounds.bottom) / 2f, paint);

        // 绘制文字 Rect 左基线
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2f));
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawLine(getWidth() / 2f - bounds.right / 2f, getHeight() / 2f - bounds.bottom, getWidth() / 2f - bounds.right / 2f, getHeight() / 2f - (bounds.top + bounds.bottom) / 2f, paint);

        // 绘制文字 Rect 右基线
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2f));
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawLine(getWidth() / 2f + bounds.right / 2f, getHeight() / 2f - bounds.bottom, getWidth() / 2f + bounds.right / 2f, getHeight() / 2f - (bounds.top + bounds.bottom) / 2f, paint);

        // 绘制文字 Rect 顶基线
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2f));
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawLine(getWidth() / 2f - bounds.right / 2f, getHeight() / 2f - bounds.bottom, getWidth() / 2f + bounds.right / 2f, getHeight() / 2f - bounds.bottom, paint);

        // 绘制文字贴边
        paint.setTextSize(Utils.dp2px(150f));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds("abap", 0, "abap".length(), bounds);
        paint.getFontMetrics(fontMetrics);
        canvas.drawText("abap", - bounds.left, - fontMetrics.top, paint);

        paint.setTextSize(Utils.dp2px(15f));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds("abap", 0, "abap".length(), bounds);
        paint.getFontMetrics(fontMetrics);
        canvas.drawText("abap", - bounds.left, - fontMetrics.top, paint);
    }
}
