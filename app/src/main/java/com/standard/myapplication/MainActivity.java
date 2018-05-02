package com.standard.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.standard.myapplication.itemtouchhelper.ItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);
        getSupportActionBar().setTitle("RecyclerView效果测试");
        rvContent = findViewById(R.id.rvContent);
        MainAdapter mainAdapter = new MainAdapter(this, ConstantRouter.getRouterList());
        mainAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class nextActivity = (Class) v.getTag();
                Intent intent = new Intent(MainActivity.this, nextActivity);
                startActivity(intent);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mainAdapter));
        itemTouchHelper.attachToRecyclerView(rvContent);
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvContent.setAdapter(mainAdapter);
    }

}
