package com.example.recyclerviewceiling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Star> mStarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StarDecoration(this));
        recyclerView.setAdapter(new StarAdapter(this, mStarList));
    }

    private void init() {
        mStarList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int i1 = 0; i1 < 20; i1++) {
                if (i % 2 == 0) {
                    mStarList.add(new Star("何炅" + i1, "快乐家族" + i));
                } else {
                    mStarList.add(new Star("汪涵" + i1, "天天兄弟" + i));
                }
            }
        }
    }
}
