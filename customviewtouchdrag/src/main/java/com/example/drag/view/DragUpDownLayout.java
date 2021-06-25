package com.example.drag.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.drag.R;

/**
 * 使用 ViewDragHelper 实现一个纵向快滑布局
 */
public class DragUpDownLayout extends FrameLayout {
    private ViewDragHelper.Callback dragListener = new DragCallback();
    private ViewDragHelper dragHelper = ViewDragHelper.create(this, dragListener);
    private ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());

    public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == findViewById(R.id.draggedView);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            // 释放时根据速度计算子 View 应该还原的方向及偏移量
            if (Math.abs(yvel) > viewConfiguration.getScaledMinimumFlingVelocity()) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                } else {
                    dragHelper.settleCapturedViewAt(0, 0);
                }
            } else {
                if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) {
                    dragHelper.settleCapturedViewAt(0, 0);
                } else {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                }
            }
            postInvalidateOnAnimation();
        }
    }
}
