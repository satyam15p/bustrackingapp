package com.example.admin.mapdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.mapdemo.*;
import com.example.admin.mapdemo.R;


public class SplashScreenActivity extends AppCompatActivity {
    public static final int SPLASH_TIME = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                                          startActivity(i);
                                          finish();
                                      }
                                  }
                ,SPLASH_TIME);
    }
}
