package com.standard.myapplication.customlayoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.standard.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <描述功能>
 * @date: 2018/5/2 10:24
 */

public class CustomLayoutManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("自定义LayoutManager");
        setContentView(R.layout.layout_recycler);
        RecyclerView rvContent = findViewById(R.id.rvContent);
        ImageAdapter imageAdapter = new ImageAdapter(this, getImageList());
        rvContent.setAdapter(imageAdapter);
//        rvContent.setLayoutManager(new CustomLayoutManager(dip2px(250)));
        rvContent.setLayoutManager(new MyLayoutManager());
    }


    /**
     * dp转像素
     *
     * @param dpValue
     * @return
     */
    public final int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private List<Integer> getImageList() {
        List<Integer> imageReses = new ArrayList<>();
        imageReses.add(R.drawable.img1);
        imageReses.add(R.drawable.img2);
        imageReses.add(R.drawable.img3);
        imageReses.add(R.drawable.img1);
        imageReses.add(R.drawable.img2);
        imageReses.add(R.drawable.img3);
        imageReses.add(R.drawable.img1);
        imageReses.add(R.drawable.img2);
        imageReses.add(R.drawable.img3);
        imageReses.add(R.drawable.img1);
        imageReses.add(R.drawable.img2);
        imageReses.add(R.drawable.img3);
        return imageReses;
    }
}
