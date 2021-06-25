package com.example.drag.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 使用 OnDragListener 实现子 View 拖拽
 */
public class DragListenerGridView extends ViewGroup {
    private final static int COLUMNS = 2;
    private final static int ROWS = 3;

    private OnDragListener dragListener = new MyDragListener();
    private View draggedView;
    private ArrayList<View> orderedChildren = new ArrayList<>();

    public DragListenerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            orderedChildren.add(child);
            // 所有子 View 长按时触发拖拽
            child.setOnLongClickListener(v -> {
                draggedView = v;
                // 拖拽触发
                // data 参数为要通过拖拽方式传输的数据，在特定阶段才能获取到，一般是 drop 阶段
                // shadowBuilder 参数为拖拽时的 View 的样式，使用 DragShadowBuilder 为默认样式
                // myLocalState 参数为本地数据，随时可以通过 DragEvent.getLocalState() 获取
                // flags 参数为拖拽标记，传 0 即可
                v.startDrag(null, new DragShadowBuilder(v), v, 0);
                return false;
            });
            // 为所有子 View 添加拖拽监听
            child.setOnDragListener(dragListener);
        }
    }

    /**
     * 拖拽事件可通过自行创建 OnDragListener 并调用 setOnDragListener 设置，
     * 亦可通过 onDragEvent() 监听
     *
     * @param event
     * @return
     */
    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int i = 0; i < getChildCount(); i++) {
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            View child = getChildAt(i);
            // 所有子 View 都放在 0，0 位置
            child.layout(0, 0, childWidth, childHeight);
            // 再通过偏移调整其位置
            child.setTranslationX((float) childLeft);
            child.setTranslationY((float) childTop);
        }
    }

    /**
     * 对子 View 进行排序
     *
     * @param targetView
     */
    private void sort(View targetView) {
        int draggedIndex = -1;
        int targetIndex = -1;
        for (int i = 0; i < orderedChildren.size(); i++) {
            if (targetView == orderedChildren.get(i)) {
                targetIndex = i;
            } else if (draggedView == orderedChildren.get(i)) {
                draggedIndex = i;
            }
        }

        orderedChildren.remove(draggedIndex);
        orderedChildren.add(targetIndex, draggedView);

        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int i = 0; i < orderedChildren.size(); i++) {
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            orderedChildren.get(i).animate()
                    .translationX(childLeft)
                    .translationY(childTop)
                    .setDuration(150);
        }
    }

    /**
     * 拖拽监听
     */
    private class MyDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // 表示拖拽开始
                    if (event.getLocalState() == v) {
                        v.setVisibility(View.INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // 表示拖拽行为已经再本窗口中进行
                    if (event.getLocalState() != v) {
                        sort(v);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    // 表示拖拽行为已离开本窗口
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // 表示拖拽事件结束
                    if (event.getLocalState() == v) {
                        v.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }
    }
}
