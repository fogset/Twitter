package com.example.tianhao.twitter;

import java.util.ArrayList;
import java.util.List;

public class userTweets {
    private String email;
    private List<String> tweetList = new ArrayList();

    public userTweets(){

    }

    public userTweets(List<String> tweetList) {
        this.tweetList = tweetList;
    }

    public userTweets(String email) {
        this.email = email;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
