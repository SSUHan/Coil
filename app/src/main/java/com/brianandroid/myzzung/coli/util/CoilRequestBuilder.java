package com.brianandroid.myzzung.coli.util;

import android.content.Context;
import android.util.Log;

import com.brianandroid.myzzung.coli.CoilApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by myZZUNG on 2016. 5. 11..
 */
public class CoilRequestBuilder {

    private final String TAG = "CoilRequestBuilder";

    private final String ATTR_KEY_DEBUG_MODE = "debug_mode";
    private final String ATTR_KEY_BUILD_VERSION = "build_version";

    private JSONObject mRequestBody;
    private CoilApplication app;


    public CoilRequestBuilder(Context context) throws JSONException {
        app = (CoilApplication) context.getApplicationContext();
        mRequestBody = new JSONObject();
        mRequestBody.put(ATTR_KEY_DEBUG_MODE, app.debug_mode);
        mRequestBody.put(ATTR_KEY_BUILD_VERSION, app.version_code);
    }

    public CoilRequestBuilder setCustomAttribute(String key, Object value) throws JSONException {
        mRequestBody.put(key, value);
        return this;
    }

    public void showInside(){
        Log.d(TAG, mRequestBody.toString());
    }

    public JSONObject build(){
        return this.mRequestBody;
    }
}
