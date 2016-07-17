package com.miniandroid.myzzung.coli.util;

/**
 * Created by myZZUNG on 2016. 5. 12..
 */
public class SystemMain {

    public class Build{
        public static final int V1 = 1;
        public static final int V1_END = 99;
    }

    public class URL{
        public static final String ROOT_URL= "http://ljs93kr2.cafe24.com/coil/backend/";
        public static final String URL_LOGIN = ROOT_URL+"login/do_login.php";
        public static final String URL_JOIN = ROOT_URL+"login/do_join.php";
        public static final String URL_SEARCH_STORE_ALL = ROOT_URL+"search/search_store_all.php";
        public static final String URL_COUPON_ENROLL = ROOT_URL+"client/coupon_enroll.php";
        public static final String URL_COUPON_SHOW = ROOT_URL+"client/coupon_show.php";
        public static final String URL_COUPON_UPDATE = ROOT_URL+"client/coupon_update.php";
        public static final String URL_COUPON_DELETE = ROOT_URL+"client/coupon_delete.php";
        public static final String URL_SOUND_ENROLL = ROOT_URL+"sound/sound_enroll.php";
        public static final String URL_COUPON_USE = ROOT_URL+"client/coupon_use.php";

    }

    public class SharedPreferences{
        public static final String SHARED_PREFERENCE_AUTOFILE = "auto_login"; // 자동로그인 SharedPreferences
        public static final String SHARED_PREFERENCE_NOTIFICATIONFILE = "notification_check"; // 푸쉬알림을 위한 SharedPreferences
    }

}
