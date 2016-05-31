package com.brianandroid.myzzung.coli.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.brianandroid.myzzung.coli.CoilApplication;
import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.model.StoreInfo;
import com.brianandroid.myzzung.coli.ui.PresentActivity;
import com.brianandroid.myzzung.coli.volley.MyVolley;

import org.json.JSONException;

import java.util.List;

/**
 * Created by myZZUNG on 2016. 5. 17..
 */
public class MyCouponAdapter extends RecyclerView.Adapter<MyCouponAdapter.ViewHolder> {

    private Context context;
    List<StoreInfo> items;
    int item_layout;
    private CoilApplication app;

    private EditText passwordInput; // 도장 증가할때 가게 비밀번호
    private EditText numInput; // 도장 증가할때 도장 갯수

    public MyCouponAdapter(Context context, List<StoreInfo> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        app = (CoilApplication) context.getApplicationContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View v = inflater.inflate(R.layout.coupon_item_front, null);
            return new FrontCardHolder(v);

        } else {
            View v = inflater.inflate(R.layout.coupon_item_back, parent, false);
            return new BackCardHolder(v);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StoreInfo item = items.get(position);
        Log.d("holder.getItemType", holder.getItemViewType() + "");
        switch (holder.getItemViewType()) {
            case 0:
                FrontCardHolder fHolder = (FrontCardHolder) holder;
                Drawable drawable = context.getResources().getDrawable(item.getImage());
                fHolder.image.setBackground(drawable);
                fHolder.text.setText(item.getStoreName());
                break;
            case 1:
                final BackCardHolder bHolder = (BackCardHolder) holder;
                bHolder.text.setText(item.showCouponState());
                bHolder.btn_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(context)
                                .items(R.array.coupon_work)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @SuppressWarnings("ResourceAsColor")
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        final CouponWork couponWork = new CouponWork(context);
                                        switch(which){
                                            case 0:
                                                MaterialDialog custom_dialog = new MaterialDialog.Builder(context)
                                                        .title("도장찍기")
                                                        .customView(R.layout.dialog_layout_stamp_input, true)
                                                        .positiveText(R.string.btn_positive_text)
                                                        .negativeText(R.string.btn_negative_text)
                                                        .onPositive(new MaterialDialog.SingleButtonCallback(){

                                                            @Override
                                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                couponWork.doMakeStamp(item.getCouponId(),item.getStoreId(), Integer.parseInt(numInput.getText().toString()), passwordInput.getText().toString());

                                                            }
                                                        })
                                                        .build();
                                                final View positiveAction = custom_dialog.getActionButton(DialogAction.POSITIVE);
                                                numInput = (EditText)custom_dialog.findViewById(R.id.dialog_store_stamp_num);
                                                passwordInput = (EditText) custom_dialog.findViewById(R.id.dialog_store_password);
                                                passwordInput.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                    }
                                                });
                                                positiveAction.setEnabled(false); // disabled by default
                                                custom_dialog.show();


                                                break;
                                            case 1:

                                                break;
                                            case 2:
                                                Intent intent = new Intent(context, PresentActivity.class);
                                                intent.putExtra("coupon_id", item.getCouponId());
                                                intent.putExtra("store_id", item.getStoreId());
                                                intent.putExtra("user_stamp", item.getUserStamp());
                                                context.startActivity(intent);
                                                break;
                                            case 3:
                                                new MaterialDialog.Builder(context)
                                                        .title("쿠폰삭제")
                                                        .content(R.string.dialog_delete_contents)
                                                        .positiveText(R.string.btn_positive_text)
                                                        .negativeText(R.string.btn_negative_text)
                                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                            @Override
                                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                couponWork.deleteCoupon(item.getCouponId(), item.getStoreId());
                                                            }
                                                        })
                                                        .theme(Theme.DARK)
                                                        .show();

                                                break;

                                        }
                                    }
                                })
                                .negativeText(R.string.btn_negative_text)
                                .show();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getFlipType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();
            Toast.makeText(context, "holder position : " + position, Toast.LENGTH_SHORT).show();
            items.get(position).flipCard();
            notifyItemChanged(position);
        }
    }

    public class FrontCardHolder extends ViewHolder {

        public ImageView image;
        public TextView text;
        //public CardView card;

        public FrontCardHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_image_front);
            text = (TextView) itemView.findViewById(R.id.item_text_front);

        }
    }

    public class BackCardHolder extends ViewHolder {

        public TextView text;
        public Button btn_download;

        public BackCardHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text_back);
            btn_download = (Button) itemView.findViewById(R.id.item_btn_back);

        }
    }
}
