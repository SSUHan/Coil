package com.miniandroid.myzzung.coli.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miniandroid.myzzung.coli.CoilApplication;
import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.util.CoilRequestBuilder;
import com.miniandroid.myzzung.coli.util.SystemMain;
import com.miniandroid.myzzung.coli.volley.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ppang on 16. 5. 22..
 */
public class UserReportActivity extends AppCompatActivity {

    private String TAG = "UserReportActivity";

    private EditText reportEdit;
    private Spinner categorySpinner;
    private CoilApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);

        reportEdit  = (EditText) findViewById(R.id.report_editor);

        app = (CoilApplication)getApplicationContext();

        /*
         * Category Spinner
         */
        categorySpinner = (Spinner) findViewById(R.id.report_category_spinner);
        ArrayAdapter categotyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.user_report_category));
        categotyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categotyAdapter);
    }

    // Cancel Btn
    public void cancelReport(View v) {
        this.finish();
    }

    // Enroll Btn
    public void enrollReport(View v) {

        if(reportEdit.getText().toString().trim().length() == 0){
            Toast.makeText(getApplicationContext(),R.string.error_user_report_field_required,Toast.LENGTH_SHORT).show();
            return;
        }

        if(categorySpinner.getSelectedItemPosition() == 0){
            // toast
            Toast.makeText(getApplicationContext(),R.string.error_user_report_category_required,Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         *  통신
         */
        final RequestQueue queue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();

        CoilRequestBuilder builder = new CoilRequestBuilder(getApplicationContext());
        builder.setCustomAttribute("user_id", app.user_id)
                .setCustomAttribute("contents", reportEdit.getText().toString())
                .setCustomAttribute("type", categorySpinner.getSelectedItemPosition());
        Log.d(TAG, "before network : "+builder.build().toString());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                SystemMain.URL.URL_SOUND_ENROLL,
                builder.build(),
                networkSuccessListener(),
                networkErrorListener());

        queue.add(myReq);
    }
    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    if(response.getBoolean("sound_enroll")){
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), response.getString("error_message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), R.string.volley_network_fail, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
