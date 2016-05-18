package com.brianandroid.myzzung.coli.model;

import com.brianandroid.myzzung.coli.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by myZZUNG on 2016. 5. 13..
 */
public class StoreInfo {
    private int couponId;
    private int storeId;
    private String storeName;
    private int maxStamp;
    private int userStamp;
    private String created;
    private String modified;
    private int image;
    private int userDown;
    // flipType
    private int flipType;

    public static final int STORE_INFO = 1;
    public static final int COUFON_INFO = 2;

    public StoreInfo(int image_res, String storeName){
        this.image = image_res;
        this.storeName = storeName;
        this.flipType = 0;
    }
    public StoreInfo(JSONObject obj, int flag) throws JSONException {
        this.storeId = obj.getInt("store_id");
        this.created = obj.getString("created");
        this.image = R.drawable.logo_sample3;
        if(flag == STORE_INFO){
            this.storeName = obj.getString("store_name");
            this.userDown = obj.getInt("user_down");
        }else if(flag == COUFON_INFO){
            this.couponId = obj.getInt("coupon_id");
            this.userStamp = obj.getInt("current_stamp");
        }

    }

    public void flipCard(){
        this.flipType = (this.flipType ==0 ? 1 : 0);
    }

    public int getFlipType(){
        return this.flipType;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getMaxStamp() {
        return maxStamp;
    }

    public void setMaxStamp(int maxStamp) {
        this.maxStamp = maxStamp;
    }

    public int getUserStamp() {
        return userStamp;
    }

    public void setUserStamp(int userStamp) {
        this.userStamp = userStamp;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getUserDown() {
        return userDown;
    }

    public void setUserDown(int userDown) {
        this.userDown = userDown;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
}
