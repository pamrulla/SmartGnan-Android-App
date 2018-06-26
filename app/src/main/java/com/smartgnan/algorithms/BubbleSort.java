package com.smartgnan.algorithms;

import android.os.Debug;
import android.util.Log;

import com.smartgnan.graphics.State;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;

import java.util.ArrayList;

public class BubbleSort extends BaseAlgorithm {

    public BubbleSort(int w, int h) {
        super("Bubble Sort", w, h);
    }

    @Override
    public void Init() {
        ArrayList<Integer> dataSet = new ArrayList<Integer>() {{
            add(8);
            add(1);
            add(3);
            add(6);
            add(7);
            add(4);
            add(2);
            add(5);
        }};

        this.CreateNodesForBarGraph(dataSet);

        State s = new State(this.Nodes);
        s.info = "Initial State";
        this.States.add(s);
    }

    @Override
    public void ProcessOptions() {

    }

    @Override
    public void Process() {
        ArrayList<BaseWidget> newNodes = new ArrayList<>();

        for(int i = 0; i < this.Nodes.size(); i++)
        {
            if(i == 0) {
                BoxWidget w1 = new BoxWidget((BoxWidget) (this.Nodes.get(i)));
                BoxWidget w2 = new BoxWidget((BoxWidget) (this.Nodes.get(i + 1)));
                float temp = w1.x;
                w1.UpdateNewBounds(w2.x, w1.y);
                w2.UpdateNewBounds(temp, w2.y);
                newNodes.add(w1);
                newNodes.add(w2);
                i= 1;
            }
            else
            {
                BoxWidget w1 = new BoxWidget((BoxWidget) (this.Nodes.get(i)));
                newNodes.add(w1);
            }
        }

        State s = new State(newNodes);
        s.info = "Second State";
        this.States.add(s);

    }
}
