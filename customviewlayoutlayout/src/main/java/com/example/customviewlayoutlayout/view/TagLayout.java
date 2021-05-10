package com.example.customviewlayoutlayout.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TagLayout extends ViewGroup {
    // 所有子 View 的位置及尺寸
    private ArrayList<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int lineWidthUsed = 0;
        int lineMaxHeight = 0;
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            // 子 View 尺寸要求官方通用计算方法
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);

            // 计算折行
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.getMeasuredWidth() > specWidthSize) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            // 保存子 View 位置及尺寸
            if (i >= childrenBounds.size()) {
                childrenBounds.add(new Rect());
            }
            Rect childBounds = childrenBounds.get(i);
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());

            // 已用空间更新
            lineWidthUsed += child.getMeasuredWidth();
            // 总宽度更新
            widthUsed = Math.max(lineWidthUsed, widthUsed);
            // 行高更新
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        // 计算自己的尺寸
        int selfWidth = widthUsed;
        int selfHeight = heightUsed + lineMaxHeight;
        setMeasuredDimension(selfWidth, selfHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = childrenBounds.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
