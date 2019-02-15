package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flavourheights.apple.skyrestaurantapp.Adapter.CartAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.CartListPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoneyWalletActivity extends AppCompatActivity {

    ImageView imageViewback;
    Button buttonreadme;
    EditText editTextrefercode;
    TextView textViewbalance;
    String refcode,path,user,balance;
    ServiceHandler shh;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_wallet);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();

        buttonreadme = (Button)findViewById(R.id.btnreadme);
        editTextrefercode = (EditText)findViewById(R.id.etrefcode);
        textViewbalance = (TextView)findViewById(R.id.tvbalance);
        imageViewback=(ImageView)findViewById(R.id.imgback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MoneyWalletActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        buttonreadme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refcode = editTextrefercode.getText().toString();
                new addBalance().execute();
            }
        });
    }

//    public class addBalance extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            shh= new ServiceHandler();
//            String url= path + "Cart/GetCartItems";
//            Log.d("Url",">"+url);
//
//            try {
//                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("Username", user));
//                params2.add(new BasicNameValuePair("RefStatus", "1"));
//                params2.add(new BasicNameValuePair("ReferCode", refcode));
//
//                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);
//
//                if (jsonStr != null) {
//                    JSONObject c1 = new JSONObject(jsonStr);
//                    cnt = c1.getString("Response");
//                }
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            count = Integer.parseInt(cnt);
//            balance = count * (50);
//            textViewbalance.setText(String.valueOf(balance));
//        }
//    }

    public class addBalance extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            shh= new ServiceHandler();
            String url= path + "Registration/getRamount";
            Log.d("Url",">"+url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("Email", user));
                params2.add(new BasicNameValuePair("ReferCode", refcode));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);
                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++) {

                        JSONObject a1 = classArray.getJSONObject(i);
                        balance = a1.getString("RAmount");
                    }
                }
                else{
                    Toast.makeText(MoneyWalletActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            count = Integer.parseInt(cnt);
//            balance = count * (50);
            textViewbalance.setText(String.valueOf(balance));
        }
    }
}
