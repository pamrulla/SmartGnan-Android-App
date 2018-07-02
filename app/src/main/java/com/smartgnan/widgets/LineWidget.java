package com.smartgnan.widgets;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;

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
        this.old_p2 = new PointF(that.old_p2.x, that.old_p2.y);
        this.color = that.color;
        this.isUpdated = that.isUpdated;
    }


    @Override
    public void RenderWidget(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);

        if(isFrontArrow) {
            int offset = 5;
            Path path = new Path();
            path.moveTo(p2.x, p2.y);
            path.lineTo(p2.x - offset, p2.y + offset);
            path.lineTo(p2.x - offset, p2.y - offset);
            path.close();
            paint.setStyle(Paint.Style.FILL);
            Matrix mMatrix = new Matrix();
            RectF bounds = new RectF();
            path.computeBounds(bounds, true);
            double theta = Math.atan2(p2.y - p1.y, p2.x - p1.x);
            mMatrix.postRotate(((float) Math.toDegrees(theta)), bounds.centerX(), bounds.centerY());
            path.transform(mMatrix);
            canvas.drawPath(path, paint);

        }
    }

    @Override
    public void Animate() {
        if(this.isUpdated) {
            PropertyValuesHolder x1 = PropertyValuesHolder.ofFloat("LINE_X1", old_p1.x, p1.x);
            PropertyValuesHolder y1 = PropertyValuesHolder.ofFloat("LINE_Y1", old_p1.y, p1.y);
            PropertyValuesHolder x2 = PropertyValuesHolder.ofFloat("LINE_X2", old_p2.x, p2.x);
            PropertyValuesHolder y2 = PropertyValuesHolder.ofFloat("LINE_Y2", old_p2.y, p2.y);

            final ValueAnimator animator = new ValueAnimator();
            animator.setValues(x1, y1, x2, y2);
            animator.setDuration(1000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    p1.x = (float) valueAnimator.getAnimatedValue("LINE_X1");
                    p1.y = (float) valueAnimator.getAnimatedValue("LINE_Y1");
                    p2.x = (float) valueAnimator.getAnimatedValue("LINE_X2");
                    p2.y = (float) valueAnimator.getAnimatedValue("LINE_Y2");
                }
            });

            animator.start();
        }
    }

    public void UpdateNewBounds(int x1, int y1, int x2, int y2) {
        this.old_p1 = new PointF(p1.x, p1.y);
        this.old_p2 = new PointF(p2.x, p2.y);
        this.p1 = new PointF(x1, y1);
        this.p2 = new PointF(x2, y2);
        this.isUpdated = true;
    }
}
