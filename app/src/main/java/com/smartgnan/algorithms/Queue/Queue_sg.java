package com.smartgnan.algorithms.Queue;

import android.graphics.Point;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.graphics.State;
import com.smartgnan.helpers.Helper;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;
import com.smartgnan.widgets.CircleWidget;
import com.smartgnan.widgets.PathWidget;
import com.smartgnan.widgets.PointerWidget;

import java.util.ArrayList;

public class Queue_sg extends BaseAlgorithm {

    int nodeRadius;
    int nodeY;
    int startX;
    int endX;
    int maxCount;

    public Queue_sg(int w, int h) {
        super("Queue", w, h);
    }

    @Override
    protected void UpdateOptions() {
        options.add(new Options("Enqueue", OptionType.Input_Event));
        options.add(new Options("Dequeue", OptionType.Event));
        options.add(new Options("IsEmpty", OptionType.Event));
    }

    @Override
    public void Init() {
        maxCount = 5;
        nodeRadius = (screenWidth / (maxCount + 3))/2;
        nodeY = halfHeight / 2;
        startX = screenWidth - (nodeRadius * 2) - padding;
        endX = padding;

        PathWidget container1 = new PathWidget();
        container1.points.add(new Point(startX - padding, nodeY - nodeRadius - padding));
        container1.points.add(new Point(endX + (nodeRadius*2) + padding, nodeY - nodeRadius - padding));

        PathWidget container2 = new PathWidget();
        container2.points.add(new Point(startX - padding, nodeY + nodeRadius + padding));
        container2.points.add(new Point(endX + (nodeRadius*2) + padding, nodeY + nodeRadius + padding));

        PointerWidget frontPointer = new PointerWidget(endX + (nodeRadius*2) + padding, nodeY + nodeRadius + padding + padding, 'F');
        PointerWidget tailPointer = new PointerWidget(startX - padding, nodeY + nodeRadius + padding + padding, 'T');

        this.Background.add(container1);
        this.Background.add(container2);
        this.Background.add(frontPointer);
        this.Background.add(tailPointer);

    }

    @Override
    public void ProcessOptions(int index) {
        switch (index) {
            case 0:
                Enqueue(options.get(index).inputs.get(0));
                break;
            case 1:
                Dequeue();
                break;
            case 2:
                IsEmpty();
                break;
            default:
                break;
        }
    }

    private void IsEmpty() {
        States.clear();
        boolean isEmpty = Nodes.size() == 0;
        InsertState("The Queue is " + (isEmpty ? "" : "not") + " empty.");
    }

    private void Dequeue() {
        States.clear();
        InsertState("Initial state");

        InsertState("Checking whether Stack is empty or not");

        if(Nodes.isEmpty()) {
            IsEmpty();
            return;
        }
        else
        {
            IsEmpty();
        }

        int idx = 0;
        Nodes.get(idx).color = Helper.ColorRed;
        InsertState("Selecting the front item of the Queue");

        int frontX = ((CircleWidget) Nodes.get(idx)).x;
        ((CircleWidget) Nodes.get(idx)).UpdateNewBounds(endX+nodeRadius, ((CircleWidget) Nodes.get(idx)).y);
        InsertState("Removing the from item from the Queue");

        Nodes.remove(idx);
        int temp;
        for(int i = 0; i < Nodes.size(); i++) {
            temp = ((CircleWidget) Nodes.get(i)).x;
            ((CircleWidget) Nodes.get(i)).UpdateNewBounds(frontX, ((CircleWidget) Nodes.get(i)).y);
            frontX = temp;
        }
        InsertState("Final State after Dequeue operation");

        for(int i = 0; i < Nodes.size(); i++) {
            Nodes.get(i).isUpdated = false;
        }
    }

    private void Enqueue(int value) {
        States.clear();
        InsertState("Initial state");

        CircleWidget newNode = new CircleWidget(startX, nodeY, nodeRadius, ""+value);
        newNode.color = Helper.ColorRed;
        Nodes.add(newNode);
        InsertState("New item is created");

        int idx = Nodes.size()-1;
        int newX = endX + ((nodeRadius * 2 + padding) * (Nodes.size() + 1)) - nodeRadius;
        ((CircleWidget) Nodes.get(idx)).UpdateNewBounds(newX, ((CircleWidget) Nodes.get(idx)).y);
        InsertState("Placing the new item from the tail");

        Nodes.get(idx).color = Helper.ColorOrange;
        Nodes.get(idx).isUpdated = false;
        InsertState("Final state after Enqueue operation");
    }

    @Override
    public void Process() {

    }

    @Override
    public void InsertState(String info) {
        ArrayList<BaseWidget> copyNodes = new ArrayList<>();

        for(int i = 0; i < this.Nodes.size(); i++) {
            copyNodes.add(new CircleWidget(((CircleWidget) this.Nodes.get(i))));
        }

        State s = new State(copyNodes);
        s.info = info;
        this.States.add(s);
    }
}
