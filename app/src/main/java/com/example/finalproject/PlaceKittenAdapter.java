package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaceKittenAdapter extends RecyclerView.Adapter<PlaceKittenAdapter.ViewHolder> {

    private List<PlaceKittenInfo> mItems;

    public void setItems(List<PlaceKittenInfo> items) {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mHeightView;
        public TextView mWidthView;
        public TextView mTimestampView;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeightView = itemView.findViewById(R.id.height);
            mWidthView = itemView.findViewById(R.id.width);
            mTimestampView = itemView.findViewById(R.id.timestamp);
        }
    }

    public PlaceKittenAdapter(List<PlaceKittenInfo> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycler, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlaceKittenInfo item = mItems.get(position);
        holder.mHeightView.setText(String.valueOf(item.getHeight()));
        holder.mWidthView.setText(String.valueOf(item.getWidth()));
        holder.mTimestampView.setText(String.valueOf(item.getDate()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
