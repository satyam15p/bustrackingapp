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
import com.example.admin.mapdemo.model.DriverList;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverActivity extends AppCompatActivity {

    private static  final String  TAG = DriverActivity.class.getSimpleName();
    @BindView(R.id.driver_username)
    EditText mUserName;
    @BindView(R.id.driver_password)
    EditText mPassword;
    String userName;
    String password;
    private ProgressDialog mProgressDialog;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Driver");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Login...");




    }
    @OnClick(R.id.login_Button_driver)
    public void driverLogin(){
        userName = mUserName.getText().toString();
        password = mPassword.getText().toString();
        Log.e(TAG, "Button clicked");
        Log.e(TAG, userName+password +"nulll");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){
            mProgressDialog.show();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getSingleDriverData1(userName, password).enqueue(new Callback<DriverList>() {
                @Override
                public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                    if(mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.e(TAG, new Gson().toJson(response.body().getResult()));
                    Intent intent = new Intent(DriverActivity.this, DriverLocationUpdates.class);
                    Bundle extras = new Bundle();
                    extras.putString("driverName", String.valueOf(response.body().getResult().get(0).getFullName()));
                    extras.putString("busId", String.valueOf(response.body().getResult().get(0).getBusNumber()));
                    intent.putExtras(extras);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<DriverList> call, Throwable t) {
                    Log.e(TAG, String.valueOf(t));
                    mProgressDialog.dismiss();
                    Toast.makeText(DriverActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(DriverActivity.this, "Please fill all fields..", Toast.LENGTH_LONG).show();
        }

    }
}
