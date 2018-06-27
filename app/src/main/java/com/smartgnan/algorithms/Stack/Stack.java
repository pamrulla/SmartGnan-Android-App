package com.smartgnan.algorithms.Stack;

import android.graphics.Point;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.widgets.PathWidget;

public class Stack extends BaseAlgorithm {

    int nodeWidth;
    int nodeHeight;
    int maxCount = 8;

    public Stack(int w, int h) {
        super("Stack", w, h);

    }

    @Override
    public void Init() {
        maxCount = 5;
        int screenCenter = super.screenWidth / 4;

        nodeWidth = screenCenter - super.padding - padding;
        nodeHeight = (halfHeight / (maxCount+2)) - padding;

        PathWidget stackContainer = new PathWidget();
        stackContainer.points.add(new Point((screenWidth/2) - (screenCenter/2), nodeHeight));
        stackContainer.points.add(new Point((screenWidth/2) - (screenCenter/2), halfHeight - padding));
        stackContainer.points.add(new Point((screenWidth/2) + (screenCenter/2), halfHeight-padding));
        stackContainer.points.add(new Point((screenWidth/2) + (screenCenter/2), nodeHeight));

        this.Background.add(stackContainer);

    }

    @Override
    public void ProcessOptions() {

    }

    @Override
    public void Process() {

    }

    @Override
    public void InsertState(String info) {

    }
}
