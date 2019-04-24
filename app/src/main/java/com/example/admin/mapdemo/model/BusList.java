package com.example.admin.mapdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusList {
    @SerializedName("result")
    @Expose
    private List<BusListResult> result = null;

    public List<BusListResult> getResult() {
        return result;
    }

    public void setResult(List<BusListResult> result) {
        this.result = result;
    }

}
