package com.smartgnan.helpers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Helper {
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
