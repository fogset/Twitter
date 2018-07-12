package com.example.tianhao.twitter;

public class User {
    private String email;
    private String following;

    public User(){

    }

    public User(String email, String following) {
        this.email = email;
        this.following = following;
    }

    public String getEmail() {
        return email;
    }

    public String getFollowing() {
        return following;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
