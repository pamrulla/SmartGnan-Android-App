package com.smartgnan.algorithms;

import com.smartgnan.graphics.State;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;

import java.util.ArrayList;

import static com.smartgnan.helpers.Helper.maxValue;
import static com.smartgnan.helpers.Helper.minValue;
import static java.lang.Math.abs;

public abstract class BaseAlgorithm {
    String Title;
    String Info;
    String Options;
    public ArrayList<State> States;
    public ArrayList<BaseWidget> Background;
    public ArrayList<BaseWidget> Nodes;
    protected int screenWidth;
    int screenHeight;
    protected int halfHeight;
    protected final int padding = 5;
    final int topPadding = 5;

    public BaseAlgorithm(String title, int w, int h) {
        Title = title;
        screenWidth = w;
        screenHeight = h;
        halfHeight = h / 2;
        this.States = new ArrayList<State>();
        this.Background = new ArrayList<BaseWidget>();
        this.Nodes = new ArrayList<>();
        Init();
    }

    public abstract void Init();

    public abstract void ProcessOptions();

    public abstract void Process();

    public ArrayList<State> getStates() {
        return States;
    }

    public void CreateNodesForBarGraph(ArrayList<Integer> dataSet) {

        int widthPerNode = screenWidth / dataSet.size();
        int offset = 0;
        int actualFullHeight = halfHeight - topPadding;
        int min = minValue(dataSet);
        int max = maxValue(dataSet);

        int diff = abs(max - min) + 2;
        int eachPartHeight = actualFullHeight / diff;

        for (int i = 0; i < dataSet.size(); i++) {
            BoxWidget n = new BoxWidget();
            n.SetBounds(offset + padding, topPadding + actualFullHeight - (eachPartHeight * dataSet.get(i)),
                    widthPerNode - padding - padding, (eachPartHeight * dataSet.get(i)));
            n.SetText(dataSet.get(i).toString());
            offset += widthPerNode;
            this.Nodes.add(n);
        }
    }

    public abstract void InsertState(String info);
}
