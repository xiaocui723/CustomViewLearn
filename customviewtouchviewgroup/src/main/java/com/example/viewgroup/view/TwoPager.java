package com.example.viewgroup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

public class TwoPager extends ViewGroup {
    private float downX = 0f;
    private float downY = 0f;
    private float downScrollX = 0f;
    private boolean scrolling = false;
    private OverScroller overScroller = new OverScroller(getContext());
    private ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
    private VelocityTracker velocityTracker = VelocityTracker.obtain();
    private int minVelocity = viewConfiguration.getScaledMinimumFlingVelocity(); // 手指滑动速率最小值
    private int maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity(); // 手指滑动速率最大值
    private int pagingSlop = viewConfiguration.getScaledPagingTouchSlop(); // 分页滚动手势滑动阈值

    public TwoPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childTop = 0;
        int childRight = getWidth();
        int childBottom = getHeight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childLeft, childTop, childRight, childBottom);
            childLeft += getWidth();
            childRight += getHeight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(ev); // 跟踪事件

        boolean result = false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 记录初始数据
                scrolling = false;
                downX = ev.getX();
                downY = ev.getY();
                downScrollX = (float) getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 判断手指滑动是否达到滑动阈值，
                // 达到阈值就拦截后续事件
                if (!scrolling) {
                    float dx = downX - ev.getX();
                    if (Math.abs(dx) > pagingSlop) {
                        scrolling = true;
                        // 告诉父 View 不要拦截本次触碰事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                        result = true;
                    }
                }
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downScrollX = (float) getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (downX - event.getX() + downScrollX);
                dx = Math.max(dx, 0);
                dx = Math.min(dx, getWidth());
                scrollTo(dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                // 计算手指滑动速度
                velocityTracker.computeCurrentVelocity(1000, (float) maxVelocity);
                float vx = velocityTracker.getXVelocity();
                float scrollX = getScrollX();
                int targetPage;
                // 滑动速度快慢
                if (Math.abs(vx) < minVelocity) {
                    // 速度不够，判断滑动距离是不是大于 View 宽度的一半，
                    // 大于就表示要滚动到第二页面
                    if (scrollX > getWidth() / 2f) {
                        targetPage = 1;
                    } else {
                        targetPage = 0;
                    }
                } else {
                    // 速度够大，判断滑动的方向，负值为左滑，表示滚动到第二页
                    if (vx < 0) {
                        targetPage = 1;
                    } else {
                        targetPage = 0;
                    }
                }

                float scrollDistance;
                if (targetPage == 1) {
                    // 计算滚动到第二页的滚动距离
                    scrollDistance = getWidth() - getScrollX();
                } else {
                    scrollDistance = -getScrollX();
                }
                overScroller.startScroll(getScrollX(), 0, (int) scrollDistance, 0);
                postInvalidateOnAnimation();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        // scrollTo 属于瞬时操作，因此需要借助 overScroller 计算滚动过程中的偏移值
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.getCurrX(), overScroller.getCurrY());
            postInvalidateOnAnimation();
        }
    }
}
