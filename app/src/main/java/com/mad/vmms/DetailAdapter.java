package com.mad.vmms;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ItemHolder>{

    ArrayList<Entry> list;

    public DetailAdapter(ArrayList<Entry> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DetailAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewitem, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.txtDate.setText(list.get(position).getDate());
        holder.txtPrice.setText(String.valueOf(list.get(position).getPrice()));
        holder.txtVolume.setText(String.valueOf(list.get(position).getVolume()));
        holder.txtOdometer.setText(String.valueOf(list.get(position).getOdometer()));
        holder.txtAverage.setText(String.valueOf(list.get(position).getAvg()));
        holder.txtRs.setText(String.valueOf(list.get(position).getRs()));
        if (position>1){
            float currentAvg = Float.parseFloat(list.get(position).getAvg());
            float preAvg = Float.parseFloat(list.get(position-1).getAvg());
            float currentRs = Float.parseFloat(list.get(position).getRs());
            float preRs = Float.parseFloat(list.get(position-1).getRs());
            if (currentAvg < preAvg && currentRs > preRs){
                holder.txtDate.setTextColor(Color.RED);
                holder.txtAverage.setTextColor(Color.RED);
                holder.txtRs.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtPrice, txtVolume, txtOdometer, txtAverage, txtRs;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtVolume = itemView.findViewById(R.id.txtVolume);
            txtOdometer = itemView.findViewById(R.id.txtOdometer);
            txtAverage = itemView.findViewById(R.id.txtAverage);
            txtRs = itemView.findViewById(R.id.txtRs);
        }
    }
}
