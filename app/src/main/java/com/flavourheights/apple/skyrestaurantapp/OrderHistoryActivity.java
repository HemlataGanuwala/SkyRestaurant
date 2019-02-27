package com.flavourheights.apple.skyrestaurantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.flavourheights.apple.skyrestaurantapp.Adapter.OrderAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.OrderPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    ImageView imageViewback;
    List<OrderPlanet> mPlanetlist = new ArrayList<OrderPlanet>();
    OrderAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,mdate,mtime,noofitem,amount, user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();


        imageViewback=(ImageView)findViewById(R.id.imgback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(OrderHistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        new getOrder().execute();
        recyclerView = (RecyclerView)findViewById(R.id.recycleorderhistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this));
    }





    class getOrder extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(OrderHistoryActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/getOrder";
            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("UserName", user));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        mdate = a1.getString("ODate");
                        mtime = a1.getString("OTime");
                        amount = a1.getString("TotalAmout");
                        noofitem = a1.getString("NoOfItem");

                        OrderPlanet planet1 = new OrderPlanet(mdate, mtime, amount, noofitem);
                        mPlanetlist.add(planet1);


                    }

                } else {
                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new OrderAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);
                }
            });
        }
    }
}
