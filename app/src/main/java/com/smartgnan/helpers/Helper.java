package com.smartgnan.helpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;

public class Helper {

    public static final int ColorOrange = Color.rgb(255,165,0);
    public static final int ColorRed = Color.rgb(255,0,0);
    public static final int ColorGreen = Color.rgb(0,255,0);
    public static final int ColorBlue = Color.rgb(0, 0, 255);
    public static final int ColorLightBlue = Color.rgb(135,206,250);

    public static GradientDrawable purpleGradient = new GradientDrawable(GradientDrawable.Orientation.BL_TR,
            new int[] {0xffff6ec4, 0xff7873f5});
    public static GradientDrawable greenGradient = new GradientDrawable(GradientDrawable.Orientation.BL_TR,
            new int[] {0xffb2fc45, 0xff309f58});
    public static GradientDrawable blueGradient = new GradientDrawable(GradientDrawable.Orientation.BL_TR,
            new int[] {0xff45cafc, 0xff303f9f});
    public static GradientDrawable peachGradient = new GradientDrawable(GradientDrawable.Orientation.BL_TR,
            new int[] {0xffFFD86F, 0xffFC6262});

    public static void setTextSizeForWidth(Canvas canvas, Paint paint) { //Paint paint, String text, float desiredWidth) {
        double relation = Math.sqrt(canvas.getWidth() * canvas.getHeight());

        relation = relation / 250;

        paint.setTextSize((float)(10 * relation));
    }

    public static int minValue(ArrayList<Integer> dataSet) {
        int min = dataSet.get(0);
        for (int i: dataSet) {
            if(min > i) {
                min = i;
            }
        }
        return min;
    }

    public static int maxValue(ArrayList<Integer> dataSet) {
        int max = dataSet.get(0);
        for (int i: dataSet) {
            if(max < i) {
                max = i;
            }
        }
        return max;
    }
}
