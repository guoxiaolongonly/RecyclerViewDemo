package com.standard.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.standard.myapplication.itemtouchhelper.ItemTouchHelperCallback;

import java.util.List;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <描述功能>
 * @date: 2018/5/2 09:30
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements ItemTouchHelperCallback.OnItemPositionChangeListener {
    private Context mContext;
    private List<Pair<String, Class>> mRouterSource;


    private View.OnClickListener mOnItemClickListener;

    public MainAdapter(Context context, List<Pair<String, Class>> routerSource) {
        mContext = context;
        mRouterSource = routerSource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mRouterSource.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mRouterSource.size();
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public boolean onItemMove(int fromPos, int toPos) {
        Pair classInfo = mRouterSource.get(fromPos);
        mRouterSource.remove(fromPos);
        mRouterSource.add(toPos, classInfo);
        notifyItemMoved(fromPos, toPos);
        return false;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView;
        }

        public void setData(final Pair<String, Class> s, final int position) {
            tvContent.setText(s.first);
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        v.setTag(s.second);
                        mOnItemClickListener.onClick(v);
                    }
                }
            });
        }
    }
}
