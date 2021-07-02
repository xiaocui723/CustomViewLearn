package com.example.constraintlayout.ui;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.R;
import com.example.constraintlayout.databinding.FragmentFlowBinding;

public class FlowFragment extends Fragment implements View.OnClickListener {
    private FragmentFlowBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFlowBinding.inflate(inflater, container, false);
        binding.btnNone.setOnClickListener(this);
        binding.btnChain.setOnClickListener(this);
        binding.btnAligned.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(binding.getRoot());
        }

        int id = v.getId();
        if (id == R.id.btn_none) {
            binding.flow.setWrapMode(Flow.WRAP_NONE);
        } else if (id == R.id.btn_chain) {
            binding.flow.setWrapMode(Flow.WRAP_CHAIN);
        } else if (id == R.id.btn_aligned) {
            binding.flow.setWrapMode(Flow.WRAP_ALIGNED);
        }
    }
}
