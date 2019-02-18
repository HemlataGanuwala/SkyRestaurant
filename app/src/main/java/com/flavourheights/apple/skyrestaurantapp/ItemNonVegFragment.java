package com.flavourheights.apple.skyrestaurantapp;

import android.app.ProgressDialog;
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


public class ItemNonVegFragment extends Fragment {

    View view;
    String path,itemname,subitem,rate,img,user,pass;
    ProgressDialog progress;
    ServiceHandler shh;
    List<ItemPlanet> mPlanetlist1 = new ArrayList<ItemPlanet>();
    ItemAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_item_non_veg, container, false);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();
        pass = globalVariable.getloginPassword();

        Displayitem();

        new getNonvegItem().execute();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleNonveg);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

    class getNonvegItem extends AsyncTask<Void, Void, String>
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
            String url = path + "Registration/getVegNonVegItems";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("ItemName", itemname));
                params2.add(new BasicNameValuePair("ItemType", "NonVeg"));
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

//            adapter.setOnItemClickListner(new ItemAdapter.OnItemClickListner() {
//                @Override
//                public void onItemClick(int position) {
//
//                }
//
//                @Override
//                public void iconImageViewOnClick(View v, int position) {
////                int t1 =0;
////                t1 = recyclerView.getAdapter().getItemCount();
////
////                for (int i=0; i<t1; i++)
////                {
//                    ItemPlanet planet1 = mPlanetlist1.get(position);
//                    subitem1 = planet1.getSubItemname();
//                    rate1 = planet1.getRate();
////                }
//                    new getAllItemcount().execute();
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (Exception e) {}
//
//                    if (Response == "null")
//                    {
//                        count++;
//                        cnt = String.valueOf(count);
//                        new RegisterData().execute();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(), "Item Already Added", Toast.LENGTH_LONG).show();
//                    }
//
//                    activityCommunicator.passDataActivity(cnt);
//
//
//
//                }
//
//            });

        }
    }

//    class getAllItemcount extends AsyncTask<Void, Void, String>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            shh = new ServiceHandler();
//            String url = path + "Registration/GetItemsCount";
//            Log.d("Url: ", "> " + url);
//
//            try{
//                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("Username", user));
//                params2.add(new BasicNameValuePair("Password", pass));
//                params2.add(new BasicNameValuePair("SubItemName", subitem1));
//                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);
//
//                if (jsonStr != null) {
//                    JSONObject c1 = new JSONObject(jsonStr);
//                    Response  = c1.getString("Response");
//
//
//                }
//                else
//                {
//                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//                }
//
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            // progress.dismiss();
//        }
//    }



}
