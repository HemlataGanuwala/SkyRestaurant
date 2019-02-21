package com.flavourheights.apple.skyrestaurantapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flavourheights.apple.skyrestaurantapp.Adapter.ItemAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.ItemPlanet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    ImageView imageViewback;
    EditText editTextfname, editTextlname, editTextemail, editTextphoneno, editTextpass, editTextconformpass;
    TextView textViewcondition;
    CheckBox radioButtonaccept;
    Button buttonregister,buttonreg;
    String fname, lname, emailid, phoneno, password, conformpass, refercode,regrefercode, response, mobileno, user,referuser,refername,refermobile,referlastname;
    int Status;
    ServiceHandler shh;
    String path;
    boolean valid=true;
    FirebaseAuth mAuth;
//    CheckBox checkBoxrefercode;
    DatabaseHelpher databaseHelpher;
    int amount, ramount=50;
    int total;
    String dataemail,datapass,datamobile;
    ProgressDialog progress;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelpher = new DatabaseHelpher(this);

        GetData();

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
//        user = globalVariable.getUsername();
//        mobileno = globalVariable.getMobileNo();

        mAuth=FirebaseAuth.getInstance();
//        textViewrefercode = (EditText) findViewById(R.id.etregrefercode);
//        textViewrefercode.setVisibility(View.GONE);

        final Drawable icon=getResources().getDrawable(R.drawable.icon_error);
        final Drawable icon_write=getResources().getDrawable(R.drawable.icons_checkmark);

        imageViewback=(ImageView)findViewById(R.id.imgback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        editTextfname=(EditText)findViewById(R.id.etfname);
        editTextlname=(EditText)findViewById(R.id.etlname);
        editTextemail=(EditText)findViewById(R.id.etemail);
        editTextphoneno=(EditText)findViewById(R.id.etphoneno);
        editTextpass=(EditText)findViewById(R.id.etpassword);
        editTextconformpass=(EditText)findViewById(R.id.etconformpass);


        new GetReferCode().execute();
//        new CheckRegData().execute();


        textViewcondition=(TextView)findViewById(R.id.tvcondition);
        String text = "<font color=#000000>I Accept</font> <font color=#E53935>Term and Conditions</font>";
        textViewcondition.setText(Html.fromHtml(text));
        textViewcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(RegistrationActivity.this, PrivacyActivity.class);
                startActivity(intent);

            }
        });

//        checkBoxrefercode = (CheckBox)findViewById(R.id.chkrefercode);
//        checkBoxrefercode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if ((checkBoxrefercode).isChecked())
//                {
//                    textViewrefercode.setVisibility(View.VISIBLE);
////                    regrefercode = textViewrefercode.getText().toString();
////                    new ReferCodeData().execute();
//                }else {
//                    textViewrefercode.setVisibility(View.GONE);
//                }
//
//            }
//        });



        radioButtonaccept=(CheckBox)findViewById(R.id.rbaccept);

        buttonreg=(Button)findViewById(R.id.btnregister);
        buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (user == null|| mobileno == null)
//                {
                    insertData();
//                }
//                else
//                {
//                    Toast.makeText(RegistrationActivity.this, "You are already register", Toast.LENGTH_LONG).show();
//                }
            }
        });

