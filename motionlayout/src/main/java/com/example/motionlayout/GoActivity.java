package com.example.motionlayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;

/**
 * 通过 TransitionManager.go() 实现布局替换的过渡动画
 */
public class GoActivity extends AppCompatActivity implements View.OnClickListener {
    private Boolean toggle = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
        bindData();
    }

    @Override
    public void onClick(View v) {
        Scene startScene = Scene.getSceneForLayout((ViewGroup) v.getParent(), R.layout.go_start, this);
        Scene endScene = Scene.getSceneForLayout((ViewGroup) v.getParent(), R.layout.go_end, this);
        if (toggle) {
            TransitionManager.go(endScene);
        } else {
            TransitionManager.go(startScene);
        }

        bindData();
        toggle = !toggle;
    }

    private void bindData() {
        findViewById(R.id.image_film_cover).setOnClickListener(this);
        ((RatingBar) findViewById(R.id.rating_film_rating)).setRating(4.5f);
        ((TextView) findViewById(R.id.text_film_title)).setText(getString(R.string.film_title));
        ((TextView) findViewById(R.id.text_film_description)).setText(getString(R.string.film_description));
    }
}