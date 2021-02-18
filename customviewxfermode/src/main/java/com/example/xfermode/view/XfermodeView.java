package com.example.xfermode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class XfermodeView extends View {
    private static final PorterDuffXfermode XFERMODE = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF bounds = new RectF(Utils.dp2px(150f), Utils.dp2px(50f), Utils.dp2px(300f), Utils.dp2px(200f));

    private Bitmap circleBitmap = Bitmap.createBitmap((int)Utils.dp2px(150f), (int)Utils.dp2px(150f), Bitmap.Config.ARGB_8888);
    private Bitmap squareBitmap = Bitmap.createBitmap((int)Utils.dp2px(150f), (int)Utils.dp2px(150f), Bitmap.Config.ARGB_8888);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 通过 canvas 给 bitmap 绘制内容
        Canvas canvas = new Canvas(circleBitmap);
        paint.setColor(Color.parseColor("#D81B60"));
        canvas.drawOval(Utils.dp2px(50f), Utils.dp2px(0f), Utils.dp2px(150f), Utils.dp2px(100f), paint);

        canvas.setBitmap(squareBitmap);
        paint.setColor(Color.parseColor("#2196F3"));
        canvas.drawRect(Utils.dp2px(0f), Utils.dp2px(50f), Utils.dp2px(100f), Utils.dp2px(150f), paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // 使用离屏缓冲，bounds 是离屏缓冲的大小
        int count = canvas.saveLayer(bounds, null);

        // 绘制目标图形
        canvas.drawBitmap(circleBitmap, Utils.dp2px(150f), Utils.dp2px(50f), paint);

        // 设置混合的模式
        paint.setXfermode(XFERMODE);

        // 绘制源图形
        canvas.drawBitmap(squareBitmap, Utils.dp2px(150f), Utils.dp2px(50f), paint);

        // 混合模式重置
        paint.setXfermode(null);

        // 离屏缓冲关闭，将内容放到 View 中
        canvas.restoreToCount(count);
    }
}
