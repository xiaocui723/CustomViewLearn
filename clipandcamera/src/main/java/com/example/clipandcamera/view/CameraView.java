package com.example.clipandcamera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.clipandcamera.R;
import com.example.clipandcamera.Utils;

public class CameraView extends View {
    private static final float BITMAP_SIZE = Utils.dp2px(200f);
    private static final float BITMAP_PADDING = Utils.dp2px(100f);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap = getAvatar((int) BITMAP_SIZE);

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_bq, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_bq, options);
    }
}
