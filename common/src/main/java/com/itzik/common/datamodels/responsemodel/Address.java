package com.itzik.common.datamodels.responsemodel;

import com.google.gson.annotations.SerializedName;

public class Address {


    @SerializedName("street")
    private Street street;

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    public Address(){}


    public String getCity(){
        return city;
    }

    public String getCountry(){
        return country;
    }

    public String getStreet(){
        return street.getNameStreet()+" , " + street.getNumber();
    }
}
