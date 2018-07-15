package com.smartgnan.algorithms.LinkedLists;

import android.graphics.Point;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.graphics.State;
import com.smartgnan.helpers.BoxWidgetTextLocation;
import com.smartgnan.helpers.Helper;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;
import com.smartgnan.smartalgo.R;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;
import com.smartgnan.widgets.LineWidget;
import com.smartgnan.widgets.PointerWidget;

import org.w3c.dom.Node;

import java.text.NumberFormat;
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

    int dialogValue = -1;
    int dialogPosition = -1;
    int dialogOption = 100;

    public SingleLinkedLists(int w, int h) {
        super(w, h);
    }

    @Override
    protected void UpdateOptions() {
        this.options.add(new Options("Generate", OptionType.Event));
        this.options.add(new Options("Insert", OptionType.Input_Event));
        this.options.add(new Options("Delete", OptionType.Input_Event));
        this.options.add(new Options("Search", OptionType.Input_Event));
        this.options.add(new Options("Traverse", OptionType.Event));
        this.options.add(new Options("Length", OptionType.Event));
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

        PointerWidget head = new PointerWidget(startX + padding, startY + nodeHeight, 'H');
        ExtrasNodes.add(head);

        InsertState("Insert an item to start");
    }

    @Override
    public void ProcessOptions(int index) {
        switch (index) {
            case 0: // Generate
                Nodes.clear();
                InsertAtBeginning(1);
                InsertAtEnd(2);
                InsertAtEnd(3);
                ClearIsUpdateOfExtras();
                ClearIsUpdateOfNodes();
                ClearStates();
                InsertState("Generation completed");
                break;
            case 1: // Insert
                if(dialogOption == 100) {
                    InsertAtBeginning(dialogValue);
                }
                else if(dialogOption == 101) {
                    InsertAtPosition(dialogValue, dialogPosition);
                }
                else if(dialogOption == 102) {
                    InsertAtEnd(dialogValue);
                }
                break;
            case 2: // Delete
                if(dialogOption == 100) {
                    DeleteAtBeginning();
                }
                else if(dialogOption == 101) {
                    DeleteAtPosition(dialogPosition);
                }
                else if(dialogOption == 102) {
                    DeleteAtEnd();
                }
                break;
            case 3: // Search
                Search(dialogValue);
                break;
            case 4: // Traverse
                Traverse();
                break;
            case 5: // Length
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
                InsertState("Given value is present at " + (i/widgetsPerNode) + " position");
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
            values += ((BoxWidget) this.Nodes.get(i)).text;
        }

        InsertState("Next node is not available");

        for(i = 0; i < this.Nodes.size(); i+=widgetsPerNode ) {
            this.Nodes.get(i).color = Helper.ColorOrange;
        }
        InsertState("The values are " + values);
    }

    private void DeleteAtPosition(Integer position) {
        ClearStates();

        if(position < 0 || position > this.Nodes.size() / widgetsPerNode) {
            InsertState("Invalid position");
            return;
        }

        if(position == 0 || Nodes.size() == 1) {
            DeleteAtBeginning();
            return;
        }

        if(position == (Nodes.size() / widgetsPerNode)) {
            DeleteAtEnd();
            return;
        }

        InsertState("Initial State of the Linked List");

        int i = 0;
        Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head");
        i += widgetsPerNode;

        while(i <= (position * widgetsPerNode)) {
            int j = i - widgetsPerNode - widgetsPerNode;
            int k = i - widgetsPerNode;
            if(j >= 0) {
                Nodes.get(j).color = Helper.ColorOrange;
            }
            if(k >= 0) {
                Nodes.get(k).color = Helper.ColorBlue;
            }
            Nodes.get(i).color = Helper.ColorRed;
            InsertState("Proceeding to next");
            i += widgetsPerNode;
        }

        //point new node to current node
        int currIdx = i - widgetsPerNode;
        int prevIdx = currIdx - widgetsPerNode;
        int nextIdx = currIdx + widgetsPerNode;

        ((LineWidget) Nodes.get(currIdx - 1)).UpdateNewBounds((int)(((BoxWidget) Nodes.get(prevIdx + 1)).x + pointerWidth / 2),
                (int)(((BoxWidget) Nodes.get(prevIdx + 1)).y + nodeHeight / 2),
                ((int) ((BoxWidget) Nodes.get(nextIdx)).x),
                ((int) ((BoxWidget) Nodes.get(nextIdx)).y) +  nodeHeight / 2);

        ((BoxWidget) Nodes.get(currIdx)).UpdateNewBounds(newNodeX, newNodeY);
        ((BoxWidget) Nodes.get(currIdx+1)).UpdateNewBounds(newNodeX+valueWidth, newNodeY);
        ((LineWidget) Nodes.get(currIdx+2)).UpdateNewBounds((int)(((BoxWidget) Nodes.get(currIdx + 1)).x + pointerWidth / 2),
                (int)(((BoxWidget) Nodes.get(currIdx + 1)).y + nodeHeight / 2),
                ((int) ((BoxWidget) Nodes.get(nextIdx)).x),
                ((int) ((BoxWidget) Nodes.get(nextIdx)).y) +  nodeHeight / 2);

        InsertState("Updating previous node link to next node");

        ((LineWidget) Nodes.get(currIdx - 1)).isUpdated = false;
        ((LineWidget) Nodes.get(currIdx+2)).isUpdated = false;
        Nodes.remove(currIdx);
        Nodes.remove(currIdx);
        Nodes.remove(currIdx);

        InsertState("Deleting the current node");

        ((BoxWidget) Nodes.get(prevIdx)).color = Helper.ColorOrange;
        ((BoxWidget) Nodes.get(currIdx)).color = Helper.ColorOrange;

        ReArrangeAllNode();
        InsertState("Final State of Linked List");

        ClearIsUpdateOfNodes();
        ClearIsUpdateOfExtras();
    }

    private void DeleteAtEnd() {
        ClearStates();

        InsertState("Initial Status of Linked List");

        if(Nodes.size() == 0) {
            InsertState("List is empty");
            return;
        }

        int i = 0;
        Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head");
        i += widgetsPerNode;

        while(i < Nodes.size()) {
            int j = i - widgetsPerNode - widgetsPerNode;
            int k = i - widgetsPerNode;
            if(j >= 0) {
                Nodes.get(j).color = Helper.ColorOrange;
            }
            if(k >= 0) {
                Nodes.get(k).color = Helper.ColorBlue;
            }
            Nodes.get(i).color = Helper.ColorRed;
            InsertState("Proceeding till last node");
            i += widgetsPerNode;
        }

        int currIdx = Nodes.size() - widgetsPerNode + 1;

        if(currIdx - 1 >= 0) {
            Nodes.remove(currIdx - 1);
            InsertState("Deleting link between previous node and last node");
        }

        Nodes.remove(Nodes.size()-1);
        Nodes.remove(Nodes.size()-1);
        InsertState("Deleting last node");

        if(Nodes.size() > 0) {
            Nodes.get(Nodes.size() - widgetsPerNode + 1).color = Helper.ColorOrange;
        }
        InsertState("Final State of Linked Lists");

        ClearIsUpdateOfNodes();
        ClearIsUpdateOfExtras();
    }

    private void DeleteAtBeginning() {
        ClearStates();

        InsertState("Initial Status of Linked List");

        if(Nodes.size() == 0) {
            InsertState("List is empty");
            return;
        }

        this.Nodes.get(0).color = Helper.ColorRed;
        InsertState("Starting with head");

        if(Nodes.size() > widgetsPerNode) {
            float hx = ((BoxWidget) Nodes.get(widgetsPerNode)).x;
            float hy = ((BoxWidget) Nodes.get(widgetsPerNode)).y;
            ((PointerWidget) ExtrasNodes.get(0)).UpdateLocation(((int) hx), ((int) hy) + nodeHeight);
            InsertState("Moving head to next node");
        }

        this.Nodes.remove(0);
        this.Nodes.remove(0);
        if(this.Nodes.size() != 0) {
            this.Nodes.remove(0);
        }
        ((PointerWidget) ExtrasNodes.get(0)).isUpdated = false;
        InsertState("Deleting first node");

        ReArrangeAllNode();
        ((PointerWidget) ExtrasNodes.get(0)).UpdateLocation(startX + padding, startY + nodeHeight);
        InsertState("Final State of the Linked List");

        ClearIsUpdateOfNodes();
        ClearIsUpdateOfExtras();
    }

    private void InsertAtPosition(Integer integer, Integer position) {
        ClearStates();
        InsertState("Initial State of the Linked List");

        InsertState("Checking for given position is valid value?");
        if(position < 0) {
            InsertState("Invalid position");
            return;
        }

        if(Nodes.size() == 0) {
            InsertState("Linked List is Empty hence performing Insert At Beginning");
            InternalInsertAtBeginning(integer);
            return;
        }

        InsertState("Checking for given position at the beginning?");
        if(position == 0) {
            InsertState("Given position is 0 hence performing Insert At Beginning");
            InternalInsertAtBeginning(integer);
            return;
        }

        InsertState("Checking for given position at the end?");
        if(position > Nodes.size() / widgetsPerNode) {
            InsertState("Given position is greater than length of linked list, hence performing Insert At End");
            InternalInsertAtEnd(integer);
            return;
        }

        CreateNewNode(integer);

        int i = 0;
        Nodes.get(i).color = Helper.ColorRed;
        InsertState("Starting with head");
        i += widgetsPerNode;

        while(i <= (position * widgetsPerNode)) {
            int j = i - widgetsPerNode - widgetsPerNode;
            int k = i - widgetsPerNode;
            if(j >= 0) {
                Nodes.get(j).color = Helper.ColorOrange;
            }
            if(k >= 0) {
                Nodes.get(k).color = Helper.ColorBlue;
            }
             Nodes.get(i).color = Helper.ColorRed;
            InsertState("Proceeding to next");
            i += widgetsPerNode;
        }

        //point new node to current node
        int currIdx = i - widgetsPerNode;
        int prevIdx = currIdx - widgetsPerNode;
        int newNodeIdx = Nodes.size() - widgetsPerNode + 1;
        CreateLine(((BoxWidget) Nodes.get(newNodeIdx)), ((BoxWidget) Nodes.get(currIdx)), -1);
        InsertState("Linking new node with current node");

        //point previous node to new node
        CreateLine(((BoxWidget) Nodes.get(prevIdx)), ((BoxWidget) Nodes.get(newNodeIdx)), -1);
        Nodes.remove(currIdx-1);
        InsertState("Linking previous node with new node");

        i = Nodes.size() - 1;
        LineWidget prevToNew = new LineWidget(((LineWidget) Nodes.get(i)));
        --i;
        LineWidget newToCurr = new LineWidget(((LineWidget) Nodes.get(i)));
        --i;
        BoxWidget pointerNode= new BoxWidget(((BoxWidget) Nodes.get(i)));
        --i;
        BoxWidget valueNode  = new BoxWidget(((BoxWidget) Nodes.get(i)));

        Nodes.remove(Nodes.size()-1);
        Nodes.remove(Nodes.size()-1);
        Nodes.remove(Nodes.size()-1);
        Nodes.remove(Nodes.size()-1);

        i = prevIdx + widgetsPerNode - 1;
        Nodes.add(i, prevToNew);
        ++i;
        Nodes.add(i, valueNode);
        ++i;
        Nodes.add(i, pointerNode);
        ++i;
        Nodes.add(i, newToCurr);

        ((BoxWidget) Nodes.get(prevIdx)).color = Helper.ColorOrange;
        ((BoxWidget) Nodes.get(i+1)).color = Helper.ColorOrange;

        ReArrangeAllNode();
        InsertState("Final State of Linked List");

        ClearIsUpdateOfNodes();
        ClearIsUpdateOfExtras();
    }

    private void InternalInsertAtEnd(Integer integer) {
        CreateNewNode(integer);
        int i = 0;
        for(i = 0; i < Nodes.size() - widgetsPerNode; i+=widgetsPerNode) {
            if(i == 0) {
                this.Nodes.get(i).color = Helper.ColorRed;
                InsertState("Starting from head node");
            }
            else {
                this.Nodes.get(i).color = Helper.ColorRed;
                this.Nodes.get(i-(widgetsPerNode)).color = Helper.ColorOrange;
                InsertState("Traversing till we find last node");
            }
        }

        int fromIdx = Nodes.size() - widgetsPerNode;
        int toIdx = fromIdx + 1;

        //Create a link from new node from tail
        CreateLine(((BoxWidget) Nodes.get(fromIdx)), ((BoxWidget) Nodes.get(toIdx)), fromIdx + 1);
        InsertState("Created link from last node to new node");


        this.Nodes.get(i-widgetsPerNode).color = Helper.ColorOrange;
        ReArrangeAllNode();
        InsertState("Final State of Linked List");

        ClearIsUpdateOfNodes();
        ClearIsUpdateOfExtras();
    }

    private void InsertAtEnd(Integer integer) {
        if(this.Nodes.size() == 0) {
            InsertAtBeginning(integer);
            return;
        }

        ClearStates();

        InsertState("Initial State of the Linked List");

        InternalInsertAtEnd(integer);
    }

    private void InsertAtBeginning(Integer integer) {
        ClearStates();

        InsertState("Initial State of the Linked List");

        InternalInsertAtBeginning(integer);
    }

    private void InternalInsertAtBeginning(Integer integer) {
        CreateNewNode(integer);

        if(Nodes.size() > widgetsPerNode-1) {
            //Create a link from new node to existing head
            CreateLine(((BoxWidget) Nodes.get(Nodes.size() - (widgetsPerNode - 1))), ((BoxWidget) Nodes.get(widgetsPerNode - 2)), -1);
            InsertState("Created link from new node to head node");

            Collections.rotate(this.Nodes, widgetsPerNode);
        }

        float hx = ((BoxWidget) Nodes.get(0)).x;
        float hy = ((BoxWidget) Nodes.get(0)).y;
        ((PointerWidget) ExtrasNodes.get(0)).UpdateLocation(((int) hx), ((int) hy) + nodeHeight);
        InsertState("Assigning head to new node");

        ReArrangeAllNode();
        ((PointerWidget) ExtrasNodes.get(0)).UpdateLocation(startX + padding, startY + nodeHeight);
        InsertState("Final State of Linked List");

        ClearIsUpdateOfNodes();
        ClearIsUpdateOfExtras();
    }

    private void ReArrangeAllNode() {
        int x = startX;
        int y = startY;

        int i  = 0;

        while(i < Nodes.size()) {
            if (BoxWidget.class.isInstance(this.Nodes.get(i))) {
                ((BoxWidget) Nodes.get(i)).UpdateNewBounds(x, y);
            }
            else if (LineWidget.class.isInstance(this.Nodes.get(i))) {
                ((LineWidget) Nodes.get(i)).UpdateNewBounds(x - pointerWidth/2, y + nodeHeight / 2, x + lineWidth, y + nodeHeight / 2);
            }

            if(i % widgetsPerNode == 0) {
                x += valueWidth;
            }
            else if(i % widgetsPerNode == 1) {
                x += pointerWidth;
            }
            else {
                x += lineWidth;
            }
            ++i;
        }
    }

    private void CreateLine(BoxWidget from, BoxWidget to, int index) {
        float x1  = from.x + pointerWidth / 2;
        float y1 = from.y + nodeHeight / 2;
        float x2 = to.x;
        float y2 = to.y + nodeHeight / 2;

        LineWidget link = new LineWidget(x1, y1, x2, y2, true, false);
        if(index == -1) {
            this.Nodes.add(link);
        }
        else {
            this.Nodes.add(index, link);
        }
    }

    private void CreateNewNode(int value) {
        BoxWidget valueNode = new BoxWidget(newNodeX, newNodeY, valueWidth, nodeHeight, "" + value, BoxWidgetTextLocation.Center);
        BoxWidget pointerNode = new BoxWidget(newNodeX+valueWidth, newNodeY, pointerWidth, nodeHeight);
        pointerNode.color = Helper.ColorLightBlue;

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

        ArrayList<BaseWidget> copyExtraNodes = new ArrayList<>();
        for(int i = 0; i < this.ExtrasNodes.size(); i++) {
            if(PointerWidget.class.isInstance(this.ExtrasNodes.get(i))) {
                copyExtraNodes.add(new PointerWidget(((PointerWidget) this.ExtrasNodes.get(i))));
            }
        }

        State e = new State(copyExtraNodes);
        this.Extras.add(e);
    }

    @Override
    public void UpdateActionView(View customView, int index) {
        switch (index) {
            case 1:
                GUIForInsertOperation(customView);
                break;
            case 2:
                GUIForDeleteOperation(customView);
                break;
            case 3:
                GUIForSearchOperation(customView);
                break;
        }
    }

    private void GUIForInsertOperation(View customView) {
        LinearLayout mainLayout = (LinearLayout)customView.findViewById(R.id.mainlayout);

        TextView info1 = new TextView(customView.getContext());
        info1.setText("Select your option");
        info1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainLayout.addView(info1);

        RadioGroup optionsGroup = new RadioGroup(customView.getContext());
        optionsGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RadioButton atTheBeginning = new RadioButton(customView.getContext());
        atTheBeginning.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        atTheBeginning.setText("Insert At Beginning");
        atTheBeginning.setId(100);
        optionsGroup.addView(atTheBeginning);

        final RadioButton atThePosition = new RadioButton(customView.getContext());
        atThePosition.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        atThePosition.setText("Insert At Position");
        atThePosition.setId(101);
        optionsGroup.addView(atThePosition);

        RadioButton atTheEnd = new RadioButton(customView.getContext());
        atTheEnd.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        atTheEnd.setText("Insert At End");
        atTheEnd.setId(102);
        optionsGroup.addView(atTheEnd);

        mainLayout.addView(optionsGroup);

        EditText v1 = new EditText(customView.getContext());
        v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        v1.setInputType(InputType.TYPE_CLASS_NUMBER);
        v1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        v1.setSingleLine();
        v1.setEms(10);
        v1.setHint("Enter value to insert");
        mainLayout.addView(v1);

        v1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dialogValue = Integer.parseInt(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText v2 = new EditText(customView.getContext());
        v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        v2.setInputType(InputType.TYPE_CLASS_NUMBER);
        v2.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        v2.setSingleLine();
        v2.setEms(6);
        v2.setHint("Enter position to insert");
        v2.setVisibility(View.INVISIBLE);
        mainLayout.addView(v2);

        v2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dialogPosition = Integer.parseInt(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == 101) {
                    v2.setVisibility(View.VISIBLE);
                    dialogOption = 101;
                }
                else if(i == 100) {
                    v2.setVisibility(View.INVISIBLE);
                    dialogOption = 100;
                }
                else if(i == 102) {
                    v2.setVisibility(View.INVISIBLE);
                    dialogOption = 102;
                }
            }
        });
    }

    private void GUIForDeleteOperation(View customView) {
        LinearLayout mainLayout = (LinearLayout)customView.findViewById(R.id.mainlayout);

        TextView info1 = new TextView(customView.getContext());
        info1.setText("Select your option");
        info1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainLayout.addView(info1);

        RadioGroup optionsGroup = new RadioGroup(customView.getContext());
        optionsGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RadioButton atTheBeginning = new RadioButton(customView.getContext());
        atTheBeginning.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        atTheBeginning.setText("Delete At Beginning");
        atTheBeginning.setId(100);
        optionsGroup.addView(atTheBeginning);

        final RadioButton atThePosition = new RadioButton(customView.getContext());
        atThePosition.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        atThePosition.setText("Delete At Position");
        atThePosition.setId(101);
        optionsGroup.addView(atThePosition);

        RadioButton atTheEnd = new RadioButton(customView.getContext());
        atTheEnd.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        atTheEnd.setText("Delete At End");
        atTheEnd.setId(102);
        optionsGroup.addView(atTheEnd);

        mainLayout.addView(optionsGroup);

        final EditText v2 = new EditText(customView.getContext());
        v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        v2.setInputType(InputType.TYPE_CLASS_NUMBER);
        v2.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        v2.setSingleLine();
        v2.setEms(6);
        v2.setHint("Enter position to insert");
        v2.setVisibility(View.INVISIBLE);
        mainLayout.addView(v2);

        v2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dialogPosition = Integer.parseInt(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == 101) {
                    v2.setVisibility(View.VISIBLE);
                    dialogOption = 101;
                }
                else if(i == 100) {
                    v2.setVisibility(View.INVISIBLE);
                    dialogOption = 100;
                }
                else if(i == 102) {
                    v2.setVisibility(View.INVISIBLE);
                    dialogOption = 102;
                }
            }
        });
    }

    private void GUIForSearchOperation(View customView) {
        LinearLayout mainLayout = (LinearLayout)customView.findViewById(R.id.mainlayout);

        EditText v1 = new EditText(customView.getContext());
        v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        v1.setInputType(InputType.TYPE_CLASS_NUMBER);
        v1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        v1.setSingleLine();
        v1.setEms(10);
        v1.setHint("Enter value to search for");
        mainLayout.addView(v1);

        v1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dialogValue = Integer.parseInt(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
