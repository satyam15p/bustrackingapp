package com.example.admin.mapdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.example.admin.mapdemo.activity.ViewAllBuses;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.admin.mapdemo.R;
public class BusesRegisterFragment extends Fragment {

    @BindView(R.id.bus_number)
    EditText busNumber;
    @BindView(R.id.service_number)
    EditText busServiceNumber;
    @BindView(R.id.destiation)
    EditText busDestination;


    ApiInterface apiInterface;

    public BusesRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_buses_register_fragment, container, false);
        // Inflate the layout for this fragment
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        ButterKnife.bind(this, view);

        return view;
    }
    @OnClick(R.id.add_bus)
    public  void addBusese(){
        String busNumber1 = busNumber.getText().toString();
        String busServiceNumber1 = busServiceNumber.getText().toString();
        String busDestination1 = busDestination.getText().toString();
        if(!TextUtils.isEmpty(busDestination1) && !TextUtils.isEmpty(busNumber1) && !TextUtils.isEmpty(busServiceNumber1)){
            registerBus(busNumber1, busServiceNumber1, busDestination1);
        }else{
            Toast.makeText(getContext(),"Please  fill the all fields..",Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(getContext(), "clicked", Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.viewAllBuses)
    public void viewAllBuses(){
        Intent i = new Intent(getContext(), ViewAllBuses.class);
        startActivity(i);
    }
    public  void registerBus(String  busNumber1, String busserviceNumber1, String busDestination1){
        Log.e("m ",busDestination1+""+busNumber1+busNumber1);
        apiInterface.register(busNumber1, busserviceNumber1, busDestination1).enqueue(new Callback<BusData>() {
            @Override
            public void onResponse(Call<BusData> call, Response<BusData> response) {
                Toast.makeText(getContext(), "Registration success", Toast.LENGTH_LONG).show();
                Log.e("status:", response.body().getResult());
            }

            @Override
            public void onFailure(Call<BusData> call, Throwable t) {
                Log.e("status:", String.valueOf(t));
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

}