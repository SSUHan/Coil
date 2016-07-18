package com.miniandroid.myzzung.coli.model;

import org.json.JSONObject;

/**
 * Created by myZZUNG on 2016. 7. 18..
 */
public class UserInfo extends Info {

    private String userId;
    private String userName;
    private int rank;

    public UserInfo(){

    }

    public UserInfo(String id, String name, int rank){
        userId = id;
        userName = name;
        this.rank = rank;
    }

    public UserInfo(JSONObject obj){

    }

    public String show(){
        return "user_id :"+userId+" name : "+userName+" rank : "+rank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
