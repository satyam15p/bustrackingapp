package com.example.admin.mapdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mapdemo.R;
import com.example.admin.mapdemo.model.BusHistory;

import java.util.List;

public class BusHistoryRecyclerAdapter  extends  RecyclerView.Adapter<BusHistoryRecyclerAdapter.MyView> {

    private List<BusHistory>busHistories;
    public BusHistoryRecyclerAdapter(List<BusHistory> busHistories) {
        this.busHistories = busHistories;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bus_history_list, viewGroup, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {

        myView.mDate.setText(""+busHistories.get(i).getDate());
        myView.mLocationName.setText(""+busHistories.get(i).getLocationName());
        myView.mDriverName.setText(""+busHistories.get(i).getDriverName());
        Log.e("BusHistoryRecyclerVIew:",String.valueOf(i));

    }
    @Override
    public int getItemCount() {
        Log.e("HistoryAdapter", String.valueOf(busHistories.size()));
        return busHistories.size();
    }

    public class MyView extends RecyclerView.ViewHolder{

        TextView  mDate, mTime, mLocationName, mDriverName;

        public MyView(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.date);

            mLocationName = itemView.findViewById(R.id.location_name);
            mDriverName =itemView.findViewById(R.id.driver_name);
        }
    }
}
