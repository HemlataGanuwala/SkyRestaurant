package com.flavourheights.apple.skyrestaurantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Model.CartListPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReferCodeActivity extends AppCompatActivity {

    Button buttonShare;
    TextView textViewRefer;
    String refercode;
    ImageView imageViewback;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_code);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
       user = globalVariable.getUsername();

        textViewRefer=(TextView)findViewById(R.id.tvrefercode);

        buttonShare=(Button)findViewById(R.id.btnshare);

        new GetReferCode().execute();

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(Intent.ACTION_SEND);
                intent3.setType("text/plain");
                String shareBody = "https://drive.google.com/open?id=15roJeXDa2CbIaeou6ObR6aWc-_k5xyEA";
                String refer_code= refercode;
                String value= shareBody+""+" My Refer Code is"+" "+refer_code;
                String shareSub = "Your Sub Here";
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent3.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent3.putExtra(Intent.EXTRA_TEXT, value);
                startActivity(Intent.createChooser(intent3, "Share Using"));
            }
        });

        imageViewback=(ImageView)findViewById(R.id.imgback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReferCodeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }

    public class GetReferCode extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress=new ProgressDialog(ReferCodeActivity.this);
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            shh=new ServiceHandler();
            String url = path+"Registration/ReferAndEarn";
            Log.d("Url:", ">"+url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Email",user));
                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        refercode = a1.getString("ReferCode");


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
            textViewRefer.setText(refercode);

        }
    }


}
