package com.smartgnan.algorithms.LinkedLists;

import android.provider.ContactsContract;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.graphics.State;
import com.smartgnan.helpers.Helper;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;
import com.smartgnan.widgets.LineWidget;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;

public class SingleLinkedLists extends BaseAlgorithm {

    ArrayList<Integer> dataSet;
    int maxNodes;
    int nodeFullWidth;
    int lineWidth;
    int valueWidth;
    int pointerWidth;
    int nodeHeight;
    int startX;
    int startY;
    int newNodeX;
    int newNodeY;
    int widgetsPerNode;

    public SingleLinkedLists(int w, int h) {
        super("Single Linked Lists", w, h);
    }

    @Override
    protected void UpdateOptions() {
        this.options.add(new Options("Insert At Beginning", OptionType.Input_Event));
        this.options.add(new Options("Insert At End", OptionType.Input_Event));
        this.options.add(new Options("Insert At Position", OptionType.Input_Event));
        this.options.add(new Options("Delete At Beginning", OptionType.Input_Event));
        this.options.add(new Options("Delete At End", OptionType.Input_Event));
        this.options.add(new Options("Delete At Position", OptionType.Input_Event));
        this.options.add(new Options("Traverse", OptionType.Input_Event));
        this.options.add(new Options("Search", OptionType.Input_Event));
        this.options.add(new Options("Length", OptionType.Input_Event));
    }

    @Override
    public void Init() {
        maxNodes = 5;
        nodeFullWidth = (screenWidth - (padding*5)) / maxNodes;
        lineWidth = (int) (nodeFullWidth * 0.35f);
        valueWidth = (int)(nodeFullWidth * 0.5f);
        pointerWidth = (int)(nodeFullWidth * 0.15f);
        nodeHeight = nodeFullWidth / 2;
        startX = padding;
        startY = padding;
        newNodeX = screenWidth/2 - nodeFullWidth/2;
        newNodeY = halfHeight - padding - nodeHeight;
        widgetsPerNode = 3;

        InsertState("Insert an item to start");
    }

    @Override
    public void ProcessOptions(int index) {
        switch (index) {
            case 0: // Insert At Beginning
                InsertAtBeginning(options.get(index).inputs.get(0));
                break;
            case 1: // Insert At End
                InsertAtEnd(options.get(index).inputs.get(0));
                break;
            case 2: // Insert At Position
                InsertAtPosition(options.get(index).inputs.get(0), options.get(index).inputs.get(1));
                break;
            case 3: // Delete At Beginning
                DeleteAtBeginning();
                break;
            case 4: // Delete At End
                DeleteAtEnd();
                break;
            case 5: // Delete At Position
                DeleteAtPosition(options.get(index).inputs.get(0));
                break;
            case 6: // Traverse
                Traverse();
                break;
            case 7: // Search
                Search(options.get(index).inputs.get(0));
                break;
            case 8: // Length
                Length();
                break;
            default:
                break;
        }
    }

    private void Length() {
        States.clear();

        if(this.Nodes.size() == 0) {
            InsertState("Length of the Linked List is 0");
            return;
        }
        int count = 1;
        int  i = 0;
        this.Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head node");

        for(i += widgetsPerNode; i < this.Nodes.size(); i+=widgetsPerNode ) {
            count++;

            this.Nodes.get(i-1).color = Helper.ColorBlue;
            InsertState("Travelling to next node");

            this.Nodes.get(i).color = Helper.ColorRed;
            InsertState("Length till " + count);
        }

        InsertState("Next node is not available");

        for(i = 0; i < this.Nodes.size(); i+=widgetsPerNode ) {
            this.Nodes.get(i).color = Helper.ColorOrange;
        }
        InsertState("The length of Linked List is " + count);
    }

