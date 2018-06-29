package com.smartgnan.widgets;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.smartgnan.helpers.Helper;

public class PointerWidget extends BaseWidget {

    int x;
    int y;
    char c;
    final int width = 14;
    final int height = 21;

    public PointerWidget(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public void RenderWidget(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Helper.ColorBlue);
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x+width/2, y+height/3);
        path.lineTo(x+width/2, y + height);
        path.lineTo(x-width/2, y+height);
        path.lineTo(x-width/2, y+height/3);
        path.close();
        //paint.setStrokeWidth(3);
        //paint.setPathEffect(null);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("" + c, x, y+height-2, paint);

    }

    @Override
    public void Animate() {

    }
}
