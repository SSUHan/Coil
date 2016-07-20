package com.miniandroid.myzzung.coli.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.miniandroid.myzzung.coli.R;
import com.miniandroid.myzzung.coli.model.UserInfo;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by myZZUNG on 2016. 7. 21..
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<UserInfo> list;
    private int item_layout;

    public FriendAdapter(){

    }

    public FriendAdapter(Context context, List<UserInfo> items, int item_layout){
        this.context = context;
        this.list = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(item_layout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserInfo item = list.get(position);
        holder.text_id.setText(item.getUserId());
        holder.text_point.setText(item.getPoint()+"점");
        holder.text_ranking.setText(item.getRank()+"위");
        holder.btn_push.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text_id;
        public TextView text_point;
        public TextView text_ranking;
        public Button btn_push;


        public ViewHolder(View itemView) {
            super(itemView);
            text_id = (TextView)itemView.findViewById(R.id.text_friend_id);
            text_point = (TextView)itemView.findViewById(R.id.text_friend_point);
            text_ranking = (TextView)itemView.findViewById(R.id.text_friend_ranking);
            btn_push = (Button)itemView.findViewById(R.id.btn_friend_push);
        }
    }
}
