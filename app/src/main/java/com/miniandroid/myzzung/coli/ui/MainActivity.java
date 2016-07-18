package com.miniandroid.myzzung.coli.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miniandroid.myzzung.coli.CoilApplication;
import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.model.StoreInfo;
import com.miniandroid.myzzung.coli.util.CoilRequestBuilder;
import com.miniandroid.myzzung.coli.util.CouponWork;
import com.miniandroid.myzzung.coli.util.SystemMain;
import com.miniandroid.myzzung.coli.volley.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "MainActivity";

    private CoilApplication app;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (CoilApplication)getApplicationContext();

        queue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //View nav_header_view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.nav_header_id_text);
        nav_header_id_text.setText(app.user_id);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_fragment_layout, new HomeFragment());
        ft.commit();



//        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        }

    }

//    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new SettingFragment(), "Settings");
//        adapter.addFragment(new HomeFragment(), "Homes");
//        //adapter.addFragment(new CheeseListFragment(), "Category 3");
//        viewPager.setAdapter(adapter);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomeFragment();
            title = getString(R.string.nav_home);
        } else if (id == R.id.nav_ranking) {
            fragment = new RankingFragment();
            title = getString(R.string.nav_ranking);
//            if(app.myRankings.isDoNetwork()){
//                CouponWork couponWork = new CouponWork(getApplicationContext());
//                couponWork.updateCouponInfo();
//            }

        } else if (id == R.id.nav_friend) {
            fragment = new FriendFragment();
            title = getString(R.string.nav_friend);
            if(app.storeAll.isDoStoreListAll()){

                    CoilRequestBuilder builder = new CoilRequestBuilder(getApplicationContext());
                    builder.setCustomAttribute("user_id", app.user_id);
                    Log.d(TAG, "before network : "+builder.build().toString());
                    JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                            SystemMain.URL.URL_SEARCH_STORE_ALL,
                            builder.build(),
                            networkSearchStoreSuccessListener(),
                            networkErrorListener());

                    queue.add(myReq);

            }

        } else if (id == R.id.nav_settings) {
            fragment = new SettingFragment();
            title = getString(R.string.nav_settings);

        }else if(id == R.id.nav_community){
            fragment = new CommunityFragment();
            title = getString(R.string.nav_community);

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private Response.Listener<JSONObject> networkSearchStoreSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    if(response.getBoolean("search_all")){
                        app.storeAll.setDoStoreListAll(false); // 데이터를 받았으니, 더이상 재요청은 하지 않아도 된다
                        JSONArray array = response.getJSONArray("store_list");
                        for(int i=0; i<array.length();i++){
                            JSONObject obj = array.getJSONObject(i);
                            app.storeAll.addItem(new StoreInfo(obj, StoreInfo.STORE_INFO));
                        }
                        app.storeAll.notifyAdapter(); // 데이터가 바뀌었으니 어뎁터를 새로 설정해달라고 요청

                    }else{
                        Toast.makeText(MainActivity.this, response.getString("error_message"), Toast.LENGTH_SHORT).show();
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

//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//
//        public Adapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//    }
}
