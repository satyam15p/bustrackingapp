package com.example.admin.mapdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverList {
    @SerializedName("result")
    @Expose
    private List<DriverListResult> result = null;

    public List<DriverListResult> getResult() {
        return result;
    }

    public void setResult(List<DriverListResult> result) {
        this.result = result;
    }

}
