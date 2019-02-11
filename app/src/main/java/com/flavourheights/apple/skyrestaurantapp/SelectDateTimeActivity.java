package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SelectDateTimeActivity extends AppCompatActivity {

    long time;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_time);

        Display();

        final View dialogView= View.inflate(SelectDateTimeActivity.this, R.layout.activity_select_date_time, null);
        final AlertDialog alertDialog= new AlertDialog.Builder(SelectDateTimeActivity.this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker)dialogView.findViewById(R.id.datepicker);
                TimePicker timePicker = (TimePicker)dialogView.findViewById(R.id.timepicker);

                Calendar calendar= new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                time = calendar.getTimeInMillis();
                alertDialog.dismiss();
            }
        });alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            str=(String)bundle.get("time");
        }
    }

}
