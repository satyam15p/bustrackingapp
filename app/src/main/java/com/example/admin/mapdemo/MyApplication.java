package com.example.admin.mapdemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config=new RealmConfiguration.Builder().name("myRealm2.real").schemaVersion(2).build();
        Realm.setDefaultConfiguration(config);
    }
}
