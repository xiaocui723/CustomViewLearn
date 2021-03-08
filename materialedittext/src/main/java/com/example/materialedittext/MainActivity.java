package com.example.materialedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.materialedittext.view.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MaterialEditText editText = findViewById(R.id.met);
//        editText.postDelayed(() -> {
//            editText.setUseFloatingLabel(false);
//        }, 3000);
    }
}