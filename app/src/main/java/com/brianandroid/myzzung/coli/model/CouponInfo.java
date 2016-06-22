package com.brianandroid.myzzung.coli.model;

import com.brianandroid.myzzung.coli.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cksrb on 2016. 6. 22..
 */
public class CouponInfo {
    private int couponId;
    private int storeId;
    private String storeName;
    private int maxStamp;
    private int userStamp;
    private String created;
    private String modified;
    private int image;
    // flipType
    private int flipType;

    public CouponInfo(int image_res, String storeName){
        this.image = image_res;
        this.storeName = storeName;
        this.flipType = 0;
    }

    public CouponInfo(JSONObject obj) throws JSONException {
        this.storeId = obj.getInt("store_id");
        this.created = obj.getString("created");
        this.storeName = obj.getString("store_name");
        this.image = R.drawable.logo_sample3;
        this.couponId = obj.getInt("coupon_id");
        this.userStamp = obj.getInt("current_stamp");
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

    public String showCouponState(){
        String str =  "couponId : "+couponId
                +"\nstoreId : "+storeId
                +"\nstoreName : "+storeName
                +"\ncurrent_stamp : "+userStamp
                +"\ncreated : "+created;
        return str;
    }
}
