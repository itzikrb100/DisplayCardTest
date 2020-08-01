package com.itzik.common.datamodels.responsemodel;

import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("first")
    private String firestName;

    @SerializedName("last")
    private String secondName;

    public Name(){}

    public String getFirestName(){
        return firestName;
    }

    public String getSecondName(){
        return secondName;
    }
}
