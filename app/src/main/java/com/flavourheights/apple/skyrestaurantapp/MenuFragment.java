package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class MenuFragment extends Fragment {
    View view;
    ViewFlipper viewFlipper;
    CardView cardViewsoup,cardViewbiryani,cardViewstarter,cardViewcurries,cardViewsalad,cardViewdesert,cardViewchines,cardViewbeverage;
    TextView soups,biryani,starter,currires,salad,chinese,bevreages,dessert;
    String soupsmenu,user,pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

//        Display();

        cardViewsoup = (CardView)view.findViewById(R.id.cardsoup);
        cardViewbiryani = (CardView)view.findViewById(R.id.cardbiryani);
        cardViewstarter = (CardView)view.findViewById(R.id.cardstarter);
        cardViewcurries = (CardView)view.findViewById(R.id.cardcurries);
        cardViewsalad = (CardView)view.findViewById(R.id.cardraitasalad);
        cardViewdesert = (CardView)view.findViewById(R.id.carddesert);
        cardViewchines = (CardView)view.findViewById(R.id.cardchinese);
        cardViewbeverage = (CardView)view.findViewById(R.id.cardbeverages);

        soups=(TextView)view.findViewById(R.id.tvsoups);
        biryani=(TextView)view.findViewById(R.id.tvbiryani);
        starter=(TextView)view.findViewById(R.id.tvstarters);
        currires=(TextView)view.findViewById(R.id.tvcurries);
        salad=(TextView)view.findViewById(R.id.tvraitasalad);
        chinese=(TextView)view.findViewById(R.id.tvchainese);
        bevreages=(TextView)view.findViewById(R.id.tvbeverages);
        dessert=(TextView)view.findViewById(R.id.tvdessert);




        cardViewsoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soupsmenu=soups.getText().toString();

                Intent intent = new Intent(getActivity(),MainDashActivity.class);
                intent.putExtra("a2",soupsmenu);
                intent.putExtra("User",user);
                intent.putExtra("Password",pass);
                startActivity(intent);


            }
        });

        cardViewbiryani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soupsmenu=biryani.getText().toString();

                Intent intent = new Intent(getActivity(),MainDashActivity.class);
                intent.putExtra("a2",soupsmenu);
                startActivity(intent);

            }
        });

        return view;
    }

//    public void Display()
//    {
//        Intent intent = getActivity().getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null)
//        {
//            user = (String)bundle.get("User");
//            pass = (String)bundle.get("Password");
//        }
//    }



}
