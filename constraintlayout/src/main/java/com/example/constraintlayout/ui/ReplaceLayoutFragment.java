package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionManager;

import com.example.constraintlayout.R;
import com.example.constraintlayout.databinding.FragmentReplaceLayoutStartBinding;

public class ReplaceLayoutFragment extends Fragment {
    private FragmentReplaceLayoutStartBinding binding;
    private boolean switched = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReplaceLayoutStartBinding.inflate(inflater, container, false);
        binding.getRoot().setOnClickListener(v -> {
            ConstraintLayout constraintLayout = binding.getRoot();

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.setForceId(false);
            // 通过 ConstraintSet 替换布局
            constraintSet.clone(getContext(), switched ? R.layout.fragment_replace_layout_start : R.layout.fragment_replace_layout_end);
            switched = !switched;

            // 过度动画
            TransitionManager.beginDelayedTransition(constraintLayout);
            constraintSet.applyTo(constraintLayout);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
