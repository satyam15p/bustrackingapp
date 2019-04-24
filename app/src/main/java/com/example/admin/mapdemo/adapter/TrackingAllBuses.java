package com.example.admin.mapdemo.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.admin.mapdemo.R;
import com.example.admin.mapdemo.MapsActivity;
import com.example.admin.mapdemo.activity.Test;
import com.example.admin.mapdemo.model.BusListResult;

import java.util.List;

public class TrackingAllBuses extends RecyclerView.Adapter<TrackingAllBuses.MyViewHolder> {


    private List<BusListResult> listResults;

    public TrackingAllBuses(List<BusListResult> listResults) {
        this.listResults = listResults;
    }

    @NonNull
    @Override
    public TrackingAllBuses.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.buses_list, viewGroup, false);
        return new TrackingAllBuses.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final TrackingAllBuses.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(listResults.get(i).getBusId());
        String id = listResults.get(i).getBusId();
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MapsActivity.class);
                i.putExtra("BusId", myViewHolder.textView.getText().toString());
                v.getContext().startActivity(i);
                Log. e("response:", myViewHolder.textView.getText().toString() );
            }
        });

    }

    @Override
    public int getItemCount() {
        return listResults.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }


    }
}


