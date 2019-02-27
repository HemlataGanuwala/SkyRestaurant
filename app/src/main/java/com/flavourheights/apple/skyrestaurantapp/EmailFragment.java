package com.flavourheights.apple.skyrestaurantapp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flavourheights.apple.skyrestaurantapp.Model.ItemPlanet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmailFragment extends Fragment {

    String path,user,password, email;
    EditText editTextemail;
    Button buttonsend;
    View view;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    ServiceHandler shh;
    private FirebaseAnalytics mFirebaseAnalytics;
    int Status =1;

    public EmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_email, container, false);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        editTextemail = (EditText)view.findViewById(R.id.etemail);
        buttonsend=(Button)view.findViewById(R.id.btnsend);

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetData();

                new getRegData().execute();

            }
        });

        return view;
    }


    public void GetData()
    {
        user = editTextemail.getText().toString();

        if (user.equals(email)) {

            new getEmailData().execute();
        }else {
            Toast.makeText(getActivity(), "Enter your registered email id", Toast.LENGTH_LONG).show();
        }
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
            String url = "http://emailsms.skyvisionitsolutions.com/api/Email/EmailSend";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("toEmail",user));
                params2.add(new BasicNameValuePair("subject","Reset Password"));
                params2.add(new BasicNameValuePair("emailBody","Your Password is" +" " + password));
                params2.add(new BasicNameValuePair("from","Flavour Heights Recovered Password"));
//                params2.add(new BasicNameValuePair("senderPassword","demo#1234"));

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
            if (Status == 1)
            {
                Toast.makeText(getActivity(), "Email successfully send", Toast.LENGTH_LONG).show();
            }
            else
            {

            }
        }
    }

    class getRegData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog=new ProgressDialog(getActivity());
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setProgress(0);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/ForgetPassword";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Email",user));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    JSONArray classArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        password = a1.getString("Password");
                        email = a1.getString("Email");
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
//            progressDialog.dismiss();

        }
    }

}
