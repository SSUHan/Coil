package com.brianandroid.myzzung.coli.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brianandroid.myzzung.coli.R;
import com.brianandroid.myzzung.coli.model.StoreInfo;

import java.util.List;

/**
 * Created by myZZUNG on 2016. 5. 14..
 */
public class StoreSearchAdapter extends RecyclerView.Adapter<StoreSearchAdapter.ViewHolder> {

    Context context;
    List<StoreInfo> items;
    int item_layout;

    public StoreSearchAdapter(Context context, List<StoreInfo> items, int item_layout){
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public StoreSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View v = inflater.inflate(R.layout.search_store_item_front, null);
            return new FrontCardHolder(v);

        }else{
            View v = inflater.inflate(R.layout.search_store_item_back, parent, false);
            return new BackCardHolder(v);
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        StoreInfo item = items.get(position);
        Log.d("holder.getItemType", holder.getItemViewType()+"");
        switch (holder.getItemViewType()){
            case 0:
                FrontCardHolder fHolder = (FrontCardHolder)holder;
                Drawable drawable = context.getResources().getDrawable(item.getImage());
                fHolder.image.setBackground(drawable);
//                fHolder.card.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "position : "+position, Toast.LENGTH_SHORT).show();
//                    }
//                });
                fHolder.text.setText(item.getStoreName());
                break;
            case 1:
                BackCardHolder bHolder = (BackCardHolder)holder;
                bHolder.text.setText(item.getStoreName());
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
        public CardView card;

        public FrontCardHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.item_image_front);
            text=(TextView)itemView.findViewById(R.id.item_text_front);
            card = (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    public class BackCardHolder extends ViewHolder{

        public TextView text;

        public BackCardHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text_back);
        }
    }
}
