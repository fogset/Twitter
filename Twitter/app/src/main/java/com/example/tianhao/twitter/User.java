package com.example.tianhao.twitter;

import java.util.List;

public class User {
    private String email;
    private List following;

    public User(){

    }


    public User(String email, List following) {
        this.email = email;
        this.following = following;
    }

    public List getFollowing() {
        return following;
    }

    public void setFollowing(List following) {
        this.following = following;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
