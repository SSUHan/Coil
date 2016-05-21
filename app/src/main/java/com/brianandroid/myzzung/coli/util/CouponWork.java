package com.brianandroid.myzzung.coli.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.brianandroid.myzzung.coli.CoilApplication;
import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.model.StoreInfo;
import com.brianandroid.myzzung.coli.ui.MainActivity;
import com.brianandroid.myzzung.coli.volley.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by myZZUNG on 2016. 5. 19..
 * 쿠폰 작업중 통신파트를 모아서 관리합니다
 */
public class CouponWork {

    private final String TAG = "CouponWork";

    private Context context;
    private CoilApplication app;
    private RequestQueue queue;
    private CoilRequestBuilder builder;

    public CouponWork(Context context){
        this.context = context;
        app = (CoilApplication)context.getApplicationContext();
        queue = MyVolley.getInstance(context).getRequestQueue();
        builder = new CoilRequestBuilder(context);
        builder.setCustomAttribute("user_id", app.user_id);
    }

    /**
     * 쿠폰 정보를 다시 받아오기 작업
     */
    public void updateCouponInfo(){
        Log.d(TAG, "before network : "+builder.build().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                SystemMain.URL.URL_COUPON_SHOW,
                builder.build(),
                updateCouponSuccessListener(),
                networkErrorListener());

        queue.add(myReq);
    }

    /**
     * 쿠폰에 도장 갯수를 늘이는 작업
     */
    public void doMakeStamp(int coupon_id, int stamp_num){
        builder.setCustomAttribute("coupon_id", coupon_id)
                .setCustomAttribute("stamp_num", stamp_num);
        Log.d(TAG, "before network : "+builder.build().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                SystemMain.URL.URL_COUPON_UPDATE,
                builder.build(),
                makeStampSuccessListener(),
                networkErrorListener());

        queue.add(myReq);
    }

    private Response.Listener<JSONObject> updateCouponSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    app.myCoupons.setDoNetwork(false); // 데이터를 받았으니, 더이상 재요청은 하지 않아도 된다
                    app.myCoupons.listInit();
                    JSONArray array = response.getJSONArray("coupon_list");
                    for(int i=0; i<array.length();i++){
                        JSONObject obj = array.getJSONObject(i);
                        app.myCoupons.addItem(new StoreInfo(obj, StoreInfo.COUFON_INFO));
                    }
                    app.myCoupons.notifyAdapter(); // 데이터가 바뀌었으니 어뎁터를 새로 설정해달라고 요청


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Intent intent  = new Intent(getApplicationContext(), MainActivity.class);

            }
        };
    }

    private Response.Listener<JSONObject> makeStampSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    int state = response.getInt("update_state");
                    if(state== 1){
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        app.doNetworkAgain();
                        updateCouponInfo();
                    }else if(state == -1){
                        Toast.makeText(context, response.getString("error_message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Intent intent  = new Intent(getApplicationContext(), MainActivity.class);

            }
        };
    }
    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.volley_network_fail, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
