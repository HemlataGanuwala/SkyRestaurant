package com.flavourheights.apple.skyrestaurantapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Model.OrderPlanet;
import com.flavourheights.apple.skyrestaurantapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ListHolder> {

    private List<OrderPlanet> mPlanetList;

    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public OrderAdapter(List<OrderPlanet> mPlanetList1) {
        this.mPlanetList = mPlanetList1;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_history,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.textViewmdate.setText(mPlanetList.get(position).getOrderDate());
        holder.textViewmtime.setText(mPlanetList.get(position).getOrderTime());
        holder.textViewamount.setText(mPlanetList.get(position).getAmount());
        holder.textViewnoofitem.setText(mPlanetList.get(position).getNoOfItem());
    }

    @Override
    public int getItemCount() {
        return mPlanetList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewmdate,textViewmtime,textViewamount,textViewnoofitem;
        public ListHolder(View itemView) {
            super(itemView);
            textViewmdate = (TextView) itemView.findViewById(R.id.tvorderdate);
            textViewmtime = (TextView) itemView.findViewById(R.id.tvordertime);
            textViewamount = (TextView) itemView.findViewById(R.id.tvorderamount);
            textViewnoofitem = (TextView) itemView.findViewById(R.id.tvordernoofitem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
