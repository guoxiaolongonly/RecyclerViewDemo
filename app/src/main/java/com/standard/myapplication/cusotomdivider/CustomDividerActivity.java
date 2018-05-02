package com.standard.myapplication.cusotomdivider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.standard.myapplication.R;
import com.standard.myapplication.normalconfig.DisplayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <自定义分割线>
 * @date: 2018/5/2 09:10
 */

public class CustomDividerActivity extends AppCompatActivity {
    private RecyclerView rvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);
        getSupportActionBar().setTitle("自定义分割线");
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
        rvContent.addItemDecoration(new CustomDividerItemDecoration(this, CustomDividerItemDecoration.VERTICAL, R.drawable.shape_divider_drawable));
        //添加竖直分割线
        rvContent.addItemDecoration(new CustomDividerItemDecoration(this, CustomDividerItemDecoration.HORIZONTAL, R.drawable.shape_divider_drawable));
        //设置GridLayoutManager
        rvContent.setLayoutManager(gridLayoutManager);
        rvContent.setAdapter(displayAdapter);
    }
}
