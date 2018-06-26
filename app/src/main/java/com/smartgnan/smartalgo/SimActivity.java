package com.smartgnan.smartalgo;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartgnan.algorithms.BaseAlgorithm;
import com.smartgnan.algorithms.BubbleSort;
import com.smartgnan.graphics.SimView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class SimActivity extends AppCompatActivity {

    BaseAlgorithm Algorithm;
    Class<? extends BaseAlgorithm> type;
    SimView viewSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        Button startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSim.UpdateAnimation();
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
    }
}
