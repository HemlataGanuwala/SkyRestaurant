package com.flavourheights.apple.skyrestaurantapp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment {

    ProgressDialog progressDialog;
    String password, mobileno, path;
    ServiceHandler shh;
    int Status=1;
    EditText editTextmobileno;
    Button buttonsend;
    View view;



    public SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_sms, container, false);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        editTextmobileno = (EditText)view.findViewById(R.id.etmobileno);
        buttonsend = (Button)view.findViewById(R.id.btnsend);



        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
                new getEmailData().execute();

            }
        });

        return view;
    }

    public void insertData()
    {
        mobileno = editTextmobileno.getText().toString();
        new getRegData().execute();


    }

    class getEmailData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = "http://emailsms.skyvisionitsolutions.com/api/Email/MyMessageSend";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Message","Your Password is"+" "+ password));
                params2.add(new BasicNameValuePair("MobileNo",mobileno));
                params2.add(new BasicNameValuePair("MessageSendLink", "http://mysmsshop.in/http-api.php?username=skyvision&password=skyvision123&senderid=SKYVIZ&route=1&unicode=2&number=\""));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));

                }
                else
                {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
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

            progressDialog.dismiss();


        }
    }

    class getRegData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/ForgetPasswordSms";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("PhoneNo",mobileno));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    JSONArray classArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        password = a1.getString("Password");
//                        subitem = a1.getString("SubItemName");
//                        rate = a1.getString("ItemRate");
//                        img = a1.getString("ListImg");
//
//                        ItemPlanet planet1 = new ItemPlanet(itemname,subitem,rate,img);
//                        mPlanetlist1.add(planet1);

                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
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
            progressDialog.dismiss();



        }
    }

}
