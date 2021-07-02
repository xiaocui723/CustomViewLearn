package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentPlaceholderBinding;

public class PlaceholderFragment extends Fragment implements View.OnClickListener {
    private FragmentPlaceholderBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlaceholderBinding.inflate(inflater, container, false);
        binding.favorite.setOnClickListener(this);
        binding.mail.setOnClickListener(this);
        binding.play.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        binding.placeholder.setContentId(v.getId());
    }
}
