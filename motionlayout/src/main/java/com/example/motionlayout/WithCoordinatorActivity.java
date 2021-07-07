package com.example.motionlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motionlayout.databinding.ActivityWithCoordinatorBinding;

/**
 * MotionLayout 搭配 CoordinatorLayout 使用
 */
public class WithCoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWithCoordinatorBinding binding = ActivityWithCoordinatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> binding.motionLayout.setProgress(-verticalOffset / (float) appBarLayout.getTotalScrollRange()));
    }
}