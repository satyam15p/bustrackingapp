package com.example.admin.mapdemo.fragment;

import android.app.ProgressDialog;
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


import com.example.admin.mapdemo.activity.ViewAllDrivers;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusData;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.admin.mapdemo.R;
public class DriverRegisterFragment extends Fragment {
    public static final String TAG = DriverRegisterFragment.class.getSimpleName();
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

    public DriverRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_driver_register_fragment, container, false);
        ButterKnife.bind(this, view);
        mProgressDialog = new ProgressDialog(getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        return view;
    }

    @OnClick(R.id.add_driver)
    public void addDrivers() {

        mProgressDialog.setMessage("Adding..");
        mProgressDialog.show();
        String driverFullname = driverFullName.getText().toString();
        String phone1 = driverPhoneNumber.getText().toString();
        String phone2 = driverAlternativePhoneNumber.getText().toString();
        String age = driverAge.getText().toString();
        String address = driverAddress.getText().toString();
        String userName = driverUsername.getText().toString();
        String password = driverPassword.getText().toString();
        String driverBusId1 = driverBusId.getText().toString();

        if (!TextUtils.isEmpty(driverFullname) && !TextUtils.isEmpty(phone1) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(age)
                && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(driverBusId1)) {
            apiInterface.registerDriver(driverFullname, phone1, phone2, age, address, userName, password, driverBusId1)
                    .enqueue(new Callback<BusData>() {
                        @Override
                        public void onResponse(Call<BusData> call, Response<BusData> response) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(getContext(), String.valueOf(new Gson().toJson(response.body().getResult())), Toast.LENGTH_LONG).show();
                            Log.e(TAG + ": Response", new Gson().toJson(response.body().getResult()));
                        }

                        @Override
                        public void onFailure(Call<BusData> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "Registation Failed", Toast.LENGTH_LONG).show();
                            Log.e(TAG + ":: Error ::", String.valueOf(t));
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Please Fill all the fields", Toast.LENGTH_LONG).show();
        }
        // Toast.makeText(getContext(), "Get user methode clicked", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.viewAllDrivers)
    public void viewAllDrivers() {
        //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getContext(), ViewAllDrivers.class);
        startActivity(i);
    }
}
