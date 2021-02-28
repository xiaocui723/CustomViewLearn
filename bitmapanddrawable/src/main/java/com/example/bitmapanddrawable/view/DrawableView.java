package com.example.bitmapanddrawable.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bitmapanddrawable.drawable.MeshDrawable;

public class DrawableView extends View {
//    private ColorDrawable drawable = new ColorDrawable(Color.RED);
    private MeshDrawable drawable = new MeshDrawable();

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }
}
