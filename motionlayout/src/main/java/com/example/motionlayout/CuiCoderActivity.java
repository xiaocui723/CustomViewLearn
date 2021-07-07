package com.example.motionlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.motionlayout.databinding.ActivityCuiCoderBinding;

/**
 * MotionLayout 特性示例 2
 */
public class CuiCoderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCuiCoderBinding binding = ActivityCuiCoderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.showDebug.setOnClickListener(v -> binding.getRoot().setDebugMode(MotionLayout.DEBUG_SHOW_PATH));
    }
}