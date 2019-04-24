package com.example.admin.mapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.mapdemo.activity.AdminActivity;
import com.example.admin.mapdemo.activity.DriverActivity;
import com.example.admin.mapdemo.activity.HistoryHomeHistory;
import com.example.admin.mapdemo.activity.TrackingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Home");
    }
    @OnClick(R.id.adminLayout)
    public void adminLayout(){
        Intent i = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.driver_layout)
    public void driverLayout() {
        Intent i2 = new Intent(MainActivity.this, DriverActivity.class);
        startActivity(i2);
    }
    @OnClick(R.id.trackingLayout)
    public void trackingLayout() {
        Intent i3 = new Intent(MainActivity.this, TrackingActivity.class);
        startActivity(i3);
    }
    @OnClick(R.id.historyLaout)
    public void history(){
        Intent i4 = new Intent(MainActivity.this, HistoryHomeHistory.class);
        startActivity(i4);

    }
}
