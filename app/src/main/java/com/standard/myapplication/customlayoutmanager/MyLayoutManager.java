package com.standard.myapplication.customlayoutmanager;

import android.graphics.Rect;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 *          自定义LayoutManager主要需要实现以下几个方法
 *          1.{@link #generateDefaultLayoutParams()} 这个方法用来配置默认的子View的Params
 *          2.{@link #onLayoutChildren(RecyclerView.Recycler, RecyclerView.State)}这个方法用来对item做布局
 *          3.{@link #canScrollHorizontally()} {@link #canScrollVertically()} 是否能够水平，竖直滑动
 *          4.{{@link #scrollHorizontallyBy(int, RecyclerView.Recycler, RecyclerView.State)}}
 *          {@link #scrollVerticallyBy(int, RecyclerView.Recycler, RecyclerView.State)}
 *          在竖直和水平方向滚动时的回调
 * @function <描述功能>
 * @date: 2018/5/2 10:24
 */
public class MyLayoutManager extends RecyclerView.LayoutManager {

    //保存所有item的偏移信息
    private SparseArrayCompat<Rect> itemFrames = new SparseArrayCompat<>();
    //总的高度和宽度
    private int mTotalHeight;
    private int mTotalWidth;

    private int verticalOffset;//竖直方向的偏移
    private int horizontalOffset;//水平方向的偏移

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置子View位置
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        detachAndScrapAttachedViews(recycler);

        int totalHeight = 0;
        int totalWidth = 0;
        int offsetX = 0;
        int offsetY = 0;
        //计算每个item的位置信息,存储在itemFrames里面
        for (int i = 0; i < getItemCount(); i++) {
            //从缓存中取出
            View view = recycler.getViewForPosition(i);
            //添加到RecyclerView中
            addView(view);
            //测量
            measureChildWithMargins(view, 0, 0);
            //获取测量后的宽高
            int height = getDecoratedMeasuredHeight(view);
            int width = getDecoratedMeasuredWidth(view);
            //把每一个子View的宽高加起来获得总的
            totalHeight += height;
            totalWidth += width;
            //边界信息保存到Rect里面
            Rect rect = itemFrames.get(i);
            if (rect == null) {
                rect = new Rect();
            }

            rect.set(offsetX, offsetY, offsetX + width, offsetY + height);
            itemFrames.put(i, rect);
            //横竖方向的偏移
            offsetX += width;
            offsetY += height;
        }
        mTotalHeight = Math.max(totalHeight, getVerticalSpace());
        mTotalWidth = Math.max(totalWidth, getHorizontalSpace());

        fill(recycler, state);

    }

    //回收不必要的view（超出屏幕的），取出需要的显示出来
    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //获得屏幕的边界信息
        Rect displayFrame = new Rect(horizontalOffset, verticalOffset, horizontalOffset + getHorizontalSpace(),
                verticalOffset + getVerticalSpace());

        //滑出屏幕回收到缓存中
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            childFrame.left = getDecoratedLeft(view);
            childFrame.top = getDecoratedTop(view);
            childFrame.right = getDecoratedRight(view);
            childFrame.bottom = getDecoratedBottom(view);
            //判断是否在显示区域里面
            if (!Rect.intersects(displayFrame, childFrame)) {
                removeAndRecycleView(view, recycler);
            }
        }
        //在屏幕上显示出
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayFrame, itemFrames.get(i))) {//判断是否在屏幕中
                View view = recycler.getViewForPosition(i);
                measureChildWithMargins(view, 0, 0);
                addView(view);
                Rect rect = itemFrames.get(i);
                layoutDecorated(view, rect.left - horizontalOffset, rect.top - verticalOffset,
                        rect.right - horizontalOffset, rect.bottom - verticalOffset);
            }
        }


    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (verticalOffset + dy < 0) {//滑动到最顶部
            dy = -verticalOffset;
        } else if (verticalOffset + dy > mTotalHeight - getVerticalSpace()) {//滑动到底部
            dy = mTotalHeight - getVerticalSpace() - verticalOffset;
        }

        offsetChildrenVertical(-dy);
        fill(recycler, state);
        verticalOffset += dy;
        return dy;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (horizontalOffset + dx < 0) {//滑动到最左边
            dx = -horizontalOffset;
        } else if (horizontalOffset + dx > mTotalWidth - getHorizontalSpace()) {//滑动到最右边
            dx = mTotalWidth - getHorizontalSpace() - horizontalOffset;
        }

        offsetChildrenHorizontal(-dx);
        fill(recycler, state);
        horizontalOffset += dx;
        return dx;
    }

    //获取控件的竖直高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    //获取控件的水平宽度
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
