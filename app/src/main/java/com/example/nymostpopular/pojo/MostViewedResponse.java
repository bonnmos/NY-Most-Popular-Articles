package com.example.nymostpopular.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostViewedResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("copyright")
    public String copyright;
    @SerializedName("num_results")
    public int num_results;
    @SerializedName("results")
    public List<Result> results;
}
