package com.flavourheights.apple.skyrestaurantapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView textViewcost, textViewaddress, textViewaddaddress, textViewdate;
    EditText editTextmobileno;
    ImageView imageViewback;
    Button buttonproceed;
    private int mYear, mMonth, mDay, mHour, mMinite;
    long time;
    DateFormat dateFormat;
    Calendar date;
    DatePickerDialog.OnDateSetListener listener;
    String path, mdate, mobileno;
    boolean valid=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        buttonproceed=(Button)findViewById(R.id.btnproceed);
        textViewaddress = (TextView) findViewById(R.id.tvaddress);
        textViewcost = (TextView) findViewById(R.id.tvcost);
        editTextmobileno = (EditText) findViewById(R.id.etmobileno);
        textViewaddaddress = (TextView) findViewById(R.id.tvaddadress);
        textViewdate = (TextView) findViewById(R.id.tvdate);

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

        textViewaddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlaceOrderActivity.this, AddAddressActivity.class);
                startActivity(intent);

            }
        });

        buttonproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

                        textViewdate.setText(mdate + "  " + hourOfDay + ":" + minute);
                    }
                }, mHour, mMinite, false);
        timePickerDialog.show();
    }

    public boolean validation()
    {
        if(!Pattern.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$",mobileno)) {
            editTextmobileno.setError("No should be 10-digit");
            valid = false;
        }
        return valid;
    }


}
