package com.brianandroid.myzzung.coli.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.brianandroid.myzzung.coli.CoilApplication;
import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.util.CoilRequestBuilder;
import com.brianandroid.myzzung.coli.util.CouponWork;
import com.brianandroid.myzzung.coli.util.MyCouponAdapter;
import com.brianandroid.myzzung.coli.util.SystemMain;
import com.brianandroid.myzzung.coli.volley.MyVolley;

public class PresentActivity extends AppCompatActivity {

    private final String TAG = "PresentActivity";

    //Request를 위한 객체들을 다음과 같이 준비합니다.
    private RequestQueue queue;
    private CoilRequestBuilder builder;
    private CoilApplication app;

    //쿠폰에 해당하는 Request들은 이 객체에 존재합니다.
    private CouponWork couponWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);

        app = (CoilApplication) getApplicationContext();
        queue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();

        //사용자의 쿠폰에관한 정보들을 Bundle로 부터 가져옵니다.
        Intent intent = getIntent();
        final int selected_coupon_id = intent.getIntExtra("coupon_id", -1);
        final int selected_store_id = intent.getIntExtra("store_id", -1);
//        int selected_max_stamp = intent.getIntExtra("max_stamp", -1);
        int selected_user_stamp = intent.getIntExtra("user_stamp", -1);

        final EditText editText = (EditText)findViewById(R.id.present_editText);
        Button button = (Button)findViewById(R.id.present_button);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.present_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MyCouponAdapter adapter = new MyCouponAdapter(app, app.myCoupons.getItemList(), R.layout.fragment_coupon);
        app.myCoupons.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

        //검색 버튼에 Listener를 넣습니다. 먼저 아이디 검색을 해서 보낼 대상을 골라야합니다.
        if (button != null) {
            button.setOnClickListener(
                    new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            String search_text = editText.getText().toString();
                            if (search_text.isEmpty())
                            {
                                Toast.makeText(getApplicationContext(),"검색어를 입력하십시오.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //builder에 검색에 필요한 정보들을 담습니다.
                            builder.setCustomAttribute("search_id",search_text);
                            builder.setCustomAttribute("coupon_id",selected_coupon_id);
                            builder.setCustomAttribute("store_id",selected_store_id);
                            /*
                            Log.d(TAG, "before network : "+builder.build().toString());
                            JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                                    SystemMain.URL.URL_COUPON_UPDATE,
                                    builder.build(),
                                    ???Listener(), //이 부분에 사용자 검색에 관한 리스너가 필요합니다.
                                    networkErrorListener());

                            queue.add(myReq);
                            */
                        }
                    }
            );
        }

    }
}
