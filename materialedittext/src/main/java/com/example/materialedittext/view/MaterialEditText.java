package com.example.materialedittext.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.materialedittext.R;
import com.example.materialedittext.Utils;

public class MaterialEditText extends AppCompatEditText {
    private static final float TEXT_SIZE = Utils.dp2px(12);
    private static final float TEXT_MARGIN = Utils.dp2px(8);
    private static final float HORIZONTAL_OFFSET = Utils.dp2px(5);
    private static final float VERTICAL_OFFSET = Utils.dp2px(24);
    private static final float EXTRA_VERTICAL_OFFSET = Utils.dp2px(16);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean floatingLabelShown = false;
    private float floatingLabelFraction = 0f;

    private final ObjectAnimator animator;

    private boolean useFloatingLabel = false;

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        // 自定属性
        if (useFloatingLabel != this.useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel;
            if (useFloatingLabel) {
                setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
            } else {
                setPadding(getPaddingLeft(), (int) (getPaddingTop() - TEXT_SIZE - TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
            }
        }
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(TEXT_SIZE);

        // 获取 xml 中的自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        setUseFloatingLabel(typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true));
        typedArray.recycle();

        animator = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        // 动画方向
        if (useFloatingLabel) {
            if (floatingLabelShown && TextUtils.isEmpty(getText())) {
                floatingLabelShown = false;
                animator.reverse();
            } else if (!floatingLabelShown && !TextUtils.isEmpty(getText())) {
                floatingLabelShown = true;
                animator.start();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 根据动画完成度修改属性
        paint.setAlpha((int) (floatingLabelFraction * 0xff));
        float currentVertivalValue = VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(String.valueOf(getHint()), HORIZONTAL_OFFSET, currentVertivalValue, paint);
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }
}
