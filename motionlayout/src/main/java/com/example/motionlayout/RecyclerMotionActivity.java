package com.example.motionlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motionlayout.adapter.RecyclerAdapter;
import com.example.motionlayout.databinding.ActivityRecyclerMotionBinding;

public class RecyclerMotionActivity extends AppCompatActivity {
    private ActivityRecyclerMotionBinding binding;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerMotionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recycle;
        recyclerAdapter = new RecyclerAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}