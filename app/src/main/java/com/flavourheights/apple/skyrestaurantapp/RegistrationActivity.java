package com.flavourheights.apple.skyrestaurantapp;

import android.content.DialogInterface;
import android.content.Intent;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    ImageView imageViewback;
    EditText editTextfname, editTextlname, editTextemail, editTextphoneno, editTextpass, editTextconformpass,textViewrefercode;
    TextView textViewcondition;
    CheckBox radioButtonaccept;
    Button buttonregister;
    String fname, lname, emailid, phoneno, password, conformpass, refercode,regrefercode, response, mobileno, user;
    int Status;
    ServiceHandler shh;
    String path;
    boolean valid=true;
    FirebaseAuth mAuth;
    CheckBox checkBoxrefercode;
    DatabaseHelpher databaseHelpher;

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

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();
        mobileno = globalVariable.getMobileNo();

        mAuth=FirebaseAuth.getInstance();
        textViewrefercode = (EditText) findViewById(R.id.etregrefercode);
        textViewrefercode.setVisibility(View.GONE);

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

        checkBoxrefercode = (CheckBox)findViewById(R.id.chkrefercode);
        checkBoxrefercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((checkBoxrefercode).isChecked())
                {
                    textViewrefercode.setVisibility(View.VISIBLE);
//                    regrefercode = textViewrefercode.getText().toString();
//                    new ReferCodeData().execute();
                }else {
                    textViewrefercode.setVisibility(View.GONE);
                }

            }
        });



        radioButtonaccept=(CheckBox)findViewById(R.id.rbaccept);


        buttonregister=(Button)findViewById(R.id.btnregister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user == null || mobileno == null) {
                    insertData();
                }else {
                    Toast.makeText(RegistrationActivity.this, "You are already register", Toast.LENGTH_LONG).show();
                }


            }
        });


        editTextconformpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwrd=editTextpass.getText().toString();
                if (s.length() > 0 && passwrd.length() > 0) {
                    if (!editTextpass.equals(passwrd)) {
                        // give an error that password and confirm password not match

                    }

                }

            }
        });

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

        if ((checkBoxrefercode).isChecked())
        {
            textViewrefercode.setVisibility(View.VISIBLE);
            regrefercode = textViewrefercode.getText().toString();
            new ReferCodeData().execute();
        }


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




        if(validation()) {


//            mAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        FirebaseUser user= mAuth.getCurrentUser();
//                        Toast.makeText(RegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        Toast.makeText(RegistrationActivity.this, "Account Not Created Successfully", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });

                databaseHelpher.RegistrationData(fname,lname,emailid,phoneno,password);
                new RegisterData().execute();




        }
        else{
            Toast.makeText(RegistrationActivity.this, "Please Enter Valid Data", Toast.LENGTH_LONG).show();
        }


    }

    public boolean validation()
    {

        if(fname.isEmpty())
        {
            editTextfname.setError("Name should not empty");
            valid=false;
        }

        if (lname.isEmpty())
        {
            editTextlname.setError("Last name should not empty");
            valid=false;
        }

        if (!EMAIL_ADDRESS_PATTERN.matcher(emailid).matches())
        {
            editTextemail.setError("Enter valid id");
            valid=false;
        }

        if (password.length() < 4 && password.length() > 9)
        {
            editTextpass.setError("Enter Minimum 4 character");
            valid=false;
        }


        if(!Pattern.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$",phoneno)) {
            editTextphoneno.setError("No should be 10-digit");
            valid = false;
        }

        if(!radioButtonaccept.isChecked())
        {
            radioButtonaccept.setError("Accept term and condition");
            valid=false;
        }
        else{
            valid=true;
        }

        return valid;
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
                    buttonregister.setEnabled(true);

                    Toast.makeText(RegistrationActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                else {
                    buttonregister.setEnabled(false);

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

    class ReferCodeData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Registration/getReferCode";

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("ReferCode",regrefercode));
                params2.add(new BasicNameValuePair("UserName",emailid));
                params2.add(new BasicNameValuePair("RefStatus","1"));

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

        }

    }



}
