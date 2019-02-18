package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    String path;
    ImageView logo;
    DatabaseHelpher databaseHelpher;
    String dataemail,datapass,datamobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        databaseHelpher = new DatabaseHelpher(this);
        GetData();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {}

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.setconstr("http://192.168.0.114:8098/api/");
        globalVariable.setUsername(dataemail);
        globalVariable.setloginPassword(datapass);
        globalVariable.setMobileNo(datamobile);
//        globalVariable.setconstr("http://restaurant.skyvisioncables.com/api/");
//        globalVariable.setconstr("http://restaurant.flavourheights.co.in/api/");

        path = globalVariable.getconstr();

        logo = (ImageView)findViewById(R.id.logo);



        //globalVariable.setconstr("http://SkyvisionApplication.skyvisioncables.com/api/");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new  Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("a1",path);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        },4000);// 4000 =4 seconds

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        logo.startAnimation(myanim);

        isInternetOn();
    }

    public void isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    LoginActivity.this.startActivity(new Intent(LoginActivity.this,LoginActivity.class));
//                    LoginActivity.this.finish();
//                }
//            },2000);// 4000 =4 seconds

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, "Internet Connection Not Connected ", Toast.LENGTH_LONG).show();



        }

    }


    public void GetData()
    {
        Cursor c = databaseHelpher.GetRegData();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    dataemail = c.getString(c.getColumnIndex("Email"));
                    datapass = c.getString(c.getColumnIndex("Password"));
                    datamobile = c.getString(c.getColumnIndex("PhoneNo"));
                }
                while (c.moveToNext());
            }
        }
    }
}
