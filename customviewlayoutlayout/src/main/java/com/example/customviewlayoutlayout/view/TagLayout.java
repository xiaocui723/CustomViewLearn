package com.example.customviewlayoutlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TagLayout extends ViewGroup {
    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == 0) {
                child.layout(0, 0, (r - l) / 2, (b - t) / 2);
            } else if (i == 1) {
                child.layout((r - l) / 2, (b - t) / 2, r - l, b - t);
            }
        }
    }
}
