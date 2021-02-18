package com.example.animation;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView view = findViewById(R.id.view);
        view.animate()
                .translationX(Utils.dp2px(200f))
                .translationY(Utils.dp2px(100f))
                .alpha(0.5f)
                .scaleX(2f)
                .scaleY(2f)
                .setStartDelay(1000);
    }
}