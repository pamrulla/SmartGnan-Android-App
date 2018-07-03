package com.smartgnan.algorithms;

import android.view.View;

import com.smartgnan.graphics.State;
import com.smartgnan.helpers.Options;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;

import java.util.ArrayList;

import static com.smartgnan.helpers.Helper.maxValue;
import static com.smartgnan.helpers.Helper.minValue;
import static java.lang.Math.abs;

public abstract class BaseAlgorithm {
    String Title;
    String Info;
    public ArrayList<Options> options;
    public ArrayList<State> States;
    public ArrayList<BaseWidget> Background;
    public ArrayList<BaseWidget> Nodes;
    public ArrayList<State> Extras;
    public ArrayList<BaseWidget> ExtrasNodes;
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
        this.options = new ArrayList<>();
        this.Extras = new ArrayList<>();
        this.ExtrasNodes = new ArrayList<>();
        Init();
        UpdateOptions();
    }

    protected abstract void UpdateOptions();

    public abstract void Init();

    public abstract void ProcessOptions(int index);

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

    public abstract void UpdateActionView(View customView, int index);

    public void ClearStates() {
        States.clear();
        Extras.clear();
    }

    public void ClearIsUpdateOfNodes() {
        for(int i = 0; i < Nodes.size(); i++) {
            Nodes.get(i).isUpdated = false;
        }
    }

    public void ClearIsUpdateOfExtras() {
        for(int i = 0; i < ExtrasNodes.size(); i++) {
            ExtrasNodes.get(i).isUpdated = false;
        }
    }
}
