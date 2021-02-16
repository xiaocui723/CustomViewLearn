package com.example.text.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.text.R;
import com.example.text.Utils;

public class MultilineTextView extends View {
    private static final float IMAGE_SIZE = Utils.dp2px(150f);
    private static final float IMAGE_PADDING = Utils.dp2px(50f);
    private String text = "Phasellus purus nulla, porta ut finibus dapibus, elementum quis est. Aliquam sit amet lectus id magna aliquam tincidunt. Integer pretium nibh neque, nec iaculis nulla fringilla id. Nullam eget arcu tincidunt, vestibulum arcu quis, ornare ex. Sed augue tellus, rhoncus at metus tristique, interdum auctor metus. Quisque at suscipit sapien. Suspendisse potenti. Fusce in ultricies felis, a aliquet turpis. Nulla vel magna tincidunt, fringilla lectus sit amet, finibus nibh. Quisque varius, est eget ullamcorper placerat, nunc metus mollis risus, eu ultricies ante eros sit amet sem. Nunc imperdiet rhoncus tellus, at pretium dolor facilisis quis. Cras ac tellus vitae felis malesuada vestibulum. Quisque ornare mollis ultricies. Praesent hendrerit elit at sagittis tincidunt. Vivamus consequat dui tristique sem rhoncus aliquam. Etiam dignissim dictum erat ut vulputate.";

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = getAvatar((int) IMAGE_SIZE);
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public MultilineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(Utils.dp2px(16f));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getWidth() - IMAGE_SIZE, IMAGE_PADDING, paint);
        paint.getFontMetrics(fontMetrics);
        float[] measuredWidth = new float[0];
        int start = 0;
        int count;
        float verticalOffset = -fontMetrics.top;
        while (start < text.length()) {
            float width = getWidth();
            if (verticalOffset + fontMetrics.bottom > IMAGE_PADDING && verticalOffset + fontMetrics.top < IMAGE_SIZE + IMAGE_PADDING) {
                width -= IMAGE_SIZE;
            }
            count = paint.breakText(text, start, text.length(), true, width, measuredWidth);
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint);
            start += count;
            verticalOffset += paint.getFontSpacing();
        }
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
