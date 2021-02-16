package com.example.text.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.text.Utils;

public class MultilineTextView extends View {
    private String text = "Phasellus purus nulla, porta ut finibus dapibus, elementum quis est. Aliquam sit amet lectus id magna aliquam tincidunt. Integer pretium nibh neque, nec iaculis nulla fringilla id. Nullam eget arcu tincidunt, vestibulum arcu quis, ornare ex. Sed augue tellus, rhoncus at metus tristique, interdum auctor metus. Quisque at suscipit sapien. Suspendisse potenti. Fusce in ultricies felis, a aliquet turpis. Nulla vel magna tincidunt, fringilla lectus sit amet, finibus nibh. Quisque varius, est eget ullamcorper placerat, nunc metus mollis risus, eu ultricies ante eros sit amet sem. Nunc imperdiet rhoncus tellus, at pretium dolor facilisis quis. Cras ac tellus vitae felis malesuada vestibulum. Quisque ornare mollis ultricies. Praesent hendrerit elit at sagittis tincidunt. Vivamus consequat dui tristique sem rhoncus aliquam. Etiam dignissim dictum erat ut vulputate.";

    private StaticLayout staticLayout;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    public MultilineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textPaint.setTextSize(Utils.dp2px(16f));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        staticLayout = new StaticLayout(text, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        staticLayout.draw(canvas);
    }
}
