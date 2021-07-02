package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentDimensionRatioBinding;

public class DimensionRatioFragment extends Fragment {
    private FragmentDimensionRatioBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDimensionRatioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
