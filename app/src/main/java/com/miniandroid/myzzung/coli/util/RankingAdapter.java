package com.miniandroid.myzzung.coli.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.model.StoreInfo;
import com.miniandroid.myzzung.coli.model.UserInfo;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by myZZUNG on 2016. 7. 18..
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context context;
    private List<UserInfo> list;
    private int item_layout;

    public RankingAdapter(){

    }

    public RankingAdapter(Context context, List<UserInfo> items, int item_layout){
        this.context = context;
        this.list = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(this.item_layout, null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserInfo item = list.get(position);
        holder.text_id.setText(item.getUserId());
        holder.text_name.setText(item.getUserName());
        holder.text_rank.setText(item.getRank()+"위");
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.show(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_id;
        public TextView text_name;
        public TextView text_rank;
        public Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            text_id = (TextView) itemView.findViewById(R.id.text_ranking_id);
            text_name = (TextView) itemView.findViewById(R.id.text_ranking_name);
            text_rank = (TextView) itemView.findViewById(R.id.text_ranking_rank);
            btn = (Button)itemView.findViewById(R.id.btn_ranking);

        }


    }
}
