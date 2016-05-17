package com.brianandroid.myzzung.coli.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.brianandroid.myzzung.coli.CoilApplication;
import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.model.StoreInfo;
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

    public MyCouponAdapter(Context context, List<StoreInfo> items, int item_layout){
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

        }else{
            View v = inflater.inflate(R.layout.coupon_item_back, parent, false);
            return new BackCardHolder(v);
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StoreInfo item = items.get(position);
        Log.d("holder.getItemType", holder.getItemViewType()+"");
        switch (holder.getItemViewType()){
            case 0:
                FrontCardHolder fHolder = (FrontCardHolder)holder;
                Drawable drawable = context.getResources().getDrawable(item.getImage());
                fHolder.image.setBackground(drawable);
                fHolder.text.setText(item.getStoreName());
                break;
            case 1:
                final BackCardHolder bHolder = (BackCardHolder)holder;
                bHolder.text.setText(item.getStoreId()+" "+item.getStoreName()+" "+item.getCreated());
//                bHolder.btn_download.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RequestQueue queue = MyVolley.getInstance(context).getRequestQueue();
//                        try {
//                            CoilRequestBuilder builder = new CoilRequestBuilder(context);
//                            builder.setCustomAttribute("user_id", app.user_id)
//                                    .setCustomAttribute("store_id", item.getStoreId());
//                            Log.d(TAG, "before network : "+builder.build().toString());
//                            JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST,
//                                    SystemMain.URL.URL_COUPON_ENROLL,
//                                    builder.build(),
//                                    networkSuccessListener(bHolder.btn_download),
//                                    networkErrorListener());
//
//                            queue.add(myReq);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();
            Toast.makeText(context, "holder position : "+position, Toast.LENGTH_SHORT).show();
            items.get(position).flipCard();
            notifyItemChanged(position);
        }
    }
    public class FrontCardHolder extends ViewHolder{

        public ImageView image;
        public TextView text;
        //public CardView card;

        public FrontCardHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.item_image_front);
            text=(TextView)itemView.findViewById(R.id.item_text_front);
            //card = (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    public class BackCardHolder extends ViewHolder{

        public TextView text;
        public Button btn_download;

        public BackCardHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text_back);
            btn_download = (Button) itemView.findViewById(R.id.item_btn_back);
        }
    }
}
