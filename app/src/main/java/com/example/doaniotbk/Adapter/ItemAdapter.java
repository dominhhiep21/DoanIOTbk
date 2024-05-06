package com.example.doaniotbk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doaniotbk.Model.Item;
import com.example.doaniotbk.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Item> mList;
    private ItemListener itemListener;
    public void setItemListener(ItemListener itemListener){
        this.itemListener=itemListener;
    }

    public ItemAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }
    public void setData(List<Item> list){
        this.mList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mList.get(position);
        if(item==null) return;
        holder.status_item.setImageResource(item.getImage());
        holder.tv_temperature_item.setText(item.getTemperature()+"°C");
        holder.tv_time_item.setText(item.getTime()+"H");
    }

    @Override
    public int getItemCount() {
        if(mList!=null) return mList.size();
        return 0;
    }

    public Item getItem(int position) {
        return mList.get(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_temperature_item,tv_time_item;
        private ImageView status_item;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_temperature_item = itemView.findViewById(R.id.tv_temperature_item);
            tv_time_item = itemView.findViewById(R.id.tv_time_item);
            status_item = itemView.findViewById(R.id.status_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemListener(v,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemListener(View view,int position);
    }
}
