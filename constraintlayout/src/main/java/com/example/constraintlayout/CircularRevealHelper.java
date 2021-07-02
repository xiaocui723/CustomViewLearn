package com.example.constraintlayout;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 实现圆形显示动画
 */
public class CircularRevealHelper extends ConstraintHelper {
    public CircularRevealHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void updatePostLayout(ConstraintLayout container) {
        super.updatePostLayout(container);

        int[] ids = getReferencedIds();
        for (int id : ids) {
            View view = container.findViewById(id);
            float radius = (float) Math.hypot(view.getWidth(), view.getHeight());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ViewAnimationUtils.createCircularReveal(view, 0, 0, 0f, radius).setDuration(2000L).start();
            }
        }
    }
}
