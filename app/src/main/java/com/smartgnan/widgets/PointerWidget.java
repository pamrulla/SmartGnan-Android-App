package com.smartgnan.widgets;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.smartgnan.helpers.Helper;

public class PointerWidget extends BaseWidget {

    int x;
    int y;
    int old_x;
    int old_y;
    char c;
    final int width = 14;
    final int height = 21;

    public PointerWidget(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.old_x = x;
        this.old_y = y;
    }

    public PointerWidget(PointerWidget pointerWidget) {
        this.x = pointerWidget.x;
        this.y = pointerWidget.y;
        this.c = pointerWidget.c;
        this.isUpdated = pointerWidget.isUpdated;
        this.old_x = pointerWidget.old_x;
        this.old_y = pointerWidget.old_y;
    }

    public void UpdateLocation(int x, int y) {
        this.old_x = this.x;
        this.old_y = this.y;
        this.x = x;
        this.y = y;
        this.isUpdated = true;
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
        if(this.isUpdated) {
            PropertyValuesHolder rect_x = PropertyValuesHolder.ofInt("RECT_X", old_x, x);
            PropertyValuesHolder rect_y = PropertyValuesHolder.ofInt("RECT_Y", old_y, y);

            final ValueAnimator animator = new ValueAnimator();
            animator.setValues(rect_x, rect_y);
            animator.setDuration(1000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    x = (int) valueAnimator.getAnimatedValue("RECT_X");
                    y = (int) valueAnimator.getAnimatedValue("RECT_Y");
                }
            });

            animator.start();
        }
    }
}
