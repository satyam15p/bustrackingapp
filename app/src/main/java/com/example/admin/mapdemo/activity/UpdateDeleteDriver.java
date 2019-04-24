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
import com.example.admin.mapdemo.model.DriverData;
import com.example.admin.mapdemo.model.DriverList;
import com.google.android.gms.common.AccountPicker;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteDriver extends AppCompatActivity {

    public static final String TAG = UpdateDeleteDriver.class.getSimpleName();
    @BindView(R.id.driver_full_name)
    EditText driverFullName;
    @BindView(R.id.driver_phone_number)
    EditText driverPhoneNumber;
    @BindView(R.id.driver_phone_number_alternative)
    EditText driverAlternativePhoneNumber;
    @BindView(R.id.driver_age)
    EditText driverAge;
    @BindView(R.id.driver_address)
    EditText driverAddress;
    @BindView(R.id.driver_username)
    EditText driverUsername;
    @BindView(R.id.driver_password)
    EditText driverPassword;
    @BindView(R.id.driver_bus_id)
    EditText driverBusId;

    ApiInterface apiInterface;
    ProgressDialog mProgressDialog;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_delete_driver);
        ButterKnife.bind(this);
        Intent i = getIntent();
        userName = i.getStringExtra("DriverUserName");
        //Toast.makeText(UpdateDeleteDriver.this, userName, Toast.LENGTH_LONG).show();
        Log.e(TAG, "UserName"+userName);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loding..");
        mProgressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getSingleDriverData(userName).enqueue(new Callback<DriverList>() {
            @Override
            public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                Log.e(TAG, new Gson().toJson(response.body().getResult()));
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("responce:", response.body().getResult().get(0).getAddress());

                //driverFullName.setText("satyam parasa");



                driverAddress.setText(response.body().getResult().get(0).getAddress());
                driverFullName.setText(response.body().getResult().get(0).getFullName());
                driverPhoneNumber.setText(response.body().getResult().get(0).getPhone1());
                driverAlternativePhoneNumber.setText(response.body().getResult().get(0).getPhone2());
                driverAge.setText(response.body().getResult().get(0).getAge());
                driverPassword.setText(response.body().getResult().get(0).getPassword());
                driverUsername.setText(response.body().getResult().get(0).getUsername());
                driverBusId.setText(response.body().getResult().get(0).getBusNumber());
                driverUsername.setEnabled(false);
            }
            @Override
            public void onFailure(Call<DriverList> call, Throwable t) {
                Log.e(TAG, String.valueOf(t));
            }
        });
    }
    @OnClick(R.id.update)
    public void updateDriver(){
        String driverName = driverFullName.getText().toString();
        String driverAddress1 = driverAddress.getText().toString();
        String driverPhone1 = driverPhoneNumber.getText().toString();
        String driverPhone21 = "1112";
        String driverAge1 = driverAge.getText().toString();
        String driverUserName1 = driverUsername.getText().toString();
        String driverPassword1 = driverPassword.getText().toString();
        String driverBusId1 = driverBusId.getText().toString();
        if (!TextUtils.isEmpty(driverAddress1) && !TextUtils.isEmpty(driverName) && !TextUtils.isEmpty(driverPhone1)
                && !TextUtils.isEmpty(driverAge1) && !TextUtils.isEmpty(driverUserName1) && !TextUtils.isEmpty(driverPassword1)
                && !TextUtils.isEmpty(driverBusId1)){
            mProgressDialog.show();
            apiInterface.updateDriverDetail(driverName, driverPhone1, driverPhone21, driverAddress1, driverAge1, driverUserName1, driverPassword1,driverBusId1)
                    .enqueue(new Callback<BusData>() {
                        @Override
                        public void onResponse(Call<BusData> call, Response<BusData> response) {
                            Log.e("Update response is :", new Gson().toJson(response.body().getResult()));
                            if(mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(UpdateDeleteDriver.this, "Updation Success..", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<BusData> call, Throwable t) {
                            Log.e("Error ", String.valueOf(t));
                            if(mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(UpdateDeleteDriver.this, "Updation Failed", Toast.LENGTH_LONG).show();

                        }
                    });
        }else{
            Toast.makeText(UpdateDeleteDriver.this, "Please Fill all Fields..", Toast.LENGTH_LONG ).show();
        }


        Log.e(TAG, "update cliked");
    }
    @OnClick(R.id.deleteDriver)
    public void deleteDriver(){
        Log.e(TAG, "Delete clicked");
        mProgressDialog.show();
        Log.e("driver is is", userName);
        apiInterface.deleteDriver(userName).enqueue(new Callback<BusData>() {
            @Override
            public void onResponse(Call<BusData> call, Response<BusData> response) {
                Log.e("The delete responce is :", new Gson().toJson(response.body().getResult()));

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(UpdateDeleteDriver.this, "Deletion Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BusData> call, Throwable t) {
                Log.e("The delete responce is :", String.valueOf(t));
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(UpdateDeleteDriver.this, "Deletion Failed", Toast.LENGTH_LONG).show();
            }
        });
        // Toast.makeText(UpdateDeleteBus.this, "bus delete is clicked", Toast.LENGTH_LONG).show();
    }
    }

