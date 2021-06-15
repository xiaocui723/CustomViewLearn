package com.example.multitouch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.multitouch.R;
import com.example.multitouch.Utils;

public class MultiTouchView1 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = Utils.getAvatar(getResources(), R.drawable.avatar_bq, (int) Utils.dp2px(200f));

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0f, 0f, paint);
    }
}
