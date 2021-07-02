package com.example.constraintlayout.ui.constraintset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.R;
import com.example.constraintlayout.databinding.FragmentConstraintSetBinding;

public class ConstraintSetFragment extends Fragment {
    private FragmentConstraintSetBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConstraintSetBinding.inflate(inflater, container, false);
        binding.getRoot().setOnClickListener(v -> {
            ConstraintLayout constraintLayout = binding.getRoot();
            ConstraintSet constraintSet = new ConstraintSet();
            // 拷贝当前布局的约束设置，在此基础上修改约束参数
            constraintSet.clone(constraintLayout);
            // twitter 这个子 View 添加底边与父 View 底边的约束
            constraintSet.connect(R.id.twitter, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            // 应用到约束布局
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
