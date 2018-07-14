package com.smartgnan.smartalgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartgnan.data.AlgorithmsData;
import com.smartgnan.graphics.SimView;

public class AlgorithmsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithms_list);

        Intent intent = getIntent();
        int i = intent.getIntExtra("Index", 0);
        setTitle(AlgorithmsData.Categories.get(i).Name);

        Button b = (Button) findViewById(R.id.simBtn);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SimActivity.class));
            }
        });
    }
}
