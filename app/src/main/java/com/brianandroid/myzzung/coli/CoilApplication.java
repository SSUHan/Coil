package com.brianandroid.myzzung.coli;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.brianandroid.myzzung.coli.util.SystemMain;

/**
 * Created by myZZUNG on 2016. 4. 18..
 */
public class CoilApplication extends Application {

    public String user_id;

    public int version_code;
    public boolean debug_mode;

    @Override
    public void onCreate() {
        super.onCreate();

        initCoil(true);


    }
    /**
     * 현재 어플리케이션의 초기화를 담당한다
     * version_code 에 현재 어플리케이션의 빌드 버전 코드 정보를 저장한다
     */
    private void initCoil(boolean dm){
        // debug_mode
        this.debug_mode = dm;

        // version_code
        version_code = -1;
        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version_code = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
