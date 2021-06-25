package com.example.drag.view;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.example.drag.R;

/**
 * 使用 OnDragListener 实现拖拽数据传输
 */
public class DragToCollectLayout extends ConstraintLayout {
    private OnLongClickListener dragStarter = v -> {
        // 需要传输的数据
        ClipData imageData = ClipData.newPlainText("name", v.getContentDescription());
        ViewCompat.startDragAndDrop(v, imageData, new DragShadowBuilder(v), null, 0);
        return false;
    };

    private OnDragListener dragListener = new CollectListener();

    public DragToCollectLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.avatarView).setOnLongClickListener(dragStarter);
        findViewById(R.id.logoView).setOnLongClickListener(dragStarter);
        findViewById(R.id.collectorLayout).setOnDragListener(dragListener);
    }

    private class CollectListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            // 表示拖拽在本窗口中被释放
            if (event.getAction() == DragEvent.ACTION_DROP) {
                if (v instanceof LinearLayout) {
                    TextView textView = new TextView(getContext());
                    textView.setTextSize(16f);
                    // 数据获取并使用
                    textView.setText(event.getClipData().getItemAt(0).getText());
                    ((LinearLayout) v).addView(textView);
                }
            }
            return true;
        }
    }
}
