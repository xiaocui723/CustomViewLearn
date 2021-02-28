package com.example.bitmapanddrawable.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bitmapanddrawable.Utils;

public class MeshDrawable extends Drawable {
    private static final float INTERVAL = Utils.dp2px(50f);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MeshDrawable() {
        paint.setColor(Color.parseColor("#F9A825"));
        paint.setStrokeWidth(Utils.dp2px(5f));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float x = (float) getBounds().left;
        while (x <= getBounds().right) {
            canvas.drawLine(x, getBounds().top, x, getBounds().bottom, paint);
            x += INTERVAL;
        }

        float y = (float) getBounds().top;
        while (y <= getBounds().bottom) {
            canvas.drawLine(getBounds().left, y, getBounds().right, y, paint);
            y += INTERVAL;
        }
    }

    /**
     * 设置 Drawable 整体的透明度
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public int getAlpha() {
        return paint.getAlpha();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Nullable
    @Override
    public ColorFilter getColorFilter() {
        return paint.getColorFilter();
    }

    /**
     * 不透明度
     * 影响融合
     * @return
     */
    @Override
    public int getOpacity() {
        if (paint.getAlpha() == 0) {
            return PixelFormat.TRANSPARENT;
        } else if (paint.getAlpha() == 0xff) {
            return PixelFormat.OPAQUE;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
