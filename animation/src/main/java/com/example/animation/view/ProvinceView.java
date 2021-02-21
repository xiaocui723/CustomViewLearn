package com.example.animation.view;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.animation.Utils;

import java.util.Arrays;
import java.util.List;

public class ProvinceView extends View {
    private static final List<String> provinces = Arrays.asList(
            "北京市",
            "天津市",
            "上海市",
            "重庆市",
            "河北省",
            "山西省",
            "辽宁省",
            "吉林省",
            "黑龙江省",
            "江苏省",
            "浙江省",
            "安徽省",
            "福建省",
            "江西省",
            "山东省",
            "河南省",
            "湖北省",
            "湖南省",
            "广东省",
            "海南省",
            "四川省",
            "贵州省",
            "云南省",
            "陕西省",
            "甘肃省",
            "青海省",
            "台湾省",
            "内蒙古自治区",
            "广西壮族自治区",
            "西藏自治区",
            "宁夏回族自治区",
            "新疆维吾尔自治区",
            "香港特别行政区",
            "澳门特别行政区"
    );
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String province = "北京市";

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(Utils.dp2px(50f));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(province, getWidth() / 2f, getHeight() / 2f, paint);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }

    public static class ProvinceEvaluator implements TypeEvaluator<String> {

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int startIndex = provinces.indexOf(startValue);
            int endIndex = provinces.indexOf(endValue);
            int currentIndex = (int) (startIndex + (endIndex - startIndex) * fraction);
            return provinces.get(currentIndex);
        }
    }
}
