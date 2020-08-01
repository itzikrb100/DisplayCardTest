package com.itzik.common.datamodels.responsemodel;

import com.google.gson.annotations.SerializedName;

public class Street {

    @SerializedName("number")
    private String number;

    @SerializedName("name")
    private String nameStreet;

    public Street(){}

    public String getNumber(){
        return number;
    }

    public String getNameStreet(){
        return nameStreet;
    }
}
