package com.smartgnan.widgets;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.smartgnan.helpers.Helper;

import java.util.ArrayList;

public class PathWidget extends BaseWidget {

    public ArrayList<Point> points;

    public PathWidget() {
        points = new ArrayList<>();
    }

    @Override
    public void RenderWidget(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Helper.ColorBlue);
        Path path = new Path();
        for(int i = 0; i < points.size()-1; i++) {
            path.moveTo(points.get(i).x, points.get(i).y);
            path.lineTo(points.get(i+1).x, points.get(i+1).y);
        }
        path.close();
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }

    @Override
    public void Animate() {

    }
}
