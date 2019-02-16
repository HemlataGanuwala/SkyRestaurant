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
import org.json.JSONArray;
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

    TextView textViewcost, textViewdate, textViewmobileno;
    EditText editTextaddress;
    ImageView imageViewback;
    RadioButton radioButtoncash, radioButtononline;
    Button buttonproceed;
    private int mYear, mMonth, mDay, mHour, mMinite;
    String path, mdate, sdate, mobileno, address,totalamount, cdate, paymentmode, user, mtime, refferamount, custname, custfname, custlname, totalcost, amount;
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
        mobileno = globalVariable.getMobileNo();

        buttonproceed=(Button)findViewById(R.id.btnproceed);

        textViewcost = (TextView) findViewById(R.id.tvcost);
        textViewdate = (TextView) findViewById(R.id.tvdate);
        textViewmobileno = (TextView) findViewById(R.id.etmobileno);

        editTextaddress = (EditText)findViewById(R.id.etaddress);

        radioButtoncash=(RadioButton)findViewById(R.id.rbcashpay);
        radioButtononline=(RadioButton)findViewById(R.id.rbonlinepay);

        display();

//        amount= String.valueOf(totalamount);


        buttonproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getCustomerData().execute();

                    insertData();

            }
        });

         sdate = DateFormat.getDateTimeInstance().format(new Date());

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


    }

    public void display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null)
        {
            totalamount = (String) bundle.get("Cost");
        }
        textViewcost.setText(totalamount);
        textViewmobileno.setText(mobileno);
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

                        cdate= mdate + "  " +mtime;

                        textViewdate.setText(cdate);
                    }
                }, mHour, mMinite, false);
        timePickerDialog.show();
    }

    public void insertData()
    {
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

        if(!sdate.equals(cdate))
        {
            textViewdate.setError("Select valid date and time");
            valid=false;
        }else {
            valid=true;
        }


        if (address.isEmpty())
        {
            editTextaddress.setError("Enter your Address");
            valid=false;
        }else {
            valid=true;
        }

        return valid;
    }

    class getCustomerData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Registration/getCustDetail";

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("Email",user));
                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);

                if (Jsonstr != null) {
                    JSONObject c1 = new JSONObject(Jsonstr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++) {

                        JSONObject a1 = classArray.getJSONObject(i);
                        custfname = a1.getString("CustFirstName");
                        custlname = a1.getString("CustLastName");
                        refferamount = a1.getString("RAmount");

                        custname = custfname + "  " + custlname;
                    }
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

        }

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
            String url = path + "Registration/saveOrderMaster";

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("Address",address));
                params2.add(new BasicNameValuePair("UserName",user));
                params2.add(new BasicNameValuePair("CustomerName",custname));
                params2.add(new BasicNameValuePair("MobileNo",mobileno));
                params2.add(new BasicNameValuePair("RAmount",refferamount));
                params2.add(new BasicNameValuePair("TotalAmout",String.valueOf(totalamount)));
                params2.add(new BasicNameValuePair("ODate",mdate));
                params2.add(new BasicNameValuePair("OTime",mtime));
                params2.add(new BasicNameValuePair("PaymentMode",paymentmode));
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
                    if (radioButtoncash.isChecked() || radioButtononline.isChecked()) {
                        Toast.makeText(PlaceOrderActivity.this, "Your Order Proceed Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PlaceOrderActivity.this, MakePaymentActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(PlaceOrderActivity.this, "Please check your payment option", Toast.LENGTH_LONG).show();
                    }
            }

        }
    }

}





