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

public class BubbleSort extends BaseAlgorithm {

    ArrayList<Integer> dataSet;

    public BubbleSort(int w, int h) {
        super("Bubble Sort", w, h);
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

        boolean isSwapped = false;

        int i, j;

        for(i = 0; i < copyDataSet.size(); i++)
        {
            isSwapped = false;

            for(j = 0; j < copyDataSet.size() - i - 1; j++)
            {
                //Select Nodes for comparison
                this.Nodes.get(j).color = Helper.ColorRed;
                this.Nodes.get(j+1).color = Helper.ColorRed;
                this.InsertState("Selected " + copyDataSet.get(j) + " & " + copyDataSet.get(j+1) + " for comparison.");

                if(copyDataSet.get(j) > copyDataSet.get(j+1))
                {
                    this.InsertState("Selected " + copyDataSet.get(j) + " & " + copyDataSet.get(j+1) + " are not in order, hence need to swap.");

                    //swap values
                    Collections.swap(copyDataSet, j, j+1);

                    //Swap Nodes
                    float temp = ((BoxWidget) this.Nodes.get(j)).x;
                    ((BoxWidget) this.Nodes.get(j)).UpdateNewBounds(((BoxWidget) this.Nodes.get(j+1)).x, ((BoxWidget) this.Nodes.get(j)).y);
                    ((BoxWidget) this.Nodes.get(j+1)).UpdateNewBounds(temp, ((BoxWidget) this.Nodes.get(j+1)).y);

                    this.InsertState("Selected " + copyDataSet.get(j) + " & " + copyDataSet.get(j+1) + " swapped.");
                    Collections.swap(this.Nodes, j , j+1);

                    this.Nodes.get(j).color = Helper.ColorOrange;
                    this.Nodes.get(j+1).color = Helper.ColorOrange;

                    this.Nodes.get(j).isUpdated = false;
                    this.Nodes.get(j+1).isUpdated = false;

                    this.InsertState("Selected " + copyDataSet.get(j) + " & " + copyDataSet.get(j+1) + " swapped.");

                    isSwapped = true;

                }
                else
                {
                    //Deselect nodes
                   this.Nodes.get(j).isUpdated = false;
                    this.Nodes.get(j+1).isUpdated = false;
                    this.InsertState("Selected " + copyDataSet.get(j) + " & " + copyDataSet.get(j+1) + " are in order, hence no need to swap.");

                    this.Nodes.get(j).color = Helper.ColorOrange;
                    this.Nodes.get(j+1).color = Helper.ColorOrange;

                    this.Nodes.get(j).isUpdated = false;
                    this.Nodes.get(j+1).isUpdated = false;

                    this.InsertState("Selected " + copyDataSet.get(j) + " & " + copyDataSet.get(j+1) + " are in order, hence no need to swap.");
                }
            }

            if(!isSwapped)
            {
                //Update state for no swapping done
                this.InsertState("No swapping done in single iteration, hence elements are sorted");
                break;
            }
            else
            {
                this.Nodes.get(j).color = Helper.ColorGreen;
                this.InsertState("The element " + copyDataSet.get(j) + " is sorted");
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
