package com.example.tianhao.twitter;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String tweets;

    public User(){

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
