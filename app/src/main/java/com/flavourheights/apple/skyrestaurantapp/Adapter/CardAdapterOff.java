package com.flavourheights.apple.skyrestaurantapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Model.CartListPlanet;
import com.flavourheights.apple.skyrestaurantapp.R;

import java.util.List;

public class CardAdapterOff extends RecyclerView.Adapter<CardAdapterOff.ListHolder> {
    private List<CartListPlanet> mPlanetList;

    public CardAdapterOff(List<CartListPlanet> mPlanetList) {
        this.mPlanetList = mPlanetList;

    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cartlist,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {


        holder.textViewsubitemname.setText(mPlanetList.get(position).getItemName());
        holder.textViewrate.setText(mPlanetList.get(position).getCost());
        holder.textViewnoitem.setText(String.valueOf(mPlanetList.get(position).getTotalCount()));
        holder.textViewtot.setText(String.valueOf(mPlanetList.get(position).getTotalCost()));

    }

    @Override
    public int getItemCount() {
        return mPlanetList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewsubitemname,textViewrate,textViewtot,textViewnoitem;

        public ListHolder(View itemView) {
            super(itemView);
            // parentlayout = itemView.findViewById(R.id.list);
            textViewsubitemname = (TextView) itemView.findViewById(R.id.tvcartitemname);
            textViewrate = (TextView) itemView.findViewById(R.id.tvcartcost);
            textViewnoitem = (TextView) itemView.findViewById(R.id.tvnoofitem);
            textViewtot = (TextView) itemView.findViewById(R.id.tvtotalcost);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mlistner != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            mlistner.onItemClick(position);
//                        }
//                    }
//                }
//            });

        }
    }
   }
