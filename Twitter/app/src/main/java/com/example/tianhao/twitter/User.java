package com.example.tianhao.twitter;


import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class User{
    private String email;
    private String tweets;
    public static ArrayList<String> usersTweets = new ArrayList<>();

    public User(){

    }

    public ArrayList<String> getUsersTweets() {
        return usersTweets;
    }

    public void setUsersTweets(ArrayList<String> usersTweets) {
        this.usersTweets = usersTweets;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
