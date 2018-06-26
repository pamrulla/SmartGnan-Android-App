package com.smartgnan.graphics;

import com.smartgnan.widgets.BaseWidget;

import java.util.ArrayList;

public class State {
    ArrayList<BaseWidget> Nodes;
    public String info;

    public State(ArrayList<BaseWidget> nodes) {
        info = "";
        this.Nodes = new ArrayList<>();
        this.Nodes.addAll(nodes);
    }
}
