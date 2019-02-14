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

    String path,user;
    EditText editTextemail;
    Button buttonsend;
    View view;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    ServiceHandler shh;
    private FirebaseAnalytics mFirebaseAnalytics;

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
       // user = globalVariable.getUsername();

        editTextemail = (EditText)view.findViewById(R.id.etemail);
        buttonsend=(Button)view.findViewById(R.id.btnsend);

        auth=FirebaseAuth.getInstance();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        GetData();

        resetPassword();

        return view;
    }

    public void GetData()
    {
        user = editTextemail.getText().toString();
        new getRegData().execute();
    }

    public void resetPassword(){

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email= editTextemail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("verifying..");
                progressDialog.show();

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "We have send you instruction to reset your password", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Failed to send reset email", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    class getCustlistData extends AsyncTask<Void, Void, String>
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
            String url = path + "Values/MyMailSend";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Email",user));
                params2.add(new BasicNameValuePair("Subject","Reset Password"));
               // params2.add(new BasicNameValuePair("MailBody",mailbody));
                params2.add(new BasicNameValuePair("senderEmail","abcxyzinfotech@gmail.com"));
                params2.add(new BasicNameValuePair("senderPassword","demo#1234"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    int Status = Integer.parseInt(jObj.getString("Status"));
                    if (Status == 1)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Register Successfully", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_LONG).show();
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

//            editTextname.setText("");
//            editTextaddress.setText("");
//            editTextmobileno.setText("");
//            editTexttypebusi.setText("");
//            editTextcity.setText("");
//            editTextemail.setText("");


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
//                        itemname = a1.getString("ItemName");
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
