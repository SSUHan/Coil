package com.brianandroid.myzzung.coli.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.util.SystemMain;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class SettingFragment extends Fragment {

    private static final String TAG = "SettingFragment";
    private SharedPreferences prefs;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_setting, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        /*
         *   AutoLogin Setting
         */
        Switch autoLoginSwitch = (Switch) rootView.findViewById(R.id.auto_login_switch);
        autoLoginSwitch.setChecked(prefs.getBoolean(SystemMain.SharedPreferences.SHARED_PREFERENCE_AUTOFILE, true));
        autoLoginSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(SystemMain.SharedPreferences.SHARED_PREFERENCE_AUTOFILE, isChecked);
                editor.commit();

                if(isChecked){
                    Toast.makeText(getContext(),R.string.auto_login_set_true,Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(),R.string.auto_login_set_false,Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
         *   Notification Setting
         */
        Switch NotificationSwitch = (Switch) rootView.findViewById(R.id.notification_switch);
        NotificationSwitch.setChecked(prefs.getBoolean(SystemMain.SharedPreferences.SHARED_PREFERENCE_NOTIFICATIONFILE, true));
        NotificationSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(SystemMain.SharedPreferences.SHARED_PREFERENCE_NOTIFICATIONFILE, isChecked);
                editor.commit();

                if(isChecked)
                    Toast.makeText(getContext(),R.string.notification_set_true,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),R.string.notification_set_false,Toast.LENGTH_SHORT).show();
            }
        });

        /*
         *   Notice Replay
         */
        Button noticeReplayBtn = (Button) rootView.findViewById(R.id.notice_replay_button);
        noticeReplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*
         *   Send Report
         */
        Button sendReportBtn = (Button) rootView.findViewById(R.id.send_report_button);
        sendReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserReportActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }




}
