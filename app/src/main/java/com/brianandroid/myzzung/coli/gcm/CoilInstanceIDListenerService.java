package com.brianandroid.myzzung.coli.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by myZZUNG on 2016. 5. 6..
 */
public class CoilInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegisterationIntentService.class);
        startService(intent);
    }
}
