package com.example.admin.mapdemo.activity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.mapdemo.R;
import com.example.admin.mapdemo.adapter.BusHistoryRecyclerAdapter;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusHistory;
import com.example.admin.mapdemo.model.BusHistoryResult;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusHistoryActivity extends AppCompatActivity {

    private static final String TAG =  BusHistoryActivity.class.getSimpleName();

    @BindView(R.id.bus_history_recycler_view)
    RecyclerView mRecyclerView;
    private ApiInterface apiInterface;
    private BusHistoryRecyclerAdapter busHistoryRecyclerAdapter;
    private List<BusHistory> busHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_history);
        ButterKnife.bind(this);
        Intent i  = getIntent();
        String busId = i.getStringExtra("BusId");
        Log.e(TAG+"'s  status:", busId+ "h+History");
        getSupportActionBar().setTitle(busId+"-History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        apiInterface.getBusHistory(busId).enqueue(new Callback<BusHistoryResult>() {
            @Override
            public void onResponse(Call<BusHistoryResult> call, Response<BusHistoryResult> response) {
                busHistoryList = response.body().getResult();
                busHistoryRecyclerAdapter = new BusHistoryRecyclerAdapter(busHistoryList);
                mRecyclerView.setAdapter(busHistoryRecyclerAdapter);
                Log.e(TAG+"Status:", new Gson().toJson(response.body().getResult()));
                Log.e(TAG, "Driver Name:111"+response.body().getResult().get(1).getDriverName());
                Log.e(TAG, "Time :"+response.body().getResult().get(0).getTime());
                Log.e(TAG, "Date 111:"+response.body().getResult().get(1).getDate());
                Log.e(TAG, "Lcation  Name:"+response.body().getResult().get(0).getLocationName());

        }

            @Override
            public void onFailure(Call<BusHistoryResult> call, Throwable t) {
                Log.e(TAG+"Error::",String.valueOf(t) );
                Toast.makeText(BusHistoryActivity.this, "No ResultsFound", Toast.LENGTH_LONG).show();

            }
        });






    }
}
