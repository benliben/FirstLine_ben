package com.example.benben.firstline.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.benben.firstline.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

/**
 * Created by beneben on 2016/6/1.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<String> dataList = new ArrayList<>();
    private OnItemClickListener mListener;

    public WeatherAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTx.setText(dataList.get(position));

        if (mListener != null) {
            holder.mTx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    mListener.ItemClickListener(holder.itemView, pos);
                }
            });
        }
        if (mListener != null) {
            holder.mTx.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos=holder.getAdapterPosition();//得到item对象的位置
                    mListener.ItemLongClickListener(holder.itemView,pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTx;
        public ViewHolder(View itemView) {
            super(itemView);
            mTx = (TextView) itemView.findViewById(R.id.text);
        }
    }
    /**添加点击事件*/
    public interface OnItemClickListener{
        void ItemClickListener(View view, int position);

        void ItemLongClickListener(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
