package com.example.customviewtouch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Collections;

public class TouchView extends View {
    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            // 当手指抬起，触发抬起事件，调用点击事件
            performClick();
        }
        return true;
    }
}
