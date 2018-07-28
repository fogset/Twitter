package com.example.tianhao.twitter;

import android.app.Application;

import java.util.ArrayList;

public class UsersTweets extends Application {

    private String mGlobalVarValue = "hello";

    public String getGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setGlobalVarValue(String str) {
        mGlobalVarValue = str;
    }
}
