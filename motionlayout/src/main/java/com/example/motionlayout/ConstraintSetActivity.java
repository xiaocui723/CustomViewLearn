package com.example.motionlayout;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/**
 * 使用 ConstraintSet 更换布局，通过 TransitionManager.beginDelayedTransition() 实现更换过程的过渡动画
 */
public class ConstraintSetActivity extends AppCompatActivity implements View.OnClickListener {


    private Boolean toggle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_set_start);
        ((ImageView) findViewById(R.id.image_film_cover)).setOnClickListener(this);
        ((RatingBar) findViewById(R.id.rating_film_rating)).setRating(4.5f);
        ((TextView) findViewById(R.id.text_film_title)).setText(getString(R.string.film_title));
        ((TextView) findViewById(R.id.text_film_description)).setText(getString(R.string.film_description));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        ConstraintLayout root = (ConstraintLayout) v.getParent();
        TransitionManager.beginDelayedTransition(root);
        ConstraintSet constraintSet = new ConstraintSet();
        if (toggle) {
            constraintSet.clone(this, R.layout.activity_constraint_set_end);
        } else {
            constraintSet.clone(this, R.layout.activity_constraint_set_start);
        }
        constraintSet.applyTo(root);
        toggle = !toggle;
    }
}