package com.brianandroid.myzzung.coli.model;

import com.brianandroid.myzzung.coli.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by myZZUNG on 2016. 5. 13..
 */
public class StoreInfo {
    private int storeId;
    private String storeName;
    private int maxStamp;
    private String created;
    private String modified;
    private int image;
    private int userDown;

    public StoreInfo(int image_res, String storeName){
        this.image = image_res;
        this.storeName = storeName;
    }

    public StoreInfo(JSONObject obj) throws JSONException {
        this.storeId = obj.getInt("store_id");
        this.created = obj.getString("created");
        this.storeName = obj.getString("store_name");
        this.image = R.drawable.logo_sample3;
        this.userDown = obj.getInt("user_down");
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
}
