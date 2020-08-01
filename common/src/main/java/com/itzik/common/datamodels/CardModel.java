package com.itzik.common.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.itzik.common.datamodels.responsemodel.ResponseCard;


public class CardModel implements Parcelable {

    private String mFullName;
    private String mEmail;
    private String mAddress;
    private String mPhone;
    private  String mUrlMedIcon;
    private  String mUrlSmallIcon;

    public CardModel(String fullName){
        this(fullName, "", "" ,"", "", "" );
    }

    public CardModel(String fullName, String email, String address, String phone, String urlMIcon, String urlSIcon){
        mFullName = fullName;
        mEmail = email;
        mAddress = address;
        mPhone = phone;
        mUrlMedIcon = urlMIcon;
        mUrlSmallIcon = urlSIcon;
    }

    protected CardModel(Parcel in) {
        mFullName = in.readString();
        mEmail = in.readString();
        mAddress = in.readString();
        mPhone = in.readString();
        mUrlMedIcon = in.readString();
        mUrlSmallIcon = in.readString();
    }


    public String getEmail(){
        return mEmail;
    }

    public String getAddress(){
        return mAddress;
    }

    public String getPhone(){
        return mPhone;
    }

    public String getFullName(){
        return mFullName;
    }

    public String getUrlMedIcon(){
        return mUrlMedIcon;
    }

    public String getUrlSmallIcon(){
        return mUrlSmallIcon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFullName);
        dest.writeString(mEmail);
        dest.writeString(mAddress);
        dest.writeString(mPhone);
        dest.writeString(mUrlMedIcon);
        dest.writeString(mUrlSmallIcon);
    }


    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };


    public static CardModel cardModelFrom(ResponseCard responseCard){
        return new CardModel(responseCard.getFullName(),
                     responseCard.getEmail(),
                     responseCard.getAddress(),
                     responseCard.getPhoneNumber(),
                     responseCard.getUrlMedPic(),
                     responseCard.getUrlSmallPic());
    }
}
