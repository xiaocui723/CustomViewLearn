package com.example.motionlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motionlayout.databinding.ActivityMotionBinding;

/**
 * MotionLayout 使用示例
 */
public class MotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMotionBinding binding = ActivityMotionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ratingFilmRating.setRating(4.5f);
        binding.textFilmTitle.setText(getString(R.string.film_title));
        binding.textFilmDescription.setText(getString(R.string.film_description));
    }
}