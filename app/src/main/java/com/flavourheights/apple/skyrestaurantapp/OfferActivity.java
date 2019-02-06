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

import com.flavourheights.apple.skyrestaurantapp.Adapter.OfferAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.OfferPlanet;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfferActivity extends AppCompatActivity {

    List<OfferPlanet> mPlanetlist1 = new ArrayList<OfferPlanet>();
    OfferAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,festnm,fromdt,todt,disc;
    ImageView imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        imageViewback=(ImageView)findViewById(R.id.imgvback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OfferActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbaroffer);
        setSupportActionBar(toolbar);
        new getofferlist().execute();

        recyclerView = (RecyclerView)findViewById(R.id.recycleoffer);
        recyclerView.setLayoutManager(new LinearLayoutManager(OfferActivity.this));
    }

    class getofferlist extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(OfferActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            shh = new ServiceHandler();
            String url = path + "Registration/getOffer";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET , null);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        festnm = a1.getString("FestivalName");
                        fromdt = a1.getString("ValidFrom");
                        todt = a1.getString("Validto");
                        disc = a1.getString("Discount");



//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//                        String date = formatter.format(Date.parse(fromdt));


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = sdf.parse(fromdt);
                        String df = d.toString();
                        System.out.println(d);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                        String date = formatter.format(Date.parse(df));

                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date d1 = sdf1.parse(todt);
                        String dt = d1.toString();
                        System.out.println(d);

                        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
                        String date1 = formatter1.format(Date.parse(dt));

                        OfferPlanet planet1 = new OfferPlanet(festnm,date,date1,disc);
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.dismiss();
                    adapter = new OfferAdapter(mPlanetlist1);
                    recyclerView.setAdapter(adapter);
                }
            });
        }
    }
}
