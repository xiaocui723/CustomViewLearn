package com.example.drag.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * 使用 ViewDragHelper 实现 View 拖拽功能
 */
public class DragHelperGridView extends ViewGroup {
    private final static String TAG = "DragHelperGridView";
    private final static int COLUMNS = 2;
    private final static int ROWS = 3;

    /**
     * 通过 create 获得 ViewDragHelper 实例
     */
    private ViewDragHelper dragHelper = ViewDragHelper.create(this, new DragCllback());
    private View releasedChild;

    public DragHelperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;
        // 测量阶段直接给子 View 固定尺寸
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        // 布局从左往右放置子 View，每行 2 个子 View，共 3 行
        for (int i = 0; i < getChildCount(); i++) {
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            getChildAt(i).layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 由 ViewDragHelper 负责侦测拦截触碰事件
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 由 ViewDragHelper 负责侦测触碰事件
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        // 被拖拽子 View 是否在还原中
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            // 被拖拽子 View 被还原，高度 - 1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && releasedChild != null) {
                releasedChild.setElevation(getElevation());
            }
            releasedChild = null;
        }
    }

    /**
     * 拖拽监听回调
     */
    private class DragCllback extends ViewDragHelper.Callback {
        private float capturedLeft = 0f;
        private float capturedTop = 0f;

        /**
         * 尝试捕获触碰到的子 View
         *
         * @param child     手指触碰到的子 View
         * @param pointerId 对应的手指 ID，应该是处理多点触碰会用到
         * @return 返回 true，表示允许捕获，否则不捕获
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            // 如果之前拖拽的子 View 还没回归原位则不允许拖拽其他子 View，防止还原不成功
            return releasedChild == null;
        }

        /**
         * 水平方向移动限制，返回 0 时，即不允许水平方向移动，
         * 返回其他数值即为水平方向偏移值，View 将会在拖拽时，
         * 根据其返回的水平方向偏移值进行偏移。
         * 如需水平方向自由游动，直接 <code>return left</code>。
         *
         * @param child 被拖拽的子 View
         * @param left  水平方向偏移值
         * @param dx    与前一刻拖拽时水平方向偏移值的差值
         * @return 允许水平方向偏移的偏移量
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            // 不限制水平方向移动
            return left;
        }

        /**
         * 垂直方向偏移限制与水平方向偏移限制相同，
         * 允许垂直方向自由移动，直接 <code>retrun top</code>
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            // 不限制垂直方向移动
            return top;
        }

        /**
         * 当子 View 捕获成功时回调
         *
         * @param capturedChild   被捕获可拖拽的子 View
         * @param activePointerId 触碰到子 View 的手指的 ID
         */
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 拖拽时高度 + 1
                capturedChild.setElevation(getElevation() + 1);
            }
            // 记录拖拽时的位置，用于还原
            capturedLeft = (float) capturedChild.getLeft();
            capturedTop = (float) capturedChild.getTop();
        }

        /**
         * 子 View 被拖拽时位置发生改变回调
         *
         * @param changedView 被拖拽子 View
         * @param left        水平方向偏移值
         * @param top         垂直方向偏移值
         * @param dx          与上一刻水平方向偏移值的差值
         * @param dy          与上一刻垂直方向偏移值的差值
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
        }

        /**
         * 当被拖拽的子 View 被释放时回调，
         * 也就是拖拽子 View 的手指抬起时。
         *
         * @param releasedChild 被释放的子 View
         * @param xvel          释放时的水平方向偏移值
         * @param yvel          释放时的垂直方向偏移值
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            // 保存被释放的子 View，在还原结束时置空，在拖拽新子 View 时用于判断，不为空时不允许拖拽
            DragHelperGridView.this.releasedChild = releasedChild;
            // 还原位置
            dragHelper.settleCapturedViewAt((int) capturedLeft, (int) capturedTop);
            postInvalidateOnAnimation();
        }
    }
}
