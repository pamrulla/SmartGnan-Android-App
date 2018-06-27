package com.smartgnan.smartalgo;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.algorithms.BubbleSort;
import com.smartgnan.graphics.SimView;

import org.w3c.dom.Text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class SimActivity extends AppCompatActivity {

    BaseAlgorithm Algorithm;
    Class<? extends BaseAlgorithm> type;
    SimView viewSim;

    int currentIndex;

    TextView stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        currentIndex = 0;

        stateText = (TextView)findViewById(R.id.stateInfo);

        final Button startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator animator = ValueAnimator.ofInt(currentIndex+1, Algorithm.getStates().size()-1);
                animator.setDuration(1000 * Algorithm.getStates().size());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int idx = (int)valueAnimator.getAnimatedValue();
                        currentIndex = idx;
                        viewSim.UpdateAnimation(idx);
                        stateText.setText(Algorithm.getStates().get(idx).info);
                        if((idx + 1) == Algorithm.getStates().size()) {
                            startButton.setEnabled(false);
                        }
                    }
                });
                animator.start();
            }
        });
    }

    public void AfterGotSize(int w, int h) {
        type = BubbleSort.class;
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
        stateText.setText(Algorithm.getStates().get(currentIndex).info);
    }
}
