package com.example.motionlayout;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.motionlayout.databinding.ActivityObjectAnimator2Binding;

/**
 * 修改 View 属性，通过 TransitionManager.beginDelayedTransition() 实现属性动画
 */
public class ObjectAnimator2Activity extends AppCompatActivity {
    private ActivityObjectAnimator2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityObjectAnimator2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick(View v) {
        TransitionManager.beginDelayedTransition((ViewGroup) v.getParent());

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutParams.height *= 2;
        layoutParams.width *= 2;

        v.requestLayout();
    }
}