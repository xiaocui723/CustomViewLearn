package com.example.xfermode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.xfermode.R;
import com.example.xfermode.Utils;

public class AvatarView extends View {
    private static final float IMAGE_WIDTH = Utils.dp2px(200f);
    private static final float IMAGE_PADDING = Utils.dp2px(20f);
    private static final PorterDuffXfermode XFERMODE = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF bounds = new RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH);

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 使用离屏缓冲，bounds 参数用于设置离屏缓冲的大小
        int count = canvas.saveLayer(bounds, null);

        canvas.drawOval(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint);

        paint.setXfermode(XFERMODE);

        canvas.drawBitmap(getAvatar((int) IMAGE_WIDTH), IMAGE_PADDING, IMAGE_PADDING, paint);

        paint.setStrokeWidth(Utils.dp2px(10f));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint);

        paint.setXfermode(null);
        // 恢复离屏缓冲
        canvas.restoreToCount(count);
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
