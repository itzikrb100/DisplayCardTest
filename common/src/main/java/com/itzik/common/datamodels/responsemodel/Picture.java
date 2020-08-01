package com.itzik.common.datamodels.responsemodel;

import com.google.gson.annotations.SerializedName;

public class Picture {

    @SerializedName("large")
    private String urlLargePic;

    @SerializedName("medium")
    private String urlMedPic;

    @SerializedName("thumbnail")
    private String urlSmallPic;

    public Picture(){}

    public String getUrlLargePic(){
        return urlLargePic;
    }

    public String getUrlMedPic(){
        return urlMedPic;
    }

    public String getUrlSmallPic(){
        return urlSmallPic;
    }
}
