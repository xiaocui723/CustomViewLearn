package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentBarrierBinding;

import java.util.Random;

public class BarrierFragment extends Fragment {
    private FragmentBarrierBinding binding;
    private String[] texts = {"长文本", "长文本长文本", "长文本长文本长文本"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBarrierBinding.inflate(inflater, container, false);
        Random random = new Random();
        binding.getRoot().setOnClickListener(v -> {
            binding.text1.setText(texts[random.nextInt(3)]);
            binding.text2.setText(texts[random.nextInt(3)]);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
