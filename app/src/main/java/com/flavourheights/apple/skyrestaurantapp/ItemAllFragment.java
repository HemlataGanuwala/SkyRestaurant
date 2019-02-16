package com.flavourheights.apple.skyrestaurantapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flavourheights.apple.skyrestaurantapp.Adapter.ItemAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.ItemPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ItemAllFragment extends Fragment{


    View view;
    List<ItemPlanet> mPlanetlist1 = new ArrayList<ItemPlanet>();
    ItemAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progress;
    String path,itemname,subitem,rate,img,user,pass,subitem1,rate1;
    ServiceHandler shh;
    ImageView imageViewcart;
    int Status = 1;
    int count=0;
    TextView textViewcount;
    String cnt,Response;
    public ActivityCommunicator activityCommunicator;
    public Context context;

   public interface ActivityCommunicator{
       public void passDataActivity(String someValue);
   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_itemall, container, false);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();
        pass = globalVariable.getloginPassword();

//        textViewcount = (TextView) view.findViewById(R.id.tvcount);

        Displayitem();

        mPlanetlist1.clear();

        new getAllCount().execute();

        new getAllItem().execute();

        new getAllItemcount().execute();

//        sendData();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




//        imageViewcart=(ImageView)view.findViewById(R.id.imagecart);
//        imageViewcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }

    public void Displayitem()
    {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            itemname=(String)bundle.get("a2");
        }
    }

//    public void Displaylogin()
//    {
//        Intent intent = getActivity().getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null)
//        {
//            user = (String)bundle.get("User");
//            pass = (String)bundle.get("Password");
//        }
//    }

    class getAllItem extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(getContext());
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/getItemsWiseRecord";
            Log.d("Url: ", "> " + url);

            try{
                    List<NameValuePair> params2 = new ArrayList<>();
                    params2.add(new BasicNameValuePair("ItemName", itemname));
                    String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                    if (jsonStr != null) {
                        JSONObject c1 = new JSONObject(jsonStr);
                        JSONArray classArray = c1.getJSONArray("Response");
                        for (int i = 0; i < classArray.length(); i++) {
                            JSONObject a1 = classArray.getJSONObject(i);
                            itemname = a1.getString("ItemName");
                            subitem = a1.getString("SubItemName");
                            rate = a1.getString("ItemRate");
                            img = a1.getString("ListImg");

                            ItemPlanet planet1 = new ItemPlanet(itemname,subitem,rate,img);
                            mPlanetlist1.add(planet1);

                        }

                    }
                    else
                    {
                        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                    }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new ItemAdapter(mPlanetlist1);
                    recyclerView.setAdapter(adapter);
                }
            });

        adapter.setOnItemClickListner(new ItemAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void iconImageViewOnClick(View v, int position) {
//                int t1 =0;
//                t1 = recyclerView.getAdapter().getItemCount();
//
//                for (int i=0; i<t1; i++)
//                {
                    ItemPlanet planet1 = mPlanetlist1.get(position);
                    subitem1 = planet1.getSubItemname();
                    rate1 = planet1.getRate();
//                }
                new getAllItemcount().execute();

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}

                if (Response == "null")
                {
                    count++;
                    cnt = String.valueOf(count);
                    new RegisterData().execute();
                }
                else
                {
                    Toast.makeText(getActivity(), "Item Already Added", Toast.LENGTH_LONG).show();
                }

                activityCommunicator.passDataActivity(cnt);



            }

        });


        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
        activityCommunicator = (ActivityCommunicator)context;


    }

    class getAllCount extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress=new ProgressDialog(MainActivity.this);
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/getItemsWise";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Username", user));
                params2.add(new BasicNameValuePair("Password", pass));
                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    count = c1.getInt("Response");
                }

                else
                {
                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progress.dismiss();
//            textViewcount.setText(count);

        }
    }

        class getAllItemcount extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/GetItemsCount";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Username", user));
                params2.add(new BasicNameValuePair("Password", pass));
                params2.add(new BasicNameValuePair("SubItemName", subitem1));
                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                   Response  = c1.getString("Response");


                }
                else
                {
                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progress.dismiss();
        }
    }

   public class RegisterData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Cart/AddToCart";

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("ItemName",itemname));
                params2.add(new BasicNameValuePair("SubItemName",subitem1));
                params2.add(new BasicNameValuePair("ItemRate",rate1));
                params2.add(new BasicNameValuePair("Username",user));
                params2.add(new BasicNameValuePair("Password",pass));
                params2.add(new BasicNameValuePair("TotalCount","1"));
                params2.add(new BasicNameValuePair("TotalAmt",rate1));

                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);

                if (Jsonstr != null)
                {
                    JSONObject c1= new JSONObject(Jsonstr);
                    Status =c1.getInt("Status");
                }
                else{
                    Toast.makeText(getActivity(), "Data not Found", Toast.LENGTH_SHORT).show();
                }
            }
            catch ( JSONException e){
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (Status == 1)
            {
                Toast.makeText(getActivity(), "Item add in cart", Toast.LENGTH_LONG).show();
                //new getAllItemcount().execute();
            }

        }

    }


}
