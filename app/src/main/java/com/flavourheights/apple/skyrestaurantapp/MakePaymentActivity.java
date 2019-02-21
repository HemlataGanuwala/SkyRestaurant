package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MakePaymentActivity extends AppCompatActivity {

    Button buttonhome;
    TextView textViewmessage;
    String path, message, amount;
    ServiceHandler shh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        textViewmessage = (TextView)findViewById(R.id.tvshowmessage);
        buttonhome = (Button)findViewById(R.id.btnhome);

        display();

        message = "Your Payment Of Amount" + " " + amount+ " " + "Has Been Successfully Processed";
        textViewmessage.setText(message);

        buttonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MakePaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null)
        {
            amount = (String)bundle.get("Cost");
        }

    }
}
