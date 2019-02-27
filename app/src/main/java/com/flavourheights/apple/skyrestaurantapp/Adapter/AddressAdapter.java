package com.flavourheights.apple.skyrestaurantapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Model.AddressPlanet;
import com.flavourheights.apple.skyrestaurantapp.Model.ItemPlanet;
import com.flavourheights.apple.skyrestaurantapp.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ListHolder> {

    private List<AddressPlanet> mPlanetList;
    private OnItemClickListner mlistner;
    int selectedPosition = -1;


    public interface OnItemClickListner
    {
        void onItemClick(int position);

    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.mlistner = listner;
//        this.fragment = itemAllFragment;
    }

    public AddressAdapter(List<AddressPlanet> mPlanetList1) {
        this.mPlanetList = mPlanetList1;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.addresslist_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListHolder holder, final int position) {

        holder.textViewcustname.setText(mPlanetList.get(position).getCustName());
        holder.textViewaddresstype.setText(mPlanetList.get(position).getAddressType());
        holder.textViewhousenm.setText(mPlanetList.get(position).getHousename());
        holder.textViewlandmark.setText(mPlanetList.get(position).getLandmark());
        holder.textViewlocality.setText(mPlanetList.get(position).getLocality());
        holder.textViewcity.setText(mPlanetList.get(position).getCity());
        holder.textViewpincode.setText(mPlanetList.get(position).getPincode());

        if (selectedPosition == position)
        {
            holder.imageViewcheck.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imageViewcheck.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            selectedPosition = position;
            notifyDataSetChanged();
//                if (holder.imageViewcheck.isEnabled())
//                {
//                    holder.imageViewcheck.setVisibility(View.GONE);
//                }
//                else
//                {
//                    holder.imageViewcheck.setVisibility(View.VISIBLE);
//                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlanetList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewhousenm,textViewlandmark,textViewlocality,textViewcity,textViewpincode, textViewaddresstype, textViewcustname;
        ImageView imageViewcheck;
        //LinearLayout parentlayout;
        public ListHolder(View itemView) {
            super(itemView);
            // parentlayout = itemView.findViewById(R.id.list);

            textViewcustname = (TextView) itemView.findViewById(R.id.tvcustname);
            textViewaddresstype = (TextView) itemView.findViewById(R.id.tvaddresstype);
            textViewhousenm = (TextView) itemView.findViewById(R.id.tvaddhouse);
            textViewlandmark = (TextView) itemView.findViewById(R.id.tvaddlandmark);
            textViewlocality = (TextView) itemView.findViewById(R.id.tvaddlocality);
            textViewcity = (TextView) itemView.findViewById(R.id.tvaddcity);
            textViewpincode=(TextView) itemView.findViewById(R.id.tvaddpincode);
            imageViewcheck=(ImageView) itemView.findViewById(R.id.imgaddcheck);

            imageViewcheck.setVisibility(View.GONE);

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
