package com.miniandroid.myzzung.coli;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.miniandroid.myzzung.coli.model.StoreInfo;
import com.miniandroid.myzzung.coli.util.MyCouponAdapter;
import com.miniandroid.myzzung.coli.util.StoreSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myZZUNG on 2016. 4. 18..
 */
public class CoilApplication extends Application {

    public String user_id;

    public int version_code;
    public boolean debug_mode;

    public StoreAll storeAll;
    public MyCoupons myCoupons;

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

        storeAll = new StoreAll();
        myCoupons = new MyCoupons();
    }

    public void doNetworkAgain(){
        storeAll.setDoStoreListAll(true);
        myCoupons.setDoNetwork(true);
    }

    /**
     * 가게 리스트 검색의 데이터
     */
    public class StoreAll{
        private boolean doStoreListAll;

        private List<StoreInfo> itemList;
        private StoreSearchAdapter adapter;
        public StoreAll(){
            doStoreListAll = true;
            itemList = new ArrayList<>();
        }

        public boolean isDoStoreListAll() {
            return doStoreListAll;
        }

        public void setDoStoreListAll(boolean doStoreListAll) {
            this.doStoreListAll = doStoreListAll;
        }

        public List<StoreInfo> getItemList() {
            return itemList;
        }


        public void setItemList(List<StoreInfo> itemList) {
            this.itemList = itemList;
        }

        public void addItem(StoreInfo item){
            itemList.add(item);
        }
        public void setAdapter(StoreSearchAdapter adapter){
            this.adapter = adapter;
        }

        public void notifyAdapter(){
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 쿠폰 카드 정보를 저장하는 데이터
     */
    public class MyCoupons{
        private boolean doNetwork;
        private List<StoreInfo> itemList;
        private MyCouponAdapter adapter;

        public MyCoupons(){
            this.doNetwork = true;
            itemList = new ArrayList<>();
        }
        public void listInit(){
            itemList.clear();
        }

        public void setDoNetwork(boolean doNetwork){
            this.doNetwork = doNetwork;
        }

        public boolean isDoNetwork() {
            return doNetwork;
        }
        public List<StoreInfo> getItemList() {
            return itemList;
        }


        public void setItemList(List<StoreInfo> itemList) {
            this.itemList = itemList;
        }

        public void addItem(StoreInfo item){
            itemList.add(item);
        }
        public void setAdapter(MyCouponAdapter adapter){
            this.adapter = adapter;
        }

        public void notifyAdapter(){
            adapter.notifyDataSetChanged();
        }
    }

}
