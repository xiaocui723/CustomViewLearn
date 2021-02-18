package com.example.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPropertyAnimator
//        ImageView view = findViewById(R.id.view);
//        view.animate()
//                .translationX(Utils.dp2px(200f))
//                .translationY(Utils.dp2px(100f))
//                .alpha(0.5f)
//                .scaleX(2f)
//                .scaleY(2f)
//                .setStartDelay(1000);

        // ObjectAnimator
        CircleView view = findViewById(R.id.view);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(150f));
        animator.setStartDelay(1000);
        animator.start();
    }
}