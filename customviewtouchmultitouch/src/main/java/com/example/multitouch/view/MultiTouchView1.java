package com.example.multitouch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.multitouch.R;
import com.example.multitouch.Utils;

public class MultiTouchView1 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = Utils.getAvatar(getResources(), R.drawable.avatar_bq, (int) Utils.dp2px(200f));

    private float originalOffsetX = 0f;
    private float originalOffsetY = 0f;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float downX = 0f;
    private float downY = 0f;
    private int trackingPointerId = 0;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    /**
     * 接力型实现核心在于 trackingPointerId 变量，
     * 当有手指按下时该变量都会是最后按下的那根手指的 Id。
     * 在 Move 事件发生时 trackingPointerId 对应的手指拥有控制权。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = event.getX(index) - downX + originalOffsetX;
                offsetY = event.getY(index) - downY + originalOffsetY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(newIndex);
                    downY = event.getY(newIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
