package com.example.admin.mapdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.example.admin.mapdemo.R;

import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusData;
import com.example.admin.mapdemo.model.BusList;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteBus extends AppCompatActivity {

    @BindView(R.id.bus_number)
    EditText mBusNumber;
    @BindView(R.id.service_number)
    EditText mBusServiceNumber;
    @BindView(R.id.destiation)
    EditText  mDestination;
    ProgressDialog mProgressDialog;
    ApiInterface apiInterface;
    String busId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_bus);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Modify Buses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Intent i = getIntent();
        busId = i.getStringExtra("BusId");
        Log.e("Bus ID is :", busId);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getSingleBusData(busId).enqueue(new Callback<BusList>() {

            @Override
            public void onResponse(Call<BusList> call, Response<BusList> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Log.e("The responce is:", new Gson().toJson(response.body().getResult()));
                mDestination.setText(response.body().getResult().get(0).getDestination());
                mBusServiceNumber.setText(response.body().getResult().get(0).getServiceNumber());
                mBusNumber.setText(response.body().getResult().get(0).getBusId());
            }

            @Override
            public void onFailure(Call<BusList> call, Throwable t) {
                if(mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("the error is ", String.valueOf(t));
            }
        });
    }
    @OnClick(R.id.updateBus)
    public void updateBus(){
        String busId1= mBusNumber.getText().toString();
        String busServiceNumber1 = mBusServiceNumber.getText().toString();
        String busDestination1 = mDestination.getText().toString();
        if (!TextUtils.isEmpty(busId1) && !TextUtils.isEmpty(busDestination1) && !TextUtils.isEmpty(busServiceNumber1)){
            //Toast.makeText(UpdateDeleteBus.this, "Bua Id:"+busId1+"Bus servie"+busServiceNumber1+"Busdestination"+busDestination1, Toast.LENGTH_LONG).show();
            //Toast.makeText(UpdateDeleteBus.this, "Update bus is  clicked", Toast.LENGTH_LONG).show();
            mProgressDialog.show();
            apiInterface.updateBusDetail(busId1, busServiceNumber1, busDestination1).enqueue(new Callback<BusData>() {
                @Override
                public void onResponse(Call<BusData> call, Response<BusData> response) {
                    Log.e("Update response is :", new Gson().toJson(response.body().getResult()));
                    if(mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Toast.makeText(UpdateDeleteBus.this, "Updation Success..", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<BusData> call, Throwable t) {
                    Log.e("Error ", String.valueOf(t));
                    if(mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Toast.makeText(UpdateDeleteBus.this, "Updation Failed", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(UpdateDeleteBus.this, "Please Fill fields..", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.deleteBus)
    public void deleteBus(){
        mProgressDialog.show();
        Log.e("Bus is is", busId);
        apiInterface.deleteBus(busId).enqueue(new Callback<BusData>() {
            @Override
            public void onResponse(Call<BusData> call, Response<BusData> response) {
                Log.e("The delete responce is :", new Gson().toJson(response.body().getResult()));

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(UpdateDeleteBus.this, "Delete Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BusData> call, Throwable t) {
                Log.e("The delete responce is :", String.valueOf(t));
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(UpdateDeleteBus.this, "Delete Failed", Toast.LENGTH_LONG).show();
            }
        });
        // Toast.makeText(UpdateDeleteBus.this, "bus delete is clicked", Toast.LENGTH_LONG).show();
    }
}
