package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MyAccountActivity extends AppCompatActivity {

    TextView textVieworder, textViewwallet;
    EditText editTextname, editTextmobileno, editTextemailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        textVieworder = (TextView)findViewById(R.id.tvmyacountorder);
        textViewwallet = (TextView)findViewById(R.id.tvmyacountwallet);
        editTextemailid = (EditText)findViewById(R.id.etmyacountemailid);
        editTextname = (EditText)findViewById(R.id.etmyacountname);
        editTextmobileno = (EditText)findViewById(R.id.etmyacountmobileno);

        textVieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
