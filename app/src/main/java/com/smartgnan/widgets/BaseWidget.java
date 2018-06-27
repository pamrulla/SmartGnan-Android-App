package com.smartgnan.widgets;

import android.graphics.Canvas;
import android.graphics.Color;

import com.smartgnan.helpers.Helper;

public abstract class BaseWidget {
    public int color = Helper.ColorOrange;
    public boolean isUpdated = false;

    public abstract void RenderWidget(Canvas canvas);
    public abstract void Animate();
}
