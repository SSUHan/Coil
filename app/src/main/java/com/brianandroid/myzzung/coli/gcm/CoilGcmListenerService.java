package com.brianandroid.myzzung.coli.gcm;

import android.os.Bundle;

import com.brianandroid.myzzung.coli.util.CoilNotification;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by myZZUNG on 2016. 5. 6..
 */
public class CoilGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        String title = data.getString("title");
        String message = data.getString("message");
        String is_notifi = data.getString("notification_type");
        if(is_notifi.equals("true")){
            CoilNotification cnb = new CoilNotification(getApplicationContext());
            cnb.sendNotification(title, message, CoilNotification.BY_GCM);
        }
    }
}
