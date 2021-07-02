package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentGroupBinding;

public class GroupFragment extends Fragment {
    private FragmentGroupBinding binding;
    private boolean isVisible = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupBinding.inflate(inflater, container, false);
        binding.button.setOnClickListener(v -> {
            binding.group.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            isVisible = !isVisible;
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
