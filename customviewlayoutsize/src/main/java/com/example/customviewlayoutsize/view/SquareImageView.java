package com.example.customviewlayoutsize.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 正确示范
        // Measure 阶段使用 getMeasuredWidth()、getMeasuredHeight() 获取 View 的最新宽高参数
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        // 错误示范
        // 在 layout 中修改尺寸，父 View 无法感知，
        // 可能导致父 View 布局出错。
        int width = r - l;
        int height = b - t;
        int size = Math.min(width, height);
        super.layout(l, t, l + size, t + size);
    }
}
