package com.example.admin.mapdemo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.admin.mapdemo.R;
import com.example.admin.mapdemo.adapter.HistoryAllBuses;
import com.example.admin.mapdemo.adapter.TrackingAllBuses;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusHistory;
import com.example.admin.mapdemo.model.BusList;
import com.example.admin.mapdemo.model.BusListResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusesHistoryFragment extends Fragment {

    private  static final String TAG = BusesHistoryFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    List<BusListResult> listResults;
    ProgressDialog mProgressDialog;
    private List<BusHistory> busHistoryList;
    public BusesHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_buses_history_fragment, container, false);
        // Inflate the layout for this fragment
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.e(TAG+"::Satus::", "we are in the onCreateView  methode" );
        showList();
        return view;

    }
    public void showList(){
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
           recyclerView.setHasFixedSize(true);
           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            apiInterface.getBuses().enqueue(new Callback<BusList>() {
                @Override
                public void onResponse(Call<BusList> call, Response<BusList> response) {
                    listResults = response.body().getResult();
                    HistoryAllBuses historyAllBusesRecyclerAdapter = new HistoryAllBuses(listResults);
                    recyclerView.setAdapter(historyAllBusesRecyclerAdapter);
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

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