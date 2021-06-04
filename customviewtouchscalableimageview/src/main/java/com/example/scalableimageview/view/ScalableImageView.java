package com.example.scalableimageview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import com.example.scalableimageview.Utils;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private final static int IMAGE_SIZE = (int) Utils.dp2px(300f);
    private final static float EXTRA_SCALE_FACTOR = 2f;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = Utils.getAvatar(getResources(), IMAGE_SIZE);
    private float originalOffsetX = 0f;
    private float originalOffsetY = 0f;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float smallScale = 0f;
    private float bigScale = 0f;
    private boolean big = false;
    private float scaleFraction = 0f;
    private GestureDetectorCompat gestureDetector = new GestureDetectorCompat(getContext(), this);
    private ObjectAnimator animator;
    private OverScroller scroller = new OverScroller(getContext());

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        animator = ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f);
    }

    private void setScaleFraction(float value) {
        scaleFraction = value;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (float) (getWidth() - IMAGE_SIZE) / 2;
        originalOffsetY = (float) (getHeight() - IMAGE_SIZE) / 2;

        // 计算宽高比
        // 获得大图与小图的缩放比例
        if ((float) bitmap.getWidth() / bitmap.getHeight() >
                (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * EXTRA_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * EXTRA_SCALE_FACTOR;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
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

    private void fixOffset() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (big) {
            scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    (int) -(bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int) -(bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int) Utils.dp2px(40), (int) Utils.dp2px(40));

            ViewCompat.postOnAnimation(this, this);
        }
        return false;
    }

    @Override
    public void run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.getCurrX();
            offsetY = scroller.getCurrY();
            invalidate();
            ViewCompat.postOnAnimation(this, this);
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        big = !big;
        if (big) {
            offsetX = (e.getX() - getWidth() / 2f) * (1 - bigScale / smallScale);
            offsetY = (e.getY() - getHeight() / 2f) * (1 - bigScale / smallScale);
            fixOffset();
            animator.start();
        } else {
            animator.reverse();
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
