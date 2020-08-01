package com.itzik.common.datamodels.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseCard {

    @SerializedName("name")
    private Name fulleName;

    @SerializedName("location")
    private Address address;

    @SerializedName("email")
    private String email;

   @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("picture")
    private Picture urlPic;

    public ResponseCard(){}

    public String  getFullName(){
        return fulleName.getFirestName() + " , " + fulleName.getSecondName();
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getAddress(){
        String add = address.getCountry() + " , " + address.getCity() + " , " + address.getStreet();
        return add;
    }


    public String getUrlSmallPic(){
        return urlPic.getUrlSmallPic();
    }

    public String getUrlMedPic(){
        return urlPic.getUrlMedPic();
    }
}
