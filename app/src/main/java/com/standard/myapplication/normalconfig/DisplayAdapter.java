package com.standard.myapplication.normalconfig;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.standard.myapplication.R;

import java.util.List;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <描述功能>
 * @date: 2018/4/27 16:46
 */

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder> {
    private List<String> datas;
    private Context mContext;

    public DisplayAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_header, parent, false));
            case 2:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_content, parent, false));
            case 3:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_bottom, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(datas.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 1) {
            return 1;
        } else if (position < 9) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvText);
        }

        public void setData(String s, final int position) {
            tvContent.setText(s);
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
