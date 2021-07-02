package com.example.constraintlayout.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentBase2Binding;

import org.jetbrains.annotations.NotNull;

public class Base2Fragment extends Fragment {
    private FragmentBase2Binding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBase2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
