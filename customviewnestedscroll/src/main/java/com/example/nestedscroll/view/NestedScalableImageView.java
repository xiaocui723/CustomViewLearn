package com.example.nestedscroll.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

import com.example.nestedscroll.R;
import com.example.nestedscroll.Utils;

/**
 * 通过实现 NestedScrollingChild 接口解决嵌套滑动的问题。
 */
public class NestedScalableImageView extends View implements NestedScrollingChild3 {
    private static final int IMAGE_SIZE = (int) Utils.dp2px(300f);
    private static final float EXTRA_SCALE_FACTOR = 1.5f;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = Utils.getAvatar(getResources(), R.drawable.avatar_bq, IMAGE_SIZE);

    private float originalOffsetX = 0f;
    private float originalOffsetY = 0f;

    private float offsetX = 0f;
    private float offsetY = 0f;

    private float smallScale = 0f;
    private float bigScale = 0f;

    private MyGestureListener myGestureListener = new MyGestureListener();
    private MyScaleGestureListener myScaleGestureListener = new MyScaleGestureListener();
    private MyFlingRunner myFlingRunner = new MyFlingRunner();

    private GestureDetectorCompat gestureDetector = new GestureDetectorCompat(getContext(), myGestureListener);
    private ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), myScaleGestureListener);

    private boolean big = false;

    private float currentScale = 0f;

    private ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale);

    private OverScroller scroller = new OverScroller(getContext());

    private NestedScrollingChildHelper childHelper = new NestedScrollingChildHelper(this);

    public NestedScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        childHelper.setNestedScrollingEnabled(true);
    }

    private void setCurrentScale(float value) {
        currentScale = value;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - IMAGE_SIZE) / 2f;
        originalOffsetY = (getHeight() - IMAGE_SIZE) / 2f;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * EXTRA_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * EXTRA_SCALE_FACTOR;
        }

        currentScale = smallScale;
        scaleAnimator.setFloatValues(smallScale, bigScale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            gestureDetector.onTouchEvent(event);
            childHelper.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    private int fixOffsets() {
        float rawOffsetY = offsetY;
        float maxOffsetY = (bitmap.getHeight() * bigScale - getHeight()) / 2;
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, maxOffsetY);
        offsetY = Math.max(offsetY, -maxOffsetY);

        if (rawOffsetY < -maxOffsetY) {
            return (int) (-maxOffsetY - rawOffsetY);
        }

        if (rawOffsetY > maxOffsetY) {
            return (int) (maxOffsetY - rawOffsetY);
        }
        return 0;
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return childHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        childHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return childHelper.hasNestedScrollingParent(type);
    }

    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (big) {
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        (int) (-(bitmap.getWidth() * bigScale - getWidth()) / 2),
                        (int) ((bitmap.getWidth() * bigScale - getWidth()) / 2),
                        (int) (-(bitmap.getHeight() * bigScale - getHeight()) / 2),
                        (int) ((bitmap.getHeight() * bigScale - getHeight()) / 2));
                ViewCompat.postOnAnimation(NestedScalableImageView.this, myFlingRunner);
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (big) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                int unconsumed = fixOffsets();
                if (unconsumed != 0) {
                    childHelper.dispatchNestedScroll(0, 0, 0, unconsumed, null);
                } else {
                    invalidate();
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                offsetX = (e.getX() - getWidth() / 2f) * (1 - bigScale / smallScale);
                offsetY = (e.getY() - getHeight() / 2f) * (1 - bigScale / smallScale);
                fixOffsets();
                scaleAnimator.start();
            } else {
                scaleAnimator.reverse();
            }
            return true;
        }
    }

    private class MyScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            offsetX = (detector.getFocusX() - getWidth() / 2f) * (1 - bigScale / smallScale);
            offsetY = (detector.getFocusY() - getHeight() / 2f) * (1 - bigScale / smallScale);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float tempCurrentScale = currentScale * detector.getScaleFactor();
            if (tempCurrentScale < smallScale || tempCurrentScale > bigScale) {
                return false;
            } else {
                currentScale *= detector.getScaleFactor();
                return true;
            }
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    private class MyFlingRunner implements Runnable {

        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                offsetX = (float) scroller.getCurrX();
                offsetY = (float) scroller.getCurrY();
                invalidate();
                ViewCompat.postOnAnimation(NestedScalableImageView.this, this);
            }
        }
    }
}
