package com.example.motionlayout.adapter;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motionlayout.R;
import com.example.motionlayout.Utils;
import com.example.motionlayout.databinding.RecycleItemBinding;

/**
 * 通过 MotionLayout 实现 RecyclerView 的 Item 展开动画
 * TODO 屏幕外展开状态的 Item 还原处理
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public static final String TAG = "RecyclerAdapter";
    private int expandItemPosition = -1;
    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleItemBinding binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        holder.motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {
                holder.ivExpand.setClickable(false);

                if (expandItemPosition == -1 || expandItemPosition == position) {
                    return;
                }

                RecyclerAdapter.ViewHolder oldViewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(expandItemPosition);
                if (oldViewHolder != null) {
                    oldViewHolder.motionLayout.transitionToStart();
                }
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
                marginLayoutParams.setMargins(
                        (int) (Utils.dp2px(16f) - (Utils.dp2px(8) - Utils.dp2px(8) * (1f - v))),
                        (int) Utils.dp2px(8),
                        (int) (Utils.dp2px(16f) - (Utils.dp2px(8) - Utils.dp2px(8) * (1f - v))),
                        (int) Utils.dp2px(8));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.cardView.setElevation(Utils.dp2px(4f) * v);
                }
                holder.cardView.requestLayout();
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                holder.ivExpand.setClickable(true);

                if (i == motionLayout.getEndState()) {
                    expandItemPosition = position;
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MotionLayout motionLayout;
        public ImageView ivExpand;
        public CardView cardView;
        public View vDes2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            motionLayout = itemView.findViewById(R.id.motion_layout);
            ivExpand = itemView.findViewById(R.id.iv_expand);
            cardView = itemView.findViewById(R.id.card);
            vDes2 = itemView.findViewById(R.id.v_des2);
        }
    }
}
