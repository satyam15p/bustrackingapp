package com.example.admin.mapdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



import com.example.admin.mapdemo.R;
public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setTitle("Test Actiivity");
    }
}
