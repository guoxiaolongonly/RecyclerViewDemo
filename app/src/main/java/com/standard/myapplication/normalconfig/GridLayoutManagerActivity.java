package com.standard.myapplication.normalconfig;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.standard.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <描述功能>
 * @date: 2018/5/2 09:00
 */

public class GridLayoutManagerActivity extends AppCompatActivity {
    private RecyclerView rvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("GridLayoutManager复杂布局");
        setContentView(R.layout.layout_recycler);
        rvContent = findViewById(R.id.rvContent);
        List<String> stringList = new ArrayList<>();
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        stringList.add("lucky");
        final DisplayAdapter displayAdapter = new DisplayAdapter(this, stringList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        //通过设置SpanSizeLookup来实现复杂布局
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (displayAdapter.getItemViewType(position)) {
                    case 1:
                        return 4;
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                    default:
                        return 4;
                }

            }
        });
        //添加水平分割线
        rvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //添加竖直分割线
        rvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        //设置GridLayoutManager
        rvContent.setLayoutManager(gridLayoutManager);
        rvContent.setAdapter(displayAdapter);

    }
}
