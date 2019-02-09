package com.flavourheights.apple.skyrestaurantapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView textViewcost, textViewmobileno, textViewaddress, textViewaddaddress, textViewdate;
    ImageView imageViewback;
    private int mYear, mMonth, mDay, mHour, mMinite;
    String time,date;
    DateFormat dateFormat;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        textViewaddress = (TextView) findViewById(R.id.tvaddress);
        textViewcost = (TextView) findViewById(R.id.tvcost);
        textViewmobileno = (TextView) findViewById(R.id.tvmobileno);
        textViewaddaddress = (TextView) findViewById(R.id.tvaddadress);
        textViewdate = (TextView) findViewById(R.id.tvdate);

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

        dateFormat = DateFormat.getDateInstance();
        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, mYear);
                calendar.set(Calendar.MONTH, mMonth);
                calendar.set(Calendar.DAY_OF_MONTH, mDay);

                updateLabel();

            }
        };

        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, mHour);
                calendar.set(Calendar.MINUTE, mMinite);
                updateLabel();
            }
        };

        textViewdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                new DatePickerDialog(PlaceOrderActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                new TimePickerDialog(PlaceOrderActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });
    }



    public void updateLabel(){
        textViewdate.setText(dateFormat.format(calendar.getTime()));
    }
}
