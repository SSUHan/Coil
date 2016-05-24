package com.brianandroid.myzzung.coli.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.brianandroid.myzzung.coli.R;

/**
 * Created by ppang on 16. 5. 22..
 */
public class UserReportActivity extends AppCompatActivity {

    private EditText reportEdit;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);

        reportEdit  = (EditText) findViewById(R.id.report_editor);

        /*
         * Category Spinner
         */
        categorySpinner = (Spinner) findViewById(R.id.report_category_spinner);
        ArrayAdapter categotyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.user_report_category));
        categotyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categotyAdapter);
    }

    // Cancel Btn
    public void cancelReport(View v) {
        this.finish();
    }

    // Enroll Btn
    public void enrollReport(View v) {

        if(reportEdit.getText().toString().trim().length() == 0){
            Toast.makeText(getApplicationContext(),R.string.error_user_report_field_required,Toast.LENGTH_SHORT).show();
            return;
        }

        if(categorySpinner.getSelectedItemPosition() == 0){
            // toast
            Toast.makeText(getApplicationContext(),R.string.error_user_report_category_required,Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         *  통신
         */
    }
}
