package com.example.clipandcamera.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.clipandcamera.R;
import com.example.clipandcamera.Utils;

public class CameraView extends View {
    private static final float BITMAP_SIZE = Utils.dp2px(200f);
    private static final float BITMAP_PADDING = Utils.dp2px(100f);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap = getAvatar((int) BITMAP_SIZE);
    private final Camera camera = new Camera();

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 以 X 轴旋转
        camera.rotateX(30f);
        // 设置 camera 镜头位置，位置单位为英寸，所以设置值需要做屏幕像素密度适配
        // 避免绘制翻转效果过于夸张
        camera.setLocation(0f, 0f, -6 * Resources.getSystem().getDisplayMetrics().density);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 上半部分
        canvas.save();
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
        canvas.clipRect(- BITMAP_SIZE / 2, - BITMAP_SIZE / 2, BITMAP_SIZE / 2, 0f);
        canvas.translate(- (BITMAP_PADDING + BITMAP_SIZE / 2), - (BITMAP_PADDING + BITMAP_SIZE / 2));
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
        canvas.restore();

        // 下半部分
        canvas.save();
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
        // 将 camera 应用到 canvas 中
        camera.applyToCanvas(canvas);
        canvas.clipRect(- BITMAP_SIZE / 2, 0f, BITMAP_SIZE / 2, BITMAP_SIZE /2);
        canvas.translate(- (BITMAP_PADDING + BITMAP_SIZE / 2), - (BITMAP_PADDING + BITMAP_SIZE / 2));
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
        canvas.restore();
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
