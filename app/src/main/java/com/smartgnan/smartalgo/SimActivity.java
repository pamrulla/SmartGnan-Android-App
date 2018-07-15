package com.smartgnan.smartalgo;

import android.animation.ValueAnimator;
import android.content.Intent;
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
import com.smartgnan.algorithms.LinkedLists.SingleLinkedLists;
import com.smartgnan.algorithms.Queue.Queue_sg;
import com.smartgnan.algorithms.Sort.BubbleSort;
import com.smartgnan.algorithms.Stack.Stack;
import com.smartgnan.data.AlgorithmsData;
import com.smartgnan.graphics.SimView;
import com.smartgnan.helpers.OptionType;
import com.smartgnan.helpers.Options;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    int catIndex = 0;
    int algorithmIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        Intent intent1 = getIntent();
        catIndex = intent1.getIntExtra("CatIndex", 0);
        algorithmIndex = intent1.getIntExtra("AlgorithmsIndex", 0);

        setTitle(AlgorithmsData.Categories.get(catIndex).Algorithms.get(algorithmIndex).Name);

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
        builder.setTitle("Action");

        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.input_values, null);
        Algorithm.UpdateActionView(customView, index);

        builder.setView(customView);
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
                dialog.cancel();
                Algorithm.ProcessOptions(index);
                AfterProcess();
            }
        });
    }

    public void AfterGotSize(int w, int h) {
        type = AlgorithmsData.Categories.get(catIndex).
                Algorithms.get(algorithmIndex).AlgorithmClass;
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
