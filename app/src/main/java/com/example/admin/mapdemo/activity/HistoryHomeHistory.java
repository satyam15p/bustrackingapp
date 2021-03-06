package com.example.admin.mapdemo.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.mapdemo.R;
import com.example.admin.mapdemo.adapter.AllBusesRecyclerAdapter;
import com.example.admin.mapdemo.adapter.HistoryAllBuses;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusList;
import com.example.admin.mapdemo.model.BusListResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryHomeHistory extends AppCompatActivity {


    @BindView(R.id.recyclerViewHistory)
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    List<BusListResult> listResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_home_history);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Buses List");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        apiInterface.getBuses().enqueue(new Callback<BusList>() {
            @Override
            public void onResponse(Call<BusList> call, Response<BusList> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                // Log.e("response is", response.body().getResult().get(1).getBusId());
                // Log.e("Response is :", new Gson().toJson(response.body().getResult()));
                listResults = response.body().getResult();
                HistoryAllBuses allBusesRecyclerAdapter = new HistoryAllBuses(listResults);
                recyclerView.setAdapter(allBusesRecyclerAdapter);
            }
            @Override
            public void onFailure(Call<BusList> call, Throwable t) {
                Log.e("error is:", String.valueOf(t));
                Log.e("The erroe messge  is :", String.valueOf(t));
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
    }
}
