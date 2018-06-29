package com.smartgnan.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.helpers.Helper;
import com.smartgnan.smartalgo.R;
import com.smartgnan.smartalgo.SimActivity;
import com.smartgnan.widgets.BaseWidget;

/**
 * TODO: document your custom view class.
 */
public class SimView extends View {
    Context context;
    BaseAlgorithm Algorithm;
    Paint paint;
    boolean isAnimating = false;
    int currentIndex;
    int screenWidth, screenHeight, halfHeight;

    public SimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        currentIndex = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        halfHeight = h / 2;

        ((SimActivity)getContext()).AfterGotSize(w, h);
    }

    public void SetAlgorithmReference(BaseAlgorithm alg) {
        this.Algorithm = alg;
    }

    public void UpdateAnimation(int index) {
        isAnimating = true;
        currentIndex = index;
        for (BaseWidget w : this.Algorithm.getStates().get(currentIndex).Nodes) {
            w.Animate();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(BaseWidget w : this.Algorithm.Background) {
            w.RenderWidget(canvas);
        }
        if(this.Algorithm.States.size() > 0) {
            for (BaseWidget w : this.Algorithm.getStates().get(currentIndex).Nodes) {
                w.RenderWidget(canvas);
            }
        }
    }

    public void SetCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
