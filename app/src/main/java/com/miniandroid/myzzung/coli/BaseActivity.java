package com.miniandroid.myzzung.coli;

import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by myZZUNG on 2016. 7. 21..
 */
public abstract class BaseActivity extends AppCompatActivity{

    private MaterialDialog dialog;

    public void showProgress(int max) {
        dialog = new MaterialDialog.Builder(this)
                .progress(true, max)
                .show();
    }

    public void showProgressDialog(String title, String content) {
        dialog = new MaterialDialog.Builder(this)
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
