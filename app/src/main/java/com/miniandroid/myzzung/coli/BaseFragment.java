package com.miniandroid.myzzung.coli;


import android.support.v4.app.Fragment;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by myZZUNG on 2016. 7. 21..
 */
public abstract class BaseFragment extends Fragment {

    private MaterialDialog dialog;

    protected abstract void init(View view);

    public void showProgress(int max) {
        dialog = new MaterialDialog.Builder(getActivity())
                .progress(true, max, true)
                .progressIndeterminateStyle(true)
                .show();
    }


    public void showProgressDialog(String title, String content) {
        dialog = new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .progress(true, 0)
                .show();
    }


    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }
}
