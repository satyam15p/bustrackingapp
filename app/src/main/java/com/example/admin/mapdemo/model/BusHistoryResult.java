package com.example.admin.mapdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusHistoryResult {

    @SerializedName("result")
    @Expose
    private List<BusHistory> result = null;

    public List<BusHistory> getResult() {
        return result;
    }

    public void setResult(List<BusHistory> result) {
        this.result = result;
    }
}