//        editTextconformpass.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String passwrd=editTextconformpass.getText().toString();
//                if (s.length() > 0 && passwrd.length() > 0) {
//                    if (!editTextpass.equals(passwrd)) {
//                        // give an error that password and confirm password not match
//
//                    }
//
//                }
//
//            }
//        });

    }

    public void GetData()
    {
        Cursor c = databaseHelpher.GetRegData();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    user = c.getString(c.getColumnIndex("Email"));
                    password = c.getString(c.getColumnIndex("Password"));
                    mobileno = c.getString(c.getColumnIndex("PhoneNo"));
                }
                while (c.moveToNext());
            }
        }
    }

    public void popupMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void insertData()
    {


        fname=editTextfname.getText().toString();
        lname=editTextlname.getText().toString();
        emailid=editTextemail.getText().toString();
        phoneno=editTextphoneno.getText().toString();
        password=editTextpass.getText().toString();
        conformpass=editTextconformpass.getText().toString();
//
//        if ((checkBoxrefercode).isChecked())
//        {
//            textViewrefercode.setVisibility(View.VISIBLE);
//            regrefercode = textViewrefercode.getText().toString();
//            if (regrefercode != null)
//            {
//                new getReferData().execute();
//                new ReferCodeData().execute();
//                new UpdateReferCodeData().execute();
//            }
//
//
//
//        }

        radioButtonaccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonaccept.setError(null);
                } else {
                    radioButtonaccept.setError("Accept term and conditions");
                }
            }
        });

        if(!emailid.equals(user)  || !phoneno.equals(mobileno)) {

            validation();
        }
        else {

            Toast.makeText(RegistrationActivity.this, "You are already registered", Toast.LENGTH_LONG).show();
        }

    }


    public void validation()
    {

        if (fname.isEmpty() || !Pattern.matches("[A-Z][a-zA-Z]*",fname))
        {
            editTextfname.setError("Enter Valid Name");
            valid = false;

        }

        else if (lname.isEmpty() || !Pattern.matches("[a-zA-z]+([ '-][a-zA-Z]+)*",lname))
        {
            editTextlname.setError("Enter Valid Surname");
            valid = false;

        }

        else if (emailid.isEmpty() || !EMAIL_ADDRESS_PATTERN.matcher(emailid).matches())
        {

            editTextemail.setError("Email valid id");
            valid = false;

        }

        else if (password.isEmpty() || !Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&!*])(?=\\S+$).{6,}$",password))
        {
            editTextpass.setError("Password should contain one special character and one numeric and length should be minimum 6");
            valid = false;

        }

        else if (phoneno.isEmpty() || !Pattern.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$",phoneno)){

            editTextphoneno.setError("Number should be 10-digit");
            valid = false;

        }

        else if (conformpass.isEmpty() || !conformpass.equals(password))
        {
            editTextconformpass.setError("Password not matched");
            valid = false;
        }

        else {
            databaseHelpher.RegistrationData(fname, lname, emailid, phoneno, password);
            new RegisterData().execute();
        }

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
                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET , null);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    refercode = c1.getString("Response");
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
            //textViewRefer.setText(refercode);

        }
    }


    class RegisterData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(RegistrationActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Registration/InsertReg";

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("CustFirstName",fname));
                params2.add(new BasicNameValuePair("CustLastName",lname));
                params2.add(new BasicNameValuePair("Password",password));
                params2.add(new BasicNameValuePair("ConfirmedPassword",conformpass));
                params2.add(new BasicNameValuePair("Email",emailid));
                params2.add(new BasicNameValuePair("PhoneNo",phoneno));
                params2.add(new BasicNameValuePair("ReferCode",refercode));



                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);

                if (Jsonstr != null)
                {
                    JSONObject c1= new JSONObject(Jsonstr);
                    Status =c1.getInt("Status");
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
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

                if (radioButtonaccept.isChecked()){
                    buttonreg.setEnabled(true);

                    Toast.makeText(RegistrationActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                    intent.putExtra("MobileNo", phoneno);
                    startActivity(intent);
                }

                else {
                    buttonreg.setEnabled(false);

                    Toast.makeText(RegistrationActivity.this, "Accept Term and Conditions", Toast.LENGTH_LONG).show();
                }

            }

            editTextfname.setText(" ");
            editTextlname.setText(" ");
            editTextemail.setText(" ");
            editTextphoneno.setText(" ");
            editTextpass.setText(" ");
            editTextconformpass.setText(" ");

        }

    }

//    class ReferCodeData extends AsyncTask<String,String,String>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            shh = new ServiceHandler();
//            String url = path + "Registration/AddReferCode";
//
//            try {
//                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("ReferCode",regrefercode));
//                params2.add(new BasicNameValuePair("UserName",emailid));
//                params2.add(new BasicNameValuePair("RUserName",referuser));
//                params2.add(new BasicNameValuePair("RAmount","50"));
//                params2.add(new BasicNameValuePair("RefStatus","1"));
//
//                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);
//
//                if (Jsonstr != null)
//                {
//                    JSONObject c1= new JSONObject(Jsonstr);
//                    Status =c1.getInt("Status");
//                }
//                else{
//                    Toast.makeText(RegistrationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
//                }
//            }
//            catch ( JSONException e){
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//        }
//
//    }
//
//    class UpdateReferCodeData extends AsyncTask<String,String,String>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            amount=amount+ramount;
//
//        }
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            shh = new ServiceHandler();
//            String url = path + "Registration/UpdateRegReferAmt";
//
//            try {
//                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("ReferCode",regrefercode));
////                params2.add(new BasicNameValuePair("UserName",emailid));
//                params2.add(new BasicNameValuePair("RAmount",String.valueOf(amount)));
//
//                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);
//
//                if (Jsonstr != null)
//                {
//                    JSONObject c1= new JSONObject(Jsonstr);
//                    Status =c1.getInt("Status");
//                }
//                else{
//                    Toast.makeText(RegistrationActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
//                }
//            }
//            catch ( JSONException e){
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//
//        }
//
//    }
//
//    class getReferData extends AsyncTask<Void, Void, String>
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
//            String url = path + "Registration/getReferCodeData";
//            Log.d("Url: ", "> " + url);
//
//            try{
//                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("ReferCode", regrefercode));
//                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);
//
//                if (jsonStr != null) {
//                    JSONObject c1 = new JSONObject(jsonStr);
//                    JSONArray classArray = c1.getJSONArray("Response");
//                    for (int i = 0; i < classArray.length(); i++) {
//                        JSONObject a1 = classArray.getJSONObject(i);
//                        referuser = a1.getString("Email");
//                        refername = a1.getString("CustFirstName");
//                        referlastname = a1.getString("CustLastName");
//                        refermobile = a1.getString("PhoneNo");
//                        amount = a1.getInt("RAmount");
//                    }
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
//        }
//    }



}
