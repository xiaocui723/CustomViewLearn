package com.example.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.animation.Utils;

public class ProvinceView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(Utils.dp2px(100f));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("北京市", getWidth() / 2f, getHeight() / 2f, paint);
    }
}
