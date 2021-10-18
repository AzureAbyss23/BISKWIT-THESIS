package com.example.biskwit.ui;

import com.example.biskwit.Data.UserModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailModel {
    @SerializedName("user_details")
    private List<UserModel> userDetails;

    public List<UserModel> getUserDetails() {
        return userDetails;
    }
}
