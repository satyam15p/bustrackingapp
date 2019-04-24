package com.example.admin.mapdemo.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.admin.mapdemo.activity.UpdateDeleteDriver;
import com.example.admin.mapdemo.model.DriverListResult;

import java.util.List;
import com.example.admin.mapdemo.R;
public class AllDriversRecyclerAdapter extends RecyclerView.Adapter<AllDriversRecyclerAdapter.MyViewHolder> {
    private List<DriverListResult> driverListResults;

    public AllDriversRecyclerAdapter(List<DriverListResult> driverListResults) {
        this.driverListResults = driverListResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drivers_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i){
        myViewHolder.mDriverName.setText(driverListResults.get(i).getUsername());
        String id = driverListResults.get(i).getUsername();
        myViewHolder.mDriverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateDeleteDriver.class);
                i.putExtra("DriverUserName", myViewHolder.mDriverName.getText().toString());
                v.getContext().startActivity(i);
                Log. e("response:", myViewHolder.mDriverName.getText().toString() );
            }
        });

    }

    @Override
    public int getItemCount() {
        return driverListResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mDriverName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDriverName = itemView.findViewById(R.id.text_Driver);
        }
    }
}
