package com.miniandroid.myzzung.coli.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miniandroid.myzzung.coli.CoilApplication;
import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.gcm.QuickstartPreferences;
import com.miniandroid.myzzung.coli.gcm.RegisterationIntentService;
import com.miniandroid.myzzung.coli.util.CoilRequestBuilder;
import com.miniandroid.myzzung.coli.util.SystemMain;
import com.miniandroid.myzzung.coli.volley.MyVolley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private CoilApplication app;

    // GCM references
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String gcm_token = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (CoilApplication) getApplicationContext();

        // Get GCM Token
        registBroadcastReceiver();
        setLocalBoradcastManager(getApplicationContext());
        getInstanceIdToken();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            final RequestQueue queue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();

                CoilRequestBuilder builder = new CoilRequestBuilder(getApplicationContext());
                builder.setCustomAttribute("user_id", email)
                        .setCustomAttribute("user_pw", password)
                        .setCustomAttribute("gcm_token", gcm_token);
                Log.d(TAG, "before network : "+builder.build().toString());
            JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                    SystemMain.URL.URL_LOGIN,
                    builder.build(),
                    networkSuccessListener(),
                    networkErrorListener());

            queue.add(myReq);


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void goJoin(View v){
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    if(response.getBoolean("login")){
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        app.user_id = email;
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, response.getString("error_message"), Toast.LENGTH_SHORT).show();
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

    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Instance ID를 이용하여 디바이스 토큰을 가져오는 RegistrationIntentService를 실행한다.
     */
    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(getApplicationContext(), RegisterationIntentService.class);
            startService(intent);
        }else{
            Log.d(TAG, "check Play Services invalid");
        }
    }

    /**
     * LocalBoardcastManager 를 등록한다
     * @param context
     */
    private void setLocalBoradcastManager(Context context){
        LocalBroadcastManager.getInstance(context).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(context).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(context).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    /**
     * LocalBoardcastManager를 해제한다
     * @param context
     */
    private void deleteLocalBoardcastManager(Context context){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    /**
     * LocalBroadcast 리시버를 정의한다. 토큰을 획득하기 위한 READY, GENERATING, COMPLETE 액션에 따라 UI에 변화를 준다.
     */
    public void registBroadcastReceiver(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();


                if(action.equals(QuickstartPreferences.REGISTRATION_READY)){
                    // 액션이 READY일 경우
                    Log.d(TAG, "GCM Action ready");
                } else if(action.equals(QuickstartPreferences.REGISTRATION_GENERATING)){
                    // 액션이 GENERATING일 경우
                    Log.d(TAG, "GCM Token generating");

                } else if(action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)){
                    // 액션이 COMPLETE일 경우
                    Log.d(TAG, "GCM Token generated");
                    String token = intent.getStringExtra("token");
                    Log.d(TAG, "GCM : "+token);
                    gcm_token = token;
                }

            }
        };
    }
}
