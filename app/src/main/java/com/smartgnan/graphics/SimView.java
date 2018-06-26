package com.smartgnan.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.smartgnan.algorithms.BaseAlgorithm;
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

    public SimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ((SimActivity)getContext()).AfterGotSize(w, h);
    }

    public void SetAlgorithmReference(BaseAlgorithm alg) {
        this.Algorithm = alg;
    }

    public void UpdateAnimation() {
        isAnimating = true;
        for (BaseWidget w : this.Algorithm.getStates().get(1).Nodes) {
            w.Animate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.WHITE);
        if(isAnimating) {
            for (BaseWidget w : this.Algorithm.getStates().get(1).Nodes) {
                w.RenderWidget(canvas);
            }
        }
        else {
            for (BaseWidget w : this.Algorithm.getStates().get(0).Nodes) {
                w.RenderWidget(canvas);
            }
        }

        invalidate();
    }
}