    private void Search(Integer integer) {
        States.clear();
        boolean isFound = false;

        if(this.Nodes.size() == 0) {
            InsertState("The Linked List is empty");
            return;
        }

        int  i = 0;

        for(i = 0; i < this.Nodes.size(); i+=widgetsPerNode ) {

            if(i == 0) {
                this.Nodes.get(i).color = Helper.ColorRed;
                InsertState("Starting with head node the value is " + ((BoxWidget) this.Nodes.get(i)).text);
            }
            else {
                this.Nodes.get(i-1).color = Helper.ColorBlue;
                InsertState("Travelling to next node");

                this.Nodes.get(i-widgetsPerNode).color = Helper.ColorRed;
                this.Nodes.get(i).color = Helper.ColorRed;
                InsertState("Value at current node is " + ((BoxWidget) this.Nodes.get(i)).text);
            }

            InsertState("Comparing give value " + integer + " with current node value " + ((BoxWidget) this.Nodes.get(i)).text);

            if(Integer.parseInt(((BoxWidget) this.Nodes.get(i)).text) == integer) {
                isFound = true;
                this.Nodes.get(i).color = Helper.ColorOrange;
                InsertState("Given value is present at " + (i%widgetsPerNode) + " position");
                return;
            }
            else {
                InsertState("Given value not present at current Node");
            }
        }

        for(i = 0; i < this.Nodes.size(); i+=widgetsPerNode ) {
            this.Nodes.get(i).color = Helper.ColorOrange;
        }
        InsertState("We traversed complete List, given value not found");
    }

    private void Traverse() {
        States.clear();
        String values = "";

        if(this.Nodes.size() == 0) {
            InsertState("The Linked List is empty");
            return;
        }

        int  i = 0;
        this.Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head node");
        values += ((BoxWidget) this.Nodes.get(i)).text;

        for(i += widgetsPerNode; i < this.Nodes.size(); i+=widgetsPerNode ) {
            values += ", ";
            this.Nodes.get(i-1).color = Helper.ColorBlue;
            InsertState("Travelling to next node");

            this.Nodes.get(i).color = Helper.ColorRed;
            InsertState("Value at current node is " + ((BoxWidget) this.Nodes.get(i)).text);
        }

        InsertState("Next node is not available");

        for(i = 0; i < this.Nodes.size(); i+=widgetsPerNode ) {
            this.Nodes.get(i).color = Helper.ColorOrange;
        }
        InsertState("The values are " + values);
    }

    private void DeleteAtPosition(Integer position) {
        States.clear();

        InsertState("Checking for given position is valid value?");
        if(position < 0 && position > this.Nodes.size() % widgetsPerNode) {
            InsertState("Invalid position");
            return;
        }

        InsertState("Checking for given position at the beginning?");
        if(position == 0) {
            InsertState("Given position is 0 hence performing Delete At Beginning");
            DeleteAtBeginning();
            return;
        }

        InsertState("Checking for given position at the end?");
        if(position == Nodes.size() % widgetsPerNode) {
            InsertState("Given position is the length of linked list, hence performing Delete At End");
            DeleteAtEnd();
            return;
        }

        int i = 0;
        Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head");
        i += widgetsPerNode;

        while(i < position) {
            int j = i -widgetsPerNode - widgetsPerNode;
            if(j >= 0) {
                Nodes.get(j).color = Helper.ColorOrange;
            }
            Nodes.get(i-widgetsPerNode).color = Helper.ColorBlue;
            Nodes.get(i).color = Helper.ColorRed;
            InsertState("Proceeding to next");
            i += widgetsPerNode;
        }

        //point prev node to next node
        //TODO
        InsertState("Linking previous node with next node");

        Nodes.remove(i);
        Nodes.remove(i+1);
        //TODO

        InsertState("Final State of Linked List after deleting");
    }

    private void DeleteAtEnd() {
        States.clear();

        int i = 0;
        this.Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting at head");
        i += widgetsPerNode;

        while(i < this.Nodes.size() % widgetsPerNode) {
            this.Nodes.get(i - widgetsPerNode).color = Helper.ColorOrange;
            this.Nodes.get(i).color = Helper.ColorRed;
            i+= widgetsPerNode;
            InsertState("Selecting next node and marking previous node");
        }

        this.Nodes.get(i).color = Helper.ColorRed;
        InsertState("Selecting tail node");

        InsertState("Moving tail to previous node");

        this.Nodes.remove(i);
        this.Nodes.remove(i+1);
        if(i > 0) {
            this.Nodes.remove(i - 1);
        }

        InsertState("Final State of Linked List");
    }

