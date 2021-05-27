package com.example.viewrootimpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        createPopup();

        Button btnShowPopup = findViewById(R.id.btn_show_popup);
        btnShowPopup.setOnClickListener(v -> popupWindow.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM, 0, 0));

        Button btnShowToast = findViewById(R.id.btn_show_toast);
        btnShowToast.setOnClickListener(v -> Toast.makeText(this, "this is a msg", Toast.LENGTH_SHORT).show());

        Button btnShowDialog = findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("this a Dialog");
            dialogBuilder.setNegativeButton("ok", (dialog, which) -> dialog.dismiss());
            dialogBuilder.show();
        });
    }

    private void createPopup() {
        View popupDetail = LayoutInflater.from(this).inflate(R.layout.popup_detail, null);
        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(popupDetail);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btn1 = popupDetail.findViewById(R.id.btn1);
        btn1.setOnClickListener(v1 -> popupWindow.dismiss());
        Button btn2 = popupDetail.findViewById(R.id.btn2);
        btn2.setOnClickListener(v2 -> popupWindow.dismiss());
        Button btn3 = popupDetail.findViewById(R.id.btn3);
        btn3.setOnClickListener(v3 -> popupWindow.dismiss());
    }
}