package com.smartgnan.widgets;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

public class LineWidget extends BaseWidget {

    PointF p1;
    PointF p2;
    boolean isFrontArrow;
    boolean isBackArrow;

    PointF old_p1;
    PointF old_p2;

    public LineWidget(float x1, float y1, float x2, float y2, boolean isFrontArrow, boolean isBackArrow) {
        this.p1 = new PointF(x1, y1);
        this.p2 = new PointF(x2, y2);
        this.isFrontArrow = isFrontArrow;
        this.isBackArrow = isBackArrow;

        this.old_p1 = new PointF(x1, y1);
        this.old_p2 = new PointF(x2, y2);
    }

    public LineWidget(LineWidget that) {
        this.p1 = new PointF(that.p1.x, that.p1.y);
        this.p2 = new PointF(that.p2.x, that.p2.y);
        this.isFrontArrow = that.isFrontArrow;
        this.isBackArrow = that.isBackArrow;

        this.old_p1 = new PointF(that.old_p1.x, that.old_p1.y);
        this.old_p2 = new PointF(that.old_p2.x, old_p2.y);
    }


    @Override
    public void RenderWidget(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
    }

    @Override
    public void Animate() {

    }
}
