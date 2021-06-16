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

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = event.getX() - downX + originalOffsetX;
                offsetY = event.getY() - downY + originalOffsetY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
        }
        return true;
    }
}
