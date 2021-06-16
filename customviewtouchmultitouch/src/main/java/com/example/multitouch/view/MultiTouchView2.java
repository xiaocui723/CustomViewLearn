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

public class MultiTouchView2 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = Utils.getAvatar(getResources(), R.drawable.avatar_bq, (int) Utils.dp2px(200f));

    private float originalOffsetX = 0f;
    private float originalOffsetY = 0f;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float downX = 0f;
    private float downY = 0f;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float focusX;
        float focusY;
        int pointerCount = event.getPointerCount();
        float sumX = 0f;
        float sumY = 0f;
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;
        for (int i = 0; i < pointerCount; i++) {
            if (!(isPointerUp && i == event.getActionIndex())) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }
        if (isPointerUp) {
            pointerCount--;
        }
        focusX = sumX / pointerCount;
        focusY = sumY / pointerCount;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                downX = focusX;
                downY = focusY;
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = focusX - downX + originalOffsetX;
                offsetY = focusY - downY + originalOffsetY;
                invalidate();
                break;
        }
        return true;
    }
}
