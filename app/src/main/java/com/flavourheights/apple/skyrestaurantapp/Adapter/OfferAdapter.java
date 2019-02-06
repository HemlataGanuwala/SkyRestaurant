package com.flavourheights.apple.skyrestaurantapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Model.OfferPlanet;
import com.flavourheights.apple.skyrestaurantapp.R;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ListHolder> {

    private List<OfferPlanet> mPlanetList;

    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public OfferAdapter(List<OfferPlanet> mPlanetList1) {
        this.mPlanetList = mPlanetList1;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.offer,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.textViewfestname.setText(mPlanetList.get(position).getFestName());
        holder.textViewfrom.setText(mPlanetList.get(position).getFromDt());
        holder.textViewto.setText(mPlanetList.get(position).getTodt());
        holder.textViewdisc.setText(mPlanetList.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return mPlanetList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewfestname,textViewfrom,textViewto,textViewdisc;
        public ListHolder(View itemView) {
            super(itemView);
            textViewfestname = (TextView) itemView.findViewById(R.id.tvfestnm);
            textViewfrom = (TextView) itemView.findViewById(R.id.tvfromdt);
            textViewto = (TextView) itemView.findViewById(R.id.tvtodt);
            textViewdisc = (TextView) itemView.findViewById(R.id.tvdisc);

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
