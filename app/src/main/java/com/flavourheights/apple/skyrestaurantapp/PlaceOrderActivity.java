package com.flavourheights.apple.skyrestaurantapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView textViewcost, textViewdate;
    EditText editTextmobileno, editTextaddress;
    ImageView imageViewback;
    RadioButton radioButtoncash, radioButtononline;
    RadioGroup radioGrouppaymentmode;
    Button buttonproceed;
    private int mYear, mMonth, mDay, mHour, mMinite;
    long time;
    DateFormat dateFormat;
    Calendar date;
    DatePickerDialog.OnDateSetListener listener;
    String path, mdate, mobileno, address, cdate, paymentmode, user, mtime, cost;
    boolean valid=true;
    ServiceHandler shh;
    int Status=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();

        buttonproceed=(Button)findViewById(R.id.btnproceed);

        textViewcost = (TextView) findViewById(R.id.tvcost);
        textViewdate = (TextView) findViewById(R.id.tvdate);

        editTextmobileno = (EditText) findViewById(R.id.etmobileno);
        editTextaddress = (EditText)findViewById(R.id.etaddress);

        radioGrouppaymentmode=(RadioGroup)findViewById(R.id.rgpaymentmode);
        radioButtoncash=(RadioButton)findViewById(R.id.rbcashpay);
        radioButtononline=(RadioButton)findViewById(R.id.rbonlinepay);

        display();

        textViewcost.setText(cost);

        textViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePicker();

            }
        });

        imageViewback = (ImageView) findViewById(R.id.imgback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlaceOrderActivity.this, CartListActivity.class);
                startActivity(intent);

            }
        });

        buttonproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();

            }
        });

    }

    public void display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            cost= (String)bundle.get("Cost");
        }
    }

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        mdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinite = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinite = minute;

                        mtime= mHour+ ":" + mMinite;

                        cdate= mdate + " " +mdate;

                        textViewdate.setText(cdate);
                    }
                }, mHour, mMinite, false);
        timePickerDialog.show();
    }

    public void insertData()
    {
        mobileno=editTextmobileno.getText().toString();
        address=editTextaddress.getText().toString();

        if (radioButtoncash.isChecked()==true)
        {
            paymentmode = "Cash Payment";
        }else {
            paymentmode = "Online Payment";
        }

        if (validation())
        {
            new OrderData().execute();
        }
    }

    public boolean validation()
    {

        if (address.isEmpty())
        {
            editTextaddress.setError("Enter your Address");
            valid=false;
        }

        if (cdate.isEmpty())
        {
            textViewdate.setError("Please Select Date");
            valid=false;
        }

        if (mobileno.isEmpty())
        {
            editTextmobileno.setError("Enter Mobile no");
            valid=false;
        }
        else if(!Pattern.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$",mobileno)) {
            editTextmobileno.setError("Enter valid mobile no");
            valid = false;
        }
        return valid;
    }

    class OrderData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Registration/SaveAddress";

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("Address",address));
                params2.add(new BasicNameValuePair("UserName",user));
                params2.add(new BasicNameValuePair("MobileNo",mobileno));
                params2.add(new BasicNameValuePair("ODate",mdate));
                params2.add(new BasicNameValuePair("OTime",mtime));
                params2.add(new BasicNameValuePair("TotalAmount",cost));
                params2.add(new BasicNameValuePair("Status","1"));

                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);

                if (Jsonstr != null)
                {
                    JSONObject c1= new JSONObject(Jsonstr);
                    Status =c1.getInt("Status");
                }
                else{
                    Toast.makeText(PlaceOrderActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
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



            } else {


                }

            }


        }

    }



