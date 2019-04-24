package com.example.admin.mapdemo.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class BusHistory  {

    @SerializedName("driverName")
    private String driverName;
    @SerializedName("busId")
    private String busId;
    @SerializedName("time")
    private String time;
    @SerializedName("date1")
    private String date;
    @SerializedName("location")
    private String locationName;


    public BusHistory(String driverName, String busId, String time, String date, String locationName) {
        this.driverName = driverName;
        this.busId = busId;
        this.time = time;
        this.date = date;
        this.locationName = locationName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
