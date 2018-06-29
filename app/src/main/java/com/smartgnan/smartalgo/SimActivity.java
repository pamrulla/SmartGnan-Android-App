package com.smartgnan.smartalgo;

import android.animation.ValueAnimator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.algorithms.Queue.Queue_sg;
import com.smartgnan.algorithms.Sort.BubbleSort;
import com.smartgnan.algorithms.Stack.Stack;
import com.smartgnan.graphics.SimView;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.function.ToIntFunction;

public class SimActivity extends AppCompatActivity {

    BaseAlgorithm Algorithm = null;
    Class<? extends BaseAlgorithm> type;
    SimView viewSim;

    int currentIndex;
    boolean isPlay = true;

    TextView stateText;
    ValueAnimator animator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        currentIndex = 0;

        stateText = (TextView)findViewById(R.id.stateInfo);

        final ImageButton actionSheetButton = (ImageButton)findViewById(R.id.actionSheetButton);
        actionSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Algorithm == null) {
                    return;
                }

                ArrayList<String> titles = new ArrayList<>();
                for(Options o : Algorithm.options) {
                    titles.add(o.text);
                }
                ActionSheet.createBuilder(view.getContext(), getSupportFragmentManager())
                        .setCancelButtonTitle("Cancel")
                        .setOtherButtonTitles(titles.toArray(new String[0]))
                        .setCancelableOnTouchOutside(true)
                        .setListener(new ActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                            }

                            @Override
                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                ProcessSelectedOption(index);
                            }
                        }).show();
            }
        });

        final ImageButton prevButton = (ImageButton)findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Algorithm == null || Algorithm.States.size() <= 0 ) {
                    return;
                }
                animator.end();
                --currentIndex;
                if(currentIndex < 0) {
                    currentIndex = 0;
                    return;
                }
                viewSim.UpdateAnimation(currentIndex);
                stateText.setText(Algorithm.getStates().get(currentIndex).info);
            }
        });

        final ImageButton playButton = (ImageButton)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlay) {
                    if(animator.isPaused()) {
                        animator.resume();
                    }
                    else {
                        animator.start();
                    }
                }
                else {
                    animator.pause();
                }
                isPlay = !isPlay;
                if(isPlay) {
                    playButton.setImageResource(R.drawable.play_button);
                }
                else {
                    playButton.setImageResource(R.drawable.pause_button);
                }
            }
        });

        final ImageButton nextButton = (ImageButton)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Algorithm == null || Algorithm.States.size() <= 0 ) {
                    return;
                }
                animator.cancel();
                ++currentIndex;
                if(currentIndex >= Algorithm.States.size()) {
                    currentIndex = Algorithm.States.size() - 1;
                    return;
                }
                viewSim.UpdateAnimation(currentIndex);
                stateText.setText(Algorithm.getStates().get(currentIndex).info);
            }
        });
    }

    private void ProcessSelectedOption(int index) {
        if(Algorithm.options.get(index).type == OptionType.Input_Event)
        {
            openDialogBox(index);
        }
        else
        {
            Algorithm.ProcessOptions(index);
            AfterProcess();
        }
    }

    private void openDialogBox(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input values");

        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.input_values, null);

        builder.setView(customView);

        final EditText v1 = (EditText)customView.findViewById(R.id.v1);
        final EditText v2 = (EditText)customView.findViewById(R.id.v2);
        final EditText v3 = (EditText)customView.findViewById(R.id.v3);
        final EditText v4 = (EditText)customView.findViewById(R.id.v4);
        final EditText v5 = (EditText)customView.findViewById(R.id.v5);
        final EditText v6 = (EditText)customView.findViewById(R.id.v6);
        final EditText v7 = (EditText)customView.findViewById(R.id.v7);
        final EditText v8 = (EditText)customView.findViewById(R.id.v8);

        v1.setNextFocusForwardId(R.id.v2);
        v2.setNextFocusForwardId(R.id.v3);
        v3.setNextFocusForwardId(R.id.v4);
        v4.setNextFocusForwardId(R.id.v5);
        v5.setNextFocusForwardId(R.id.v6);
        v6.setNextFocusForwardId(R.id.v7);
        v7.setNextFocusForwardId(R.id.v8);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Button cancelButton = (Button)customView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        Button submitButton = (Button)customView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Algorithm.options.get(index).inputs.clear();
                if(v1.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v1.getText().toString()));
                }
                if(v2.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v2.getText().toString()));
                }
                if(v3.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v3.getText().toString()));
                }
                if(v4.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v4.getText().toString()));
                }
                if(v5.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v5.getText().toString()));
                }
                if(v6.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v6.getText().toString()));
                }
                if(v7.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v7.getText().toString()));
                }
                if(v8.getText().toString().length() > 0) {
                    Algorithm.options.get(index).inputs.add(Integer.parseInt(v8.getText().toString()));
                }
                dialog.cancel();
                Algorithm.ProcessOptions(index);
                AfterProcess();
            }
        });
    }

    public void AfterGotSize(int w, int h) {
        type = Queue_sg.class;
        try {
            try {
                Constructor cns = type.getConstructor(new Class[] { int.class, int.class});
                try {
                    Algorithm = (BaseAlgorithm) cns.newInstance(w, h);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        viewSim = (SimView) findViewById(R.id.viewSim);
        viewSim.SetAlgorithmReference(this.Algorithm);

        Algorithm.Process();
        AfterProcess();
    }

    private void AfterProcess() {
        currentIndex = 0;
        viewSim.SetCurrentIndex(currentIndex);
        PrepareAnimator();
        if(Algorithm.States.size() > 0) {
            stateText.setText(Algorithm.getStates().get(currentIndex).info);
        }
    }

    private void PrepareAnimator() {
        if(animator != null) {
            animator.removeAllUpdateListeners();
            animator.setIntValues(currentIndex+1, Algorithm.getStates().size()-1);
            animator.setDuration(1000 * Algorithm.getStates().size());
        }
        else {
            animator = ValueAnimator.ofInt(currentIndex+1, Algorithm.getStates().size()-1);
            animator.setDuration(1000 * Algorithm.getStates().size());
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int idx = (int)valueAnimator.getAnimatedValue();
                Log.i("Khan", "" +idx);
                currentIndex = idx;
                viewSim.UpdateAnimation(idx);
                stateText.setText(Algorithm.getStates().get(idx).info);
            }
        });
    }
}
