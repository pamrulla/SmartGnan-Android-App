package com.smartgnan.widgets;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.smartgnan.helpers.Helper;

public class CircleWidget extends BaseWidget {

    public int x;
    public int y;
    public String text;
    public int radius;
    public int old_x;
    public int old_y;

    public CircleWidget(int x, int y, int radius, String t) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.text = t;
    }

    public CircleWidget(CircleWidget copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.radius = copy.radius;
        this.text = copy.text;
        this.old_x = copy.old_x;
        this.old_y = copy.old_y;
        this.isUpdated = copy.isUpdated;
        this.color = copy.color;
    }

    public void UpdateNewBounds(int new_x, int new_y) {
        this.old_x = this.x;
        this.old_y = this.y;
        this.x = new_x;
        this.y = new_y;
        this.isUpdated = true;
    }

    @Override
    public void RenderWidget(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawCircle(x, y, radius, paint);

        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        Helper.setTextSizeForWidth(canvas, paint);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText(text,  x, y + bounds.height()/2, paint);
    }

    @Override
    public void Animate() {
        if(this.isUpdated) {
            PropertyValuesHolder circ_x = PropertyValuesHolder.ofInt("CIRC_X", old_x, x);
            PropertyValuesHolder circ_y = PropertyValuesHolder.ofInt("CIRC_Y", old_y, y);

            final ValueAnimator animator = new ValueAnimator();
            animator.setValues(circ_x, circ_y);
            animator.setDuration(1000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    x = (int) valueAnimator.getAnimatedValue("CIRC_X");
                    y = (int) valueAnimator.getAnimatedValue("CIRC_Y");
                }
            });

            animator.start();
        }
    }
}
