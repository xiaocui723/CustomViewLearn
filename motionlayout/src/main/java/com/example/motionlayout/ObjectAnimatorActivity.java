package com.example.motionlayout;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.motionlayout.databinding.ActivityObjectAnimatorBinding;

/**
 * 修改 View 属性，通过 TransitionManager.beginDelayedTransition() 实现属性动画
 */
public class ObjectAnimatorActivity extends AppCompatActivity {
    private ActivityObjectAnimatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityObjectAnimatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick(View v) {
        TransitionManager.beginDelayedTransition(binding.getRoot());

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height *= 2;
        layoutParams.width *= 2;

        v.requestLayout();
    }
}