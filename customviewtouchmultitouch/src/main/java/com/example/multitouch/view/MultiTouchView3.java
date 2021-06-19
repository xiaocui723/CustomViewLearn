package com.example.multitouch.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.multitouch.Utils;

public class MultiTouchView3 extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final SparseArray<Path> paths = new SparseArray<>();

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(4f));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.valueAt(i);
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 各自为战型实现核心在于需要为每个手指创建独立的操控数据，
     * 在 Move 事件中更新所有手指的操控数据。
     * 所有手指间的数据完全独立。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // 防止部分手机拦截多点触控，导致 up 没有触发的情况
                if (paths.size() > event.getPointerCount()) {
                    paths.clear();
                }
                int actionIndex = event.getActionIndex();
                Path path = new Path();
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex));
                paths.append(event.getPointerId(actionIndex), path);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < paths.size(); i++) {
                    int pointerId = event.getPointerId(i);
                    path = paths.get(pointerId);
                    path.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                paths.remove(pointerId);
                invalidate();
                break;
        }
        return true;
    }
}
