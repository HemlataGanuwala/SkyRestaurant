package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    ImageView imageViewback;
    EditText editTextfname, editTextlname, editTextemail, editTextphoneno, editTextpass, editTextconformpass;
    TextView textViewcondition;
    RadioButton radioButtonaccept;
    Button buttonregister;
    String fname, lname, emailid, phoneno, password, conformpass, accept;
    int Status;
    ServiceHandler shh;
    String path;
    boolean valid=true;
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

        textViewcondition=(TextView)findViewById(R.id.tvcondition);
        textViewcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(RegistrationActivity.this, PrivacyActivity.class);
                startActivity(intent);

            }
        });

        radioButtonaccept=(RadioButton)findViewById(R.id.rbaccept);
//        radioButtonaccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(radioButtonaccept.equals(true))
//                {
//                    insertData();
//                }
//
//                else {
//
//                    Toast.makeText(RegistrationActivity.this, "Accept Term and Condition", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        editTextconformpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String passwrd = editTextpass.getText().toString();
                if (s.length() > 0 && passwrd.length() > 0) {
                    if (!editTextpass.equals(passwrd)) {
                        // give an error that password and confirm password not match

                    }

                }

            }
        });

        buttonregister=(Button)findViewById(R.id.btnregister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });


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
//        radioButtonaccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (radioButtonaccept.isChecked()){
//                    buttonregister.setEnabled(true);
////                    buttonregister.setTextColor(getResources().getColor(android.R.color.white));
////                    buttonregister.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
//                }
//
//                else {
//                    buttonregister.setEnabled(false);
////                    buttonregister.setTextColor(getResources().getColor(android.R.color.white));
////                    buttonregister.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//                }
//            }
//        });

        if(validation()) {
            InsertData(fname,lname,emailid,phoneno,password);
            new RegisterData().execute();
        }else {
           Toast.makeText(RegistrationActivity.this, "All Field Mandatory", Toast.LENGTH_LONG).show();
        }

    }

    public boolean validation()
    {

        if(fname.isEmpty())
        {
            editTextfname.setError("");
            valid=false;
        }

        if (lname.isEmpty())
        {
            editTextlname.setError("");
            valid=false;
        }

        if (!EMAIL_ADDRESS_PATTERN.matcher(emailid).matches())
        {
            editTextemail.setError("");
            valid=false;
        }

        if (!password.equals(conformpass))
        {
            editTextconformpass.setError("Password is not matched");
            valid=false;
        }

        if(!Pattern.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$",phoneno)) {
            valid = false;
            editTextphoneno.setError("");
        }

//        if(!radioButtonaccept.equals(false))
//        {
//            radioButtonaccept.setError("");
//            valid=false;
//        }

        return valid;
    }


    private void InsertData(String fname, String lname, String email,String mobileno,String password) {
      databaseHelpher.RegistrationData(fname, lname, email,mobileno,password);

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
//                    buttonregister.setTextColor(getResources().getColor(android.R.color.white));
//                    buttonregister.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

                    Toast.makeText(RegistrationActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                else {
                    buttonregister.setEnabled(false);
//                    buttonregister.setTextColor(getResources().getColor(android.R.color.white));
//                    buttonregister.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
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

}