    private void DeleteAtBeginning() {
        States.clear();

        this.Nodes.get(0).color = Helper.ColorRed;
        InsertState("Starting with head");

        InsertState("Moving head to next node");

        this.Nodes.remove(0);
        this.Nodes.remove(1);
        //TODO

        ReArrangeAllNode();
        InsertState("Final State of the Linked List");
    }

    private void InsertAtPosition(Integer integer, Integer position) {
        States.clear();

        InsertState("Checking for given position is valid value?");
        if(position < 0) {
            InsertState("Invalid position");
            return;
        }

        InsertState("Checking for given position at the beginning?");
        if(position == 0) {
            InsertState("Given position is 0 hence performing Insert At Beginning");
            InsertAtBeginning(integer);
            return;
        }

        InsertState("Checking for given position at the end?");
        if(position > Nodes.size() % widgetsPerNode) {
            InsertState("Given position is greater than length of linked list, hence performing Insert At End");
            InsertAtEnd(integer);
            return;
        }

        int i = 0;
        Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head");
        i += widgetsPerNode;

        while(i < position) {
            int j = i -widgetsPerNode - widgetsPerNode;
            if(j >= 0) {
                Nodes.get(j).color = Helper.ColorOrange;
            }
            Nodes.get(i-widgetsPerNode).color = Helper.ColorBlue;
            Nodes.get(i).color = Helper.ColorRed;
            InsertState("Proceeding to next");
            i += widgetsPerNode;
        }

        //point new node to current node
        //TODO
        InsertState("Linking new node with current node");

        //point previous node to new node
        //TODO
        InsertState("Linking previous node with new node");

        i = Nodes.size() - widgetsPerNode;
        BoxWidget valueNode = new BoxWidget(((BoxWidget) Nodes.get(i)));
        BoxWidget pointerNode = new BoxWidget(((BoxWidget) Nodes.get(i+1)));
        //TODO

        Nodes.remove(i);
        Nodes.remove(i+1);
        //TODO

        i = position * widgetsPerNode;
        Nodes.add(i, valueNode);
        Nodes.add(i+1, pointerNode);
        //TODO

        ReArrangeAllNode();
        InsertState("Final State of Linked List");
    }

    private void InsertAtEnd(Integer integer) {
        States.clear();

        CreateNewNode(integer);

        //Create a link from new node from tail
        //TODO

        ReArrangeAllNode();
        InsertState("Final State of Linked List");
    }

    private void ReArrangeAllNode() {
        int x = startX;
        int y = startY;

        int i  = 0;

        while(i < Nodes.size()) {
            ((BoxWidget) Nodes.get(i)).UpdateNewBounds(x, y);
            if(i % widgetsPerNode == 0) {
                x += valueWidth;
            }
            else if(i % widgetsPerNode == 1) {
                x += pointerWidth;
            }
            else {
                x += lineWidth;
            }
        }
    }

    private void InsertAtBeginning(Integer integer) {
        States.clear();

        CreateNewNode(integer);

        if(Nodes.size() > widgetsPerNode) {
            //Create a link from new node to existing head
            //TODO

            Collections.rotate(this.Nodes, widgetsPerNode);

            ReArrangeAllNode();
        }

        InsertState("Final State of Linked List");
    }

    private void CreateNewNode(int value) {
        BoxWidget valueNode = new BoxWidget(newNodeX, newNodeY, valueWidth, nodeHeight, "" + value);
        BoxWidget pointerNode = new BoxWidget(newNodeX+valueWidth, newNodeY, pointerWidth, nodeHeight);

        this.Nodes.add(valueNode);
        this.Nodes.add(pointerNode);

        InsertState("Created a new node with value " + value);
    }

    @Override
    public void Process() {

    }

    @Override
    public void InsertState(String info) {
        ArrayList<BaseWidget> copyNodes = new ArrayList<>();

        for(int i = 0; i < this.Nodes.size(); i++) {
            if (BoxWidget.class.isInstance(this.Nodes.get(i))) {
                copyNodes.add(new BoxWidget(((BoxWidget) this.Nodes.get(i))));
            }
            else if (LineWidget.class.isInstance(this.Nodes.get(i))) {
                copyNodes.add(new LineWidget(((LineWidget) this.Nodes.get(i))));
            }

        }

        State s = new State(copyNodes);
        s.info = info;
        this.States.add(s);
    }
}
