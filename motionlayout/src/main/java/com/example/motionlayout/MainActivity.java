package com.example.motionlayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motionlayout.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.motionlayout.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayout linearLayout = binding.getRoot();

        try {
            ActivityInfo[] activities = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities;
            for (ActivityInfo activity : activities) {
                if (activity.name.equals(this.getClass().getName())) {
                    continue;
                }

                Class clazz = Class.forName(activity.name);
                Button button = new Button(this);
                button.setAllCaps(false);
                button.setText(clazz.getSimpleName());
                button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, clazz)));
                linearLayout.addView(button);
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}