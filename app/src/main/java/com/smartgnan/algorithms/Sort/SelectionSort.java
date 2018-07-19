package com.smartgnan.algorithms.Sort;

import android.content.ContentProviderOperation;
import android.os.Debug;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.graphics.State;
import com.smartgnan.helpers.Helper;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;
import com.smartgnan.smartalgo.R;
import com.smartgnan.widgets.BaseWidget;
import com.smartgnan.widgets.BoxWidget;

import java.util.ArrayList;
import java.util.Collections;

public class SelectionSort extends BaseAlgorithm {

    ArrayList<Integer> dataSet;

    public SelectionSort(int w, int h) {
        super(w, h);
    }

    @Override
    protected void UpdateOptions() {
        this.options.add(new Options("Create", OptionType.Input_Event));
    }

    @Override
    public void Init() {
        dataSet = new ArrayList<Integer>() {{
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

        this.InsertState("Initial State");
    }

    @Override
    public void ProcessOptions(int index) {
        if(index == 0) {
            dataSet.clear();
            Nodes.clear();
            States.clear();

            dataSet.addAll(options.get(index).inputs);
            this.CreateNodesForBarGraph(dataSet);
            this.InsertState("Initial State");
            Process();
        }
    }

    @Override
    public void Process() {

        ArrayList<Integer> copyDataSet = new ArrayList<>(dataSet);

        int i, j, minIdx;

        for(i = 0; i < copyDataSet.size(); i++)
        {
            for(int  k = i; k < this.Nodes.size(); k++)
            {
                this.Nodes.get(k).color = Helper.ColorRed;
            }
            if(i != 0)
            {
                this.InsertState("Start finding the minimum value from the rest of the array");
            }
            else
            {
                this.InsertState("Start finding the minimum value from the array");
            }
            for(int  k = i; k < this.Nodes.size(); k++)
            {
                if(k == i)
                {
                    this.Nodes.get(k).color = Helper.ColorBlue;
                }
                else
                {
                    this.Nodes.get(k).color = Helper.ColorOrange;
                }
            }
            minIdx = i;
            this.InsertState("Lets consider the first element as minimum");
            
            for(j = i+1; j < copyDataSet.size(); j++)
            {
                //Select Nodes for comparison
                this.Nodes.get(j).color = Helper.ColorRed;
                this.InsertState("Compare current element " + copyDataSet.get(j) + " with minimum element " + copyDataSet.get(minIdx));

                if(copyDataSet.get(minIdx) > copyDataSet.get(j))
                {
                    this.InsertState("Current element " + copyDataSet.get(j) + " is less than minimum element, hence, making current element as new minimum element");
                    
                    this.Nodes.get(j).color = Helper.ColorBlue;
                    this.Nodes.get(minIdx).color = Helper.ColorOrange;
                    this.InsertState("Current element " + copyDataSet.get(j) + " is less than minimum element, hence, making current element as new minimum element");
                    
                    minIdx = j;
                }
                else
                {
                    this.Nodes.get(j).color = Helper.ColorOrange;
                    this.InsertState("Current element " + copyDataSet.get(j) + " is greater than minimum element, hence, moving to next element");
                }
                
            }
            
            if(minIdx != i)
            {
                this.Nodes.get(i).color = Helper.ColorRed;
                InsertState("Found minimum element from the array, hence, ready to swap minimum with first element of the unsorted array");
                
                Collections.swap(copyDataSet, i, minIdx);

                //Swap Nodes
                float temp = ((BoxWidget) this.Nodes.get(i)).x;
                ((BoxWidget) this.Nodes.get(i)).UpdateNewBounds(((BoxWidget) this.Nodes.get(minIdx)).x, ((BoxWidget) this.Nodes.get(i)).y);
                ((BoxWidget) this.Nodes.get(minIdx)).UpdateNewBounds(temp, ((BoxWidget) this.Nodes.get(minIdx)).y);

                this.InsertState("Selected " + copyDataSet.get(i) + " & " + copyDataSet.get(minIdx) + " swapped.");
                Collections.swap(this.Nodes, j , j+1);

                this.Nodes.get(i).color = Helper.ColorGreen;
                this.Nodes.get(minIdx).color = Helper.ColorOrange;

                this.Nodes.get(i).isUpdated = false;
                this.Nodes.get(minIdx).isUpdated = false;
            }
            else
            {
                this.Nodes.get(minIdx).color = Helper.ColorGreen;
                InsertState("First element is the  minimum element from the unsorted array, hence, it is sorted.");
            }
        }

        //Final state of the nodes
        for(int k = 0; k < this.Nodes.size(); k++)
        {
            this.Nodes.get(k).color = Helper.ColorGreen;
        }
        this.InsertState("Sorted elements");

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
        options.get(0).inputs.clear();
        LinearLayout mainLayout = (LinearLayout)customView.findViewById(R.id.mainlayout);

        TextView info1 = new TextView(customView.getContext());
        info1.setText("Enter Values");
        info1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainLayout.addView(info1);

        LinearLayout hor = new LinearLayout(customView.getContext());
        hor.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        for(int i = 1; i <= 8; i++) {
            EditText v1 = new EditText(customView.getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f));
            v1.setInputType(InputType.TYPE_CLASS_NUMBER);
            v1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            v1.setSingleLine();
            v1.setEms(10);
            hor.addView(v1);

            v1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    options.get(0).inputs.add(Integer.parseInt(charSequence.toString()));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        mainLayout.addView(hor);
    }
}
