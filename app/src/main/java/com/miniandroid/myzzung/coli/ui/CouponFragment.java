package com.miniandroid.myzzung.coli.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miniandroid.myzzung.coli.CoilApplication;
import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.util.MyCouponAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouponFragment extends Fragment {

    private final String TAG = "CouponFragment";

    private CoilApplication app;


    public CouponFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        app = (CoilApplication) getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_coupon, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

//        List<StoreInfo> items =  new ArrayList<>();
//        StoreInfo[] item=new StoreInfo[5];
//        item[0]=new StoreInfo(R.drawable.logo_sample1,"#1");
//        item[1]=new StoreInfo(R.drawable.logo_sample2,"#2");
//        item[2]=new StoreInfo(R.drawable.logo_sample3,"#3");
//        item[3]=new StoreInfo(R.drawable.logo_sample3,"#4");
//        item[4]=new StoreInfo(R.drawable.logo_sample3,"#5");
//
//        for(int i=0;i<5;i++) items.add(item[i]);
//
//        recyclerView.setAdapter(new StoreSearchAdapter(getActivity(), items, R.layout.fragment_coupon));

        MyCouponAdapter adapter = new MyCouponAdapter(getActivity(), app.myCoupons.getItemList(), R.layout.fragment_coupon);
        app.myCoupons.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
