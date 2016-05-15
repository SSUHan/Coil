package com.brianandroid.myzzung.coli.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.brianandroid.myzzung.coli.CoilApplication;
import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.model.StoreInfo;
import com.brianandroid.myzzung.coli.util.StoreSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private final String TAG = "SearchFragment";

    private CoilApplication app;


    public SearchFragment() {
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

        StoreSearchAdapter adapter = new StoreSearchAdapter(getActivity(), app.storeAll.getItemList(), R.layout.fragment_search);
        app.storeAll.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
