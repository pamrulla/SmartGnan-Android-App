package com.smartgnan.algorithms.Stack;

import android.graphics.Color;
import android.graphics.Point;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.graphics.State;
import com.smartgnan.helpers.Helper;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;
import com.smartgnan.smartalgo.R;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;
import com.smartgnan.widgets.PathWidget;

import java.util.ArrayList;

public class Stack extends BaseAlgorithm {

    int nodeWidth;
    int nodeHeight;
    int startX;
    int startY;
    int maxCount = 8;

    ArrayList<Integer> dataset;
    int dialogValue;

    public Stack(int w, int h) {
        super("Stack", w, h);

    }

    @Override
    protected void UpdateOptions() {
        options.add(new Options("Push", OptionType.Input_Event));
        options.add(new Options("Pop", OptionType.Event));
        options.add(new Options("Top/Peek", OptionType.Event));
        options.add(new Options("IsEmpty", OptionType.Event));
    }

    @Override
    public void Init() {
        dataset = new ArrayList<>();

        maxCount = 5;
        int screenCenter = super.screenWidth / 4;
        startX = (screenWidth/2) - (screenCenter/2) + padding;
        startY = padding;
        nodeWidth = screenCenter - super.padding - padding;
        nodeHeight = (halfHeight / (maxCount+2)) - padding;

        PathWidget stackContainer = new PathWidget();
        stackContainer.points.add(new Point((screenWidth/2) - (screenCenter/2), nodeHeight));
        stackContainer.points.add(new Point((screenWidth/2) - (screenCenter/2), halfHeight - padding));
        stackContainer.points.add(new Point((screenWidth/2) + (screenCenter/2), halfHeight-padding));
        stackContainer.points.add(new Point((screenWidth/2) + (screenCenter/2), nodeHeight));

        this.Background.add(stackContainer);

        InsertState("Initial Position");
    }

    @Override
    public void ProcessOptions(int index) {
        switch (index) {
            case 0: //Push
                Push(dialogValue);
                break;
            case 1: //Pop
                Pop();
                break;
            case 2: //Peek
                Peek();
                break;
            case 3: //IsEmpty
                IsEmpty();
                break;
            case 4: //Size
                Size();
                break;
            default:
                break;
        }
    }

    private void Size() {
        States.clear();
        int size = Nodes.size();
        InsertState("The size of Stack is " + size);
    }
    private void IsEmpty() {
        States.clear();
        boolean isEmpty = Nodes.size() == 0;
        InsertState("The Stack is " + (isEmpty ? "" : "not") + " empty.");
    }

    private void Peek() {
        States.clear();

        InsertState("Checking whether Stack is empty or not");

        if(Nodes.isEmpty()) {
            IsEmpty();
            return;
        }
        else
        {
            IsEmpty();
        }

        int idx = Nodes.size() - 1;
        Nodes.get(idx).color = Helper.ColorRed;
        InsertState("Selecting the top item of the Stack");

        String val = ((BoxWidget) (Nodes.get(idx))).text;
        Nodes.get(idx).color = Helper.ColorOrange;
        InsertState("The top of the Stack is " + val);
    }
    private void Pop() {
        States.clear();
        InsertState("Checking whether Stack is empty or not");

        if(Nodes.isEmpty()) {
            IsEmpty();
            return;
        }
        else
        {
            IsEmpty();
        }

        int idx = Nodes.size() - 1;
        Nodes.get(idx).color = Helper.ColorRed;
        InsertState("Selecting the top item of the Stack");

        ((BoxWidget) Nodes.get(idx)).y = -nodeHeight-padding;
        Nodes.get(idx).isUpdated = true;
        InsertState("Removing the top item from the Stack");

        Nodes.remove(idx);
        InsertState("Final State after POP operation");
    }

    private void Push(Integer integer) {
        States.clear();
        InsertState("Initial State");
        BoxWidget newNode = new BoxWidget();
        newNode.text = integer.toString();
        newNode.SetBounds(startX, startY, nodeWidth, nodeHeight);
        newNode.color = Helper.ColorRed;
        Nodes.add(newNode);
        InsertState("New item created");

        int idx = Nodes.size()-1;
        float newY = halfHeight - ((nodeHeight-padding) * (Nodes.size() + 1));
        ((BoxWidget) Nodes.get(idx)).UpdateNewBounds(((BoxWidget) Nodes.get(idx)).x, newY);
        InsertState("Placing the new item at the top of the stack");

        Nodes.get(idx).color = Helper.ColorOrange;
        Nodes.get(idx).isUpdated = false;
        InsertState("Final state after PUSH operation");
    }

    @Override
    public void Process() {

    }

    @Override
    public void InsertState(String info) {
        ArrayList<BaseWidget> copyNodes = new ArrayList<>();

        for(int i = 0; i < this.Nodes.size(); i++) {
            copyNodes.add(new BoxWidget(((BoxWidget) this.Nodes.get(i))));
        }

        State s = new State(copyNodes);
        s.info = info;
        this.States.add(s);
    }

    @Override
    public void UpdateActionView(View customView, int index) {
        LinearLayout mainLayout = (LinearLayout)customView.findViewById(R.id.mainlayout);

        EditText v1 = new EditText(customView.getContext());
        v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        v1.setInputType(InputType.TYPE_CLASS_NUMBER);
        v1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        v1.setSingleLine();
        v1.setEms(10);
        v1.setHint("Enter value to Push");
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
