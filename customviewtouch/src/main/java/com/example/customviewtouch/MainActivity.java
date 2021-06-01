package com.example.customviewtouch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.view);
        view.setClickable(false);
        view.setOnClickListener(v -> Toast.makeText(this, "点击了！", Toast.LENGTH_SHORT).show());
    }
}