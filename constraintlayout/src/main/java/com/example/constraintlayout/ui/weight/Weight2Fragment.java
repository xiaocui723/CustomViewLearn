package com.example.constraintlayout.ui.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentWeight2Binding;

public class Weight2Fragment extends Fragment {
    private FragmentWeight2Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeight2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
