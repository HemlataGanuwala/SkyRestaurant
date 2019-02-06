package com.flavourheights.apple.skyrestaurantapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Model.CartListMenuPlanet;
import com.flavourheights.apple.skyrestaurantapp.R;

import java.util.List;

public class CartMenuAdapter extends RecyclerView.Adapter<CartMenuAdapter.ListHolder> {

    private List<CartListMenuPlanet> mPlanetList;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.mlistner = listner;
//        this.fragment = itemAllFragment;
    }

    public CartMenuAdapter(List<CartListMenuPlanet> mPlanetList1) {
        this.mPlanetList = mPlanetList1;

    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cartlistmenu,parent,false);
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
            textViewsubitemname = (TextView) itemView.findViewById(R.id.tvcartitemnamemenu);
            textViewrate = (TextView) itemView.findViewById(R.id.tvcartcostmenu);
            textViewnoitem = (TextView) itemView.findViewById(R.id.tvnoofitemmenu);
            textViewtot = (TextView) itemView.findViewById(R.id.tvtotalcostmenu);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistner.onItemClick(position);
                        }
                    }
                }
            });

        }
    }


}
