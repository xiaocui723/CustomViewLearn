package com.example.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.animation.Utils;

public class PointView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PointF point = new PointF(0f, 0f);

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setStrokeWidth(Utils.dp2px(20f));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPoint(point.x, point.y, paint);
    }

    public void setPoint(PointF point) {
        this.point = point;
        invalidate();
    }

    public PointF getPoint() {
        return point;
    }
}
