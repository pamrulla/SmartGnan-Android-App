package com.smartgnan.smartalgo;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartgnan.data.AlgorithmDetails;
import com.smartgnan.data.AlgorithmsData;
import com.smartgnan.graphics.SimView;

import org.w3c.dom.Text;

public class AlgorithmsListActivity extends AppCompatActivity {

    int catIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithms_list);

        Intent intent = getIntent();
        catIndex = intent.getIntExtra("Index", 0);
        setTitle(AlgorithmsData.Categories.get(catIndex).Name);

        ListView lw = (ListView)findViewById(R.id.algorithmslist);
        CustomList adp = new CustomList();
        lw.setAdapter(adp);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean isPurchased = false;
                if(AlgorithmsData.Categories.get(catIndex).Algorithms.get(i).IsFree) {
                    isPurchased = true;
                }
                else {
                    isPurchased = false;
                }
                if(isPurchased) {
                    Intent intent = new Intent(view.getContext(), SimActivity.class);
                    intent.putExtra("CatIndex", catIndex);
                    intent.putExtra("AlgorithmsIndex", i);
                    startActivity(intent);
                }
            }
        });

    }

    public class CustomList extends BaseAdapter {


        @Override
        public int getCount() {
            return AlgorithmsData.Categories.get(catIndex).Algorithms.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView = inflater.inflate(R.layout.custom_algorithms_list_item, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.listItemTitle);
            txtTitle.setText(AlgorithmsData.Categories.get(catIndex).Algorithms.get(position).Name);

            ImageView lock = (ImageView) rowView.findViewById(R.id.unlocked);
            ImageView locked = (ImageView) rowView.findViewById(R.id.locked);
            if(AlgorithmsData.Categories.get(catIndex).Algorithms.get(position).IsFree) {
                lock.setVisibility(View.VISIBLE);
                locked.setVisibility(View.INVISIBLE);
            }
            else {
                lock.setVisibility(View.INVISIBLE);
                locked.setVisibility(View.VISIBLE);
            }
            return rowView;
        }
    }
}
