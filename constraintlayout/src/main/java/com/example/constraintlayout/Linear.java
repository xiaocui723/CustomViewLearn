package com.example.constraintlayout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.VirtualLayout;

public class Linear extends VirtualLayout {
    private ConstraintSet constraintSet = new ConstraintSet();

    public Linear(Context context, AttributeSet attrs) {
        super(context, attrs);
        constraintSet.setForceId(false);
    }

    /**
     * 更新布局前调用
     *
     * @param container 约束布局
     */
    @Override
    public void updatePreLayout(ConstraintLayout container) {
        super.updatePreLayout(container);

        constraintSet.clone(container);

        int[] ids = getReferencedIds();

        for (int i = 1; i < ids.length; i++) {
            int current = ids[i];
            int before = ids[i - 1];

            constraintSet.connect(current, ConstraintSet.START, before, ConstraintSet.START);
            constraintSet.connect(current, ConstraintSet.TOP, before, ConstraintSet.BOTTOM);
            constraintSet.applyTo(container);
        }
    }
}
