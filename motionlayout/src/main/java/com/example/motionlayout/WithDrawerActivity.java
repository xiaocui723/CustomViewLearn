package com.example.motionlayout;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.motionlayout.databinding.ActivityWithDrawerBinding;

/**
 * MotionLayout 搭配 DrawerLayout 使用
 */
public class WithDrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWithDrawerBinding binding = ActivityWithDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getRoot().addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                binding.motionLayout.setProgress(slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}