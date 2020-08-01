package com.itzik.common.datamodels.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponsData {

    @SerializedName("results")
    private List<ResponseCard> results =  new ArrayList<>();

    public ResponsData(){}

    public List<ResponseCard> getResults(){
        return results;
    }
}
