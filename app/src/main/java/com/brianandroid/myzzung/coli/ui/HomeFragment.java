package com.brianandroid.myzzung.coli.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.volley.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView home_text = null;

    private final String server_url = "http://ljs93kr.cafe24.com/coil/test_dir/coil_test.php";


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RequestQueue queue = MyVolley.getInstance(getActivity()).getRequestQueue();

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        home_text = (TextView) rootView.findViewById(R.id.home_text);
        Button home_btn = (Button) rootView.findViewById(R.id.home_button);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
                        server_url,
                        new JSONObject(),
                        networkSuccessListener(),
                        networkErrorListener());

                queue.add(myReq);

            }
        });



        return rootView;
    }
    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String from_server = null;
                try {
                    from_server = response.getString("test");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                home_text.setText(from_server);

            }
        };
    }
    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
