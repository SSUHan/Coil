package com.miniandroid.myzzung.coli.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miniandroid.myzzung.coli.CoilApplication;
import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.model.StoreInfo;
import com.miniandroid.myzzung.coli.model.UserInfo;
import com.miniandroid.myzzung.coli.volley.MyVolley;

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
    public void doMakeStamp(int coupon_id, int store_id, int stamp_num, String spw){
        builder.setCustomAttribute("coupon_id", coupon_id)
                .setCustomAttribute("store_id", store_id)
                .setCustomAttribute("store_pw", spw)
                .setCustomAttribute("stamp_num", stamp_num);
        Log.d(TAG, "before network : "+builder.build().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                SystemMain.URL.URL_COUPON_UPDATE,
                builder.build(),
                makeStampSuccessListener(),
                networkErrorListener());

        queue.add(myReq);
    }

    /**
     * 쿠폰을 삭제하는 작업
     * @param coupon_id
     * @param store_id
     */
    public void deleteCoupon(int coupon_id, int store_id){
        builder.setCustomAttribute("coupon_id", coupon_id)
                .setCustomAttribute("store_id", store_id);
        Log.d(TAG, "before network : "+builder.build().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                SystemMain.URL.URL_COUPON_DELETE,
                builder.build(),
                deleteCouponSuccessListener(),
                networkErrorListener());

        queue.add(myReq);
    }

    /**
     * 쿠폰을 사용하는 작업
     * @param coupon_id
     */
    public void useCoupon(int coupon_id){
        builder.setCustomAttribute("coupon_id", coupon_id);
        Log.d(TAG, "before network : "+builder.build().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                SystemMain.URL.URL_COUPON_USE,
                builder.build(),
                useCouponSuccessListener(),
                networkErrorListener());

        queue.add(myReq);
    }

    /**
     * 쿠폰 내용물을 보는 성공리스너
     * updateCouponInfo() 에서 사용
     * @return
     */
    private Response.Listener<JSONObject> updateCouponSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    app.myRankings.setDoNetwork(false); // 데이터를 받았으니, 더이상 재요청은 하지 않아도 된다
                    app.myRankings.listInit();
                    JSONArray array = response.getJSONArray("coupon_list");
                    for(int i=0; i<array.length();i++){
                        JSONObject obj = array.getJSONObject(i);
                        app.myRankings.addItem(new UserInfo(obj));
                    }
                    app.myRankings.notifyAdapter(); // 데이터가 바뀌었으니 어뎁터를 새로 설정해달라고 요청


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Intent intent  = new Intent(getApplicationContext(), MainActivity.class);

            }
        };
    }

    /**
     * 도장 갯수를 추가하는 성공리스너
     * doMakeStamp 에서 사용
     */
    private Response.Listener<JSONObject> makeStampSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    int state = response.getInt("update_state");
                    if(state== 1){
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(state == 0){
                        // 꽉찬 쿠폰
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(state == 3){
                        // 쿠폰이 꽉차고도 도장갯수가 남을경우
                        // 추가로 동적할당해주고 나머지를 넣어준다
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        if(response.getBoolean("additional_enroll")){
                            Toast.makeText(context, response.getString("message2"), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, response.getString("message3"), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, response.getString("error_message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(state == -1){
                        new MaterialDialog.Builder(context)
                                .title("도장찍기")
                                .content("도장찍기에 실패하였습니다\n"+response.getString("error_message"))
                                .positiveText(R.string.btn_positive_text)
                                .theme(Theme.DARK)
                                .show();
                    }
                    app.doNetworkAgain();
                    updateCouponInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Intent intent  = new Intent(getApplicationContext(), MainActivity.class);

            }
        };
    }

    /**
     * 쿠폰 삭제 성공 리스너
     * @return
     */
    private Response.Listener<JSONObject> deleteCouponSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    if(response.getBoolean("delete_state")){
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, response.getString("error_message"), Toast.LENGTH_SHORT).show();
                    }
                    updateCouponInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Intent intent  = new Intent(getApplicationContext(), MainActivity.class);

            }
        };
    }

    private Response.Listener<JSONObject> useCouponSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    if(response.getBoolean("coupon_use")){
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, response.getString("error_message"), Toast.LENGTH_SHORT).show();
                    }
                    updateCouponInfo();
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
