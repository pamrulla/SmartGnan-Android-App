package com.smartgnan.widgets;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.smartgnan.helpers.BoxWidgetTextLocation;
import com.smartgnan.helpers.Helper;

public class BoxWidget extends BaseWidget {
    public float x;
    public float y;
    float width;
    float height;
    public String text;
    float old_x;
    float old_y;
    BoxWidgetTextLocation textLocation;

    public BoxWidget() {
        textLocation = BoxWidgetTextLocation.Bottom;
    }

    public BoxWidget(float x, float y, float width, float height, String t) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = t;
        textLocation = BoxWidgetTextLocation.Bottom;
    }

    public BoxWidget(float x, float y, float width, float height, String t, BoxWidgetTextLocation loc) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = t;
        textLocation = loc;
    }

    public BoxWidget(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = "";
        textLocation = BoxWidgetTextLocation.Bottom;
    }

    public BoxWidget(BoxWidget copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.width = copy.width;
        this.height = copy.height;
        this.text = copy.text;
        this.old_x = copy.old_x;
        this.old_y = copy.old_y;
        this.isUpdated = copy.isUpdated;
        this.color = copy.color;
        this.textLocation = copy.textLocation;
    }

    public void SetBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void UpdateNewBounds(float new_x, float new_y) {
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
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        Helper.setTextSizeForWidth(canvas, paint);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        if(textLocation == BoxWidgetTextLocation.Center) {
            canvas.drawText(text, x + (width / 2), y + height / 2 + bounds.height()/2, paint);
        }
        else if(textLocation == BoxWidgetTextLocation.Top) {
            canvas.drawText(text, x + (width / 2), y + 5, paint);
        }
        else {
            canvas.drawText(text, x + (width / 2), y + height - 5, paint);
        }
    }

    @Override
    public void Animate() {
        if(this.isUpdated) {
            PropertyValuesHolder rect_x = PropertyValuesHolder.ofFloat("RECT_X", old_x, x);
            PropertyValuesHolder rect_y = PropertyValuesHolder.ofFloat("RECT_Y", old_y, y);

            final ValueAnimator animator = new ValueAnimator();
            animator.setValues(rect_x, rect_y);
            animator.setDuration(1000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    x = (float) valueAnimator.getAnimatedValue("RECT_X");
                    y = (float) valueAnimator.getAnimatedValue("RECT_Y");
                }
            });

            animator.start();
        }
    }

    public void SetText(String s) {
        text = s;
    }
}
