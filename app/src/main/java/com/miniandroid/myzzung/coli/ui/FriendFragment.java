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
import com.miniandroid.myzzung.coli.util.FriendAdapter;
import com.miniandroid.myzzung.coli.util.StoreSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    private final String TAG = "FriendFragment";

    private CoilApplication app;


    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        app = (CoilApplication) getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<UserInfo> items =  new ArrayList<>();

        UserInfo item;
        for(int i=0;i<10;i++){
            item = new UserInfo("ljs93kr"+i, "leejunsu"+i, i, 9-i);
            items.add(item);
        }

        FriendAdapter adapter = new FriendAdapter(getActivity(), items, R.layout.item_list_friend);

//        StoreSearchAdapter adapter = new StoreSearchAdapter(getActivity(), app.storeAll.getItemList(), R.layout.fragment_search);
//        app.storeAll.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
