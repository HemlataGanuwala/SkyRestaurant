package com.flavourheights.apple.skyrestaurantapp.Adapter;

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

import com.flavourheights.apple.skyrestaurantapp.Model.ItemPlanet;
import com.flavourheights.apple.skyrestaurantapp.R;


import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ListHolder> {

    private List<ItemPlanet> mPlanetList;
    private OnItemClickListner mlistner;
    private OnCountClickListener onCountClickListener;



//    public void setOnItemClickListner(ItemAllFragment activity,OnItemClickListner listner) {
//        this.fragment = activity;
//        this.mlistner = listner;
//    }

    public interface OnCountClickListener {
        void onCountItemClick(int position);
    }

    public interface OnItemClickListner
    {
        void onItemClick(int position);

        void iconImageViewOnClick(View v, int position);


    }

    public void setOnCountClickListner(OnCountClickListener listner){
        this.onCountClickListener = listner;
//        this.fragment = itemAllFragment;
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.mlistner = listner;
//        this.fragment = itemAllFragment;
    }

    public ItemAdapter(List<ItemPlanet> mPlanetList1) {
        this.mPlanetList = mPlanetList1;

    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemall,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

//        Glide.with(context)
//                .load(mPlanetList.get(position).getImage())
//                .into(holder.imageViewitem);

        holder.textViewitemname.setText(mPlanetList.get(position).getItemname());
        holder.textViewsubitemname.setText(mPlanetList.get(position).getSubItemname());
        holder.textViewrate.setText(mPlanetList.get(position).getRate());
        byte[] decodeString = Base64.decode(mPlanetList.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        holder.imageViewitem.setImageBitmap(decodebitmap);



       // holder.imageViewitem.se(mPlanetList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mPlanetList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewitemname,textViewsubitemname,textViewrate;
        ImageView imageViewitem, imageViewcart;
        //LinearLayout parentlayout;
        public ListHolder(View itemView) {
            super(itemView);
            // parentlayout = itemView.findViewById(R.id.list);
            textViewitemname = (TextView) itemView.findViewById(R.id.tvitemname);
            textViewsubitemname = (TextView) itemView.findViewById(R.id.tvname);
            textViewrate = (TextView) itemView.findViewById(R.id.tvrate);
            imageViewitem = (ImageView) itemView.findViewById(R.id.imgviewitem);
            imageViewcart=(ImageView) itemView.findViewById(R.id.imagecart);


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

            imageViewcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.iconImageViewOnClick(v, getAdapterPosition());
                        }
                    }
                }
            });


        }
    }


}
