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
import com.miniandroid.myzzung.coli.model.UserInfo;
import com.miniandroid.myzzung.coli.util.MyCouponAdapter;
import com.miniandroid.myzzung.coli.util.RankingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private final String TAG = "RankingFragment";

    private CoilApplication app;


    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        app = (CoilApplication) getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<UserInfo> items =  new ArrayList<>();
        UserInfo item;


        for(int i=0;i<10;i++){
            item = new UserInfo("ljs93kr"+i, "leejunsu"+i, i);
            items.add(item);
        }


        RankingAdapter adapter = new RankingAdapter(getActivity(), items, R.layout.item_list_ranking);

//        MyCouponAdapter adapter = new MyCouponAdapter(getActivity(), app.myRankings.getItemList(), R.layout.fragment_ranking);
        //app.myRankings.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
