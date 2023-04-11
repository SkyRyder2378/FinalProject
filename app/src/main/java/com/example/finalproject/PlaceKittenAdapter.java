package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class PlaceKittenAdapter extends RecyclerView.Adapter<PlaceKittenAdapter.ViewHolder> {

    private List<PlaceKittenInfo> mItems;
    private final LayoutInflater layoutInflater;
    private Context mContext;

    public PlaceKittenAdapter(Context context) {
        layoutInflater =LayoutInflater.from(context);
        mContext = context;
    }

    public void setItems(List<PlaceKittenInfo> items) {
        mItems = items;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mHeightView;
        public TextView mWidthView;
        public TextView mTimestampView;

        public TextView noteView;
        private int mPosition;
        public ViewHolder(View itemView) {
            super(itemView);
            mHeightView = itemView.findViewById(R.id.height);
            mWidthView = itemView.findViewById(R.id.width);
            mTimestampView = itemView.findViewById(R.id.timestamp);
        }
        public void setData(PlaceKittenInfo item) {
            mHeightView.setText(String.valueOf(item.getHeight()));
            mWidthView.setText(String.valueOf(item.getWidth()));
            mTimestampView.setText(String.valueOf(item.getDate()));
        }


    }

    public PlaceKittenAdapter(List<PlaceKittenInfo> items, LayoutInflater layoutInflater) {
        mItems = items;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Objects.requireNonNull(holder, "ViewHolder cannot be null");
        PlaceKittenInfo item = Objects.requireNonNull(mItems.get(position), "PlaceKittenInfo item cannot be null");
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        if(mItems != null) {
            return mItems.size();
        } else {
            return 0;
        }
    }

}
