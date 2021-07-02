package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentGoneMarginBinding;

public class GoneMarginFragment extends Fragment {
    private FragmentGoneMarginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGoneMarginBinding.inflate(inflater, container, false);
        binding.getRoot().setOnClickListener(v -> binding.text.setVisibility(binding.text.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
