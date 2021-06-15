package com.example.scalableimageview.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import com.example.scalableimageview.R;
import com.example.scalableimageview.Utils;

public class ScalableImageView extends View {
    private final static int IMAGE_SIZE = (int) Utils.dp2px(300f);
    private final static float EXTRA_SCALE_FACTOR = 2f;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap = Utils.getAvatar(getResources(), R.drawable.avatar_bq, IMAGE_SIZE);
    private float originalOffsetX = 0f;
    private float originalOffsetY = 0f;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float smallScale = 0f;
    private float bigScale = 0f;
    private boolean big = false;
    private float currentScale = 0f;
    private final MyRunnable myRunnable = new MyRunnable();
    private final MyGestureListener myGestureListener = new MyGestureListener();
    private final MyScaleGestureListener myScaleGestureListener = new MyScaleGestureListener();
    private final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(getContext(), myGestureListener);
    private final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), myScaleGestureListener);
    private final ObjectAnimator animator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale);
    private final OverScroller scroller = new OverScroller(getContext());

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void setCurrentScale(float value) {
        currentScale = value;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        // 计算宽高比
        // 获得大图与小图的缩放比例
        if ((float) (bitmap.getWidth() / bitmap.getHeight()) >
                (float) (getWidth() / getHeight())) {
            smallScale = (float) (getWidth() / bitmap.getWidth());
            bigScale = (float) (getHeight() / bitmap.getHeight()) * EXTRA_SCALE_FACTOR;
        } else {
            smallScale = (float) (getHeight() / bitmap.getHeight());
            bigScale = (float) (getWidth() / bitmap.getWidth()) * EXTRA_SCALE_FACTOR;
        }

        currentScale = smallScale;
        animator.setFloatValues(smallScale, bigScale);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        // 当双指缩放事件触发时，双击手势侦测器暂停侦测手势
        if (!scaleGestureDetector.isInProgress()) {
            gestureDetector.onTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 偏移量影响系数实际为缩放系数进度百分比
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);

        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    /**
     * 偏移量限定值修正
     */
    private void fixOffset() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (big) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffset();
                invalidate();
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (big) {
                // 惯性滑动开始计算
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        (int) (-(bitmap.getWidth() * bigScale - getWidth()) / 2),
                        (int) ((bitmap.getWidth() * bigScale - getWidth()) / 2),
                        (int) (-(bitmap.getHeight() * bigScale - getHeight()) / 2),
                        (int) ((bitmap.getHeight() * bigScale - getHeight()) / 2),
                        (int) Utils.dp2px(40), (int) Utils.dp2px(40));

                // 下一帧刷新时更新惯性动画
                ViewCompat.postOnAnimation(ScalableImageView.this, myRunnable);
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                // 双击放大获得跟手偏移量
                offsetX = (e.getX() - getWidth() / 2f) * (1 - bigScale / smallScale);
                offsetY = (e.getY() - getHeight() / 2f) * (1 - bigScale / smallScale);
                fixOffset();
                animator.start();
            } else {
                animator.reverse();
            }
            return true;
        }
    }

    private class MyScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // 通过双指缩放系数计算得到当前图片放缩系数
            float tempCurrentScale = currentScale * detector.getScaleFactor();
            if (tempCurrentScale < smallScale || tempCurrentScale > bigScale) {
                return false;
            } else {
                currentScale *= detector.getScaleFactor();
                invalidate();
                return true;
            }
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // 双指缩放跟手偏移量
            offsetX = (detector.getFocusX() - getWidth() / 2f) * (1 - bigScale / smallScale);
            offsetY = (detector.getFocusY() - getHeight() / 2f) * (1 - bigScale / smallScale);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            // 惯性滑动进行时计算图片偏移量
            if (scroller.computeScrollOffset()) {
                // 惯性滑动即时偏移量
                offsetX = (float) scroller.getCurrX();
                offsetY = (float) scroller.getCurrY();
                invalidate();
                ViewCompat.postOnAnimation(ScalableImageView.this, this);
            }
        }
    }
}
