package com.krunal.locationexample.Utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krunal.locationexample.Database.LogsEntity;
import com.krunal.locationexample.R;

import java.util.ArrayList;
import java.util.List;

public class LogsRecycleView extends RecyclerView.Adapter<LogsRecycleView.ViewHolder> {

    private List<LogsEntity> mList = new ArrayList<>();
    private LayoutInflater mInflater;

    public LogsRecycleView(Context context){
        this.mInflater = LayoutInflater.from(context);
    }

    public void add(List<LogsEntity> list){
        this.mList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LogsEntity current = mList.get(i);
        viewHolder.mlatitude.setText("latitude: " + current.getLatitude());
        viewHolder.mlongitude.setText("longitude:" + current.getLongitude());
        viewHolder.date_time.setText("Date Time: " + current.getDateTime());
        viewHolder.location_status.setText("Location Status: " + current.getLocation_Status());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mlatitude,mlongitude,date_time,location_status;

        ViewHolder(View itemView) {
            super(itemView);
            mlatitude = itemView.findViewById(R.id.latitude);
            mlongitude = itemView.findViewById(R.id.longitude);
            date_time = itemView.findViewById(R.id.date_time);
            location_status = itemView.findViewById(R.id.location_status);

        }


    }
}
