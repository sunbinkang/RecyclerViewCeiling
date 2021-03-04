package com.example.recyclerviewceiling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Date: 2021/2/21
 * Author: SunBinKang
 * Description:
 */
public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> {

    private Context mContext;
    private List<Star> mList;

    public StarAdapter(Context context, List<Star> starList) {
        this.mContext = context;
        this.mList = starList;
    }

    /**
     * 当前position位置的数据是否是组的第一个item
     * @param position
     * @return
     */
    public boolean isGroupHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            String currentGroupName = getGroupName(position);
            String lastGroupName = getGroupName(position - 1);
            if (currentGroupName.equals(lastGroupName)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public String getGroupName(int position) {
        return mList.get(position).getGroupName();
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StarViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        holder.tv.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class StarViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_star);
        }
    }
}
