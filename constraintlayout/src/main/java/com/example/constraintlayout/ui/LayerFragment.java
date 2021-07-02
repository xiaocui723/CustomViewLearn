package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;

import com.example.constraintlayout.databinding.FragmentLayerBinding;

public class LayerFragment extends Fragment {
    private FragmentLayerBinding binding;
    private boolean isChanged = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLayerBinding.inflate(inflater, container, false);
        Layer layer = binding.layer;
        binding.button.setOnClickListener(v -> {
            if (!isChanged) {
                layer.setRotation(45f);
                layer.setTranslationX(100f);
                layer.setTranslationY(100f);
            } else {
                layer.setRotation(0f);
                layer.setTranslationX(0f);
                layer.setTranslationY(0f);
            }

            isChanged = !isChanged;
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
