package com.example.animation;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animation.view.CameraView;
import com.example.animation.view.CircleView;
import com.example.animation.view.PointView;

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
//        CircleView view = findViewById(R.id.view);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(50f), Utils.dp2px(150f));
//        animator.setStartDelay(1000);
//        animator.start();

        // AnimatorSet
//        CameraView view = findViewById(R.id.view);
//
//        ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 60f);
//        bottomFlipAnimator.setStartDelay(1000);
//        bottomFlipAnimator.setDuration(1000);
//
//        ObjectAnimator flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f);
//        flipRotationAnimator.setStartDelay(200);
//        flipRotationAnimator.setDuration(1000);
//
//        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", - 60f);
//        topFlipAnimator.setStartDelay(200);
//        // 动画持续时间
//        topFlipAnimator.setDuration(1000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator);
//        animatorSet.start();

        // PropertyValuesHolder
//        CameraView view = findViewById(R.id.view);
//        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f);
//        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f);
//        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", - 60f);
//
//        ObjectAnimator holderAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlipHolder, flipRotationHolder, topFlipHolder);
//        holderAnimator.setStartDelay(1000);
//        holderAnimator.setDuration(2000);
//        holderAnimator.start();

        // Keyframe
//        float length = Utils.dp2px(200f);
//        // fraction 参数为动画完成比率，百分比，多个 keyframe 的 fraction 值顺序应该是由 0 ~ 1
//        // value 则没有限制
//        Keyframe keyframe1 = Keyframe.ofFloat(0f, 0f);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 0.4f * length);
//        Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
//        Keyframe keyframe4 = Keyframe.ofFloat(1f, 1f * length);
//
//        PropertyValuesHolder keyframeHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
//
//        ImageView view = findViewById(R.id.view);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, keyframeHolder);
//        animator.setStartDelay(1000);
//        animator.setDuration(2000);
//        animator.start();

        // Evaluator
        PointView view = findViewById(R.id.view);
        ObjectAnimator animator = ObjectAnimator.ofObject(view, "point", new PointFEvaluator(), new PointF(Utils.dp2px(100f), Utils.dp2px(200f)));
        animator.setStartDelay(1000);
        animator.setDuration(2000);
        animator.start();
    }

    // 自定义估值器
    static class PointFEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float startX = startValue.x;
            float endX = endValue.x;
            float currentX = startX + (endX - startX) * fraction;

            float startY = startValue.y;
            float endY = endValue.y;
            float currentY = startY + (endY - startY) * fraction;

            return new PointF(currentX, currentY);
        }
    }
}