package com.flavourheights.apple.skyrestaurantapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.flavourheights.apple.skyrestaurantapp.Adapter.AddressAdapter;
import com.flavourheights.apple.skyrestaurantapp.Adapter.CartAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.AddressPlanet;
import com.flavourheights.apple.skyrestaurantapp.Model.CartListPlanet;

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

public class PlaceOrderActivity extends AppCompatActivity implements AddressAdapter.OnItemClickListner{

    TextView textViewcost, textViewdate, textViewmobileno;
    EditText editTextaddress1,editTextaddress2,editTextaddress3;
    ImageView imageViewback;
    RadioButton radioButtoncash, radioButtononline, radioButtoncashondelivery;
    Button buttonproceed,buttonaddaddress;
    private int mYear, mMonth, mDay, mHour, mMinite;
    String path, cartstatus, totalcount, password, mdate, sdate, mobileno, address,totalamount, cdate, paymentmode, user, mtime, refferamount, custname, custfname, custlname, totalcost, amount;
    boolean valid=true;
    ServiceHandler shh;
    int Status=1;
    String pincode,landmark,houseno,locality,city,datapincode,datalandmark,datahouseno,datalocality,datacity;
    RecyclerView recyclerView;
    List<AddressPlanet> mPlanetlist= new ArrayList<AddressPlanet>();
    AddressAdapter adapter;
    ProgressDialog progress;


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

//        editTextaddress1 = (EditText)findViewById(R.id.etaddress1);
//        editTextaddress2 = (EditText)findViewById(R.id.etaddress2);
//        editTextaddress3 = (EditText)findViewById(R.id.etaddress3);
        buttonaddaddress = (Button) findViewById(R.id.btnaddaddress);

        radioButtoncash=(RadioButton)findViewById(R.id.rbcashpay);
        radioButtononline=(RadioButton)findViewById(R.id.rbonlinepay);
        radioButtoncashondelivery = (RadioButton) findViewById(R.id.rbcashondelivery);

        recyclerView=(RecyclerView)findViewById(R.id.rvaddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlaceOrderActivity.this));

        new getAddressData().execute();

        display();

        buttonproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getCustomerData().execute();

                insertData();

                new UpdateCartStatus().execute();

            }
        });

         sdate = DateFormat.getDateTimeInstance().format(new Date());

        textViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePicker();

            }
        });

        buttonaddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceOrderActivity.this,AddAddressActivity.class);
                startActivity(intent);
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
            totalcount = (String) bundle.get("Count");
        }
        textViewcost.setText(totalamount);
        textViewmobileno.setText(mobileno);
    }

    public void displayAddress()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null)
        {
            pincode = (String) bundle.get("Pincode");
            landmark = (String) bundle.get("AppartmentName");
            houseno = (String) bundle.get("HouseNo");
            locality = (String) bundle.get("Locality");
            city = (String) bundle.get("City");
        }
        if (pincode != null && landmark != null && houseno != null && locality != null && city != null)
        {
            editTextaddress1.setText(houseno);
            editTextaddress2.setText(landmark+" , "+locality);
            editTextaddress3.setText(city +"-"+ pincode);
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

                        cdate= mdate + "  " +mtime;

                        textViewdate.setText(cdate);
                    }
                }, mHour, mMinite, false);
        timePickerDialog.show();
    }

    public void insertData()
    {
//        address=editTextaddress1.getText().toString() +""+ editTextaddress2.getText().toString() +""+ editTextaddress3.getText().toString();
        address = datahouseno+" "+datalandmark+" "+datalocality+" "+datacity+" "+datapincode;

        if (radioButtoncash.isChecked()==true)
        {
            paymentmode = "Cash Payment";
        }else if (radioButtononline.isChecked() == true){

            paymentmode = "Online Payment";
        }else if (radioButtoncashondelivery.isChecked() == true){
            paymentmode = "Cash on Delivery";
        }else {
            Toast.makeText(PlaceOrderActivity.this, "Select your payment mode", Toast.LENGTH_LONG).show();
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
            editTextaddress1.setError("Enter your Address");

            valid=false;
        }else {
            valid=true;
        }

        return valid;
    }

    @Override
    public void onItemClick(int position) {



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

    class getAddressData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Registration/getAddressData";

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("UserName",user));
                String Jsonstr = shh.makeServiceCall(url ,ServiceHandler.POST , params2);

                if (Jsonstr != null) {
                    JSONObject c1 = new JSONObject(Jsonstr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++) {


                        JSONObject a1 = classArray.getJSONObject(i);
                        datahouseno = a1.getString("HouseNo");
                        datalandmark = a1.getString("AppartmentName");
                        datalocality = a1.getString("Location");
                        datacity = a1.getString("City");
                        datapincode = a1.getString("Pincode");

                        AddressPlanet planet = new AddressPlanet(datahouseno, datalandmark,datalocality,datacity,datapincode);
                        mPlanetlist.add(planet);
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter=new AddressAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);

                }
            });

            adapter.setOnItemClickListner(PlaceOrderActivity.this);
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
                params2.add(new BasicNameValuePair("NoOfItem", totalcount));
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

            if (Status == 1) {
                if (radioButtoncash.isChecked() || radioButtoncashondelivery.isChecked()) {
//                        Toast.makeText(PlaceOrderActivity.this, "Your Order Place Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PlaceOrderActivity.this, MakePaymentActivity.class);
                    intent.putExtra("Cost", totalamount);
                    startActivity(intent);

                } else if (radioButtononline.isChecked()) {
                    Intent intent = new Intent(PlaceOrderActivity.this, OnlinePaymentActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PlaceOrderActivity.this, "Please select your payment option", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    class UpdateCartStatus extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(PlaceOrderActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Cart/UpdateStatus";

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("Username", user));
                params2.add(new BasicNameValuePair("Status", "1"));

                String Jsonstr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (Jsonstr != null) {
                    JSONObject c1 = new JSONObject(Jsonstr);
                    Status = c1.getInt("Status");
                } else {
                    Toast.makeText(PlaceOrderActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            new getCartData().execute();


        }

    }


    class getCartData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();
            String url = path + "Cart/GetCartItems";

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                params2.add(new BasicNameValuePair("Email", user));
                params2.add(new BasicNameValuePair("Password", password));
                String Jsonstr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (Jsonstr != null) {
                    JSONObject c1 = new JSONObject(Jsonstr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++) {

                        JSONObject a1 = classArray.getJSONObject(i);
                        cartstatus = a1.getString("Status");

                    }
                } else {
                    Toast.makeText(PlaceOrderActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new deleteCartItem().execute();

        }

    }


        class deleteCartItem extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progress = new ProgressDialog(PlaceOrderActivity.this);
//                progress.setMessage("Loading...");
//                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progress.setIndeterminate(true);
//                progress.setProgress(0);
//                progress.show();
            }


            @Override
            protected String doInBackground(String... strings) {

                shh = new ServiceHandler();
                String url = path + "Cart/DeleteCartItem";

                try {
                    List<NameValuePair> params2 = new ArrayList<>();

                    params2.add(new BasicNameValuePair("Username", user));
                    params2.add(new BasicNameValuePair("Status", cartstatus));

                    String Jsonstr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                    if (Jsonstr != null) {
                        JSONObject c1 = new JSONObject(Jsonstr);
                        Status = c1.getInt("Status");
                    } else {
                        Toast.makeText(PlaceOrderActivity.this, "Data not Found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
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







