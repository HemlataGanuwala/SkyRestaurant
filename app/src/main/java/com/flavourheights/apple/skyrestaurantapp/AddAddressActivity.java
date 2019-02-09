package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

public class AddAddressActivity extends AppCompatActivity {

    Spinner spinnercity, spinnerlocation;
    ImageView imageViewback;
    EditText editTexthouseno, editTextapartment, editTextotheraddress;
    private String city[]={"Select City","Nagpur","Umred","Bhandara","Amravti","Wardha","Yawtmal"};
    private String location[]={"Select Location","Nandanvan", "New Nandanva", "Manish Nagar", "Ganesh Peth", "Bhande Plot", "Jagnade Square", "Sakardara", "Mahal", "Zashi Rani Square Bardi", "Railway Station", "Sadar"};

    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        imageViewback=(ImageView)findViewById(R.id.imgback);

        editTextotheraddress=(EditText)findViewById(R.id.etotheradd);
        editTexthouseno=(EditText)findViewById(R.id.ethouseno);
        editTextapartment=(EditText)findViewById(R.id.etlocality);

        spinnercity=(Spinner)findViewById(R.id.spincity);
        spinnerlocation=(Spinner)findViewById(R.id.spinlocation);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercity.setAdapter(adapter);

//        spinnerbranch.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, location);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlocation.setAdapter(adapter);

        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddAddressActivity.this, PlaceOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
