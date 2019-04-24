package com.example.admin.mapdemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.admin.mapdemo.adapter.AllDriversRecyclerAdapter;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.DriverList;
import com.example.admin.mapdemo.model.DriverListResult;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.admin.mapdemo.R;

public class ViewAllDrivers extends AppCompatActivity {

    @BindView(R.id.recyclerViewDrivers)
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    List<DriverListResult> listResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_drivers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Drivers List");
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    final    ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        apiInterface.getDrivers().enqueue(new Callback<DriverList>() {
            @Override
            public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                // Log.e("response is", response.body().getResult().get(1).getBusId());
                Log.e("Response is :", new Gson().toJson(response.body().getResult()));
                listResults = response.body().getResult();
                AllDriversRecyclerAdapter allDriversRecyclerAdapter = new AllDriversRecyclerAdapter(listResults);
                //AllBusesRecyclerAdapter allBusesRecyclerAdapter = new AllBusesRecyclerAdapter(listResults);
                recyclerView.setAdapter(allDriversRecyclerAdapter);
            }
            @Override
            public void onFailure(Call<DriverList> call, Throwable t) {
                Log.e("error is:", String.valueOf(t));
                Log.e("The erroe messge  is :", String.valueOf(t));
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
    }
}

