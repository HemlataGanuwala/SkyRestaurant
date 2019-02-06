package com.flavourheights.apple.skyrestaurantapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new SmsFragment(),"sms");
        adapter.AddFragment(new EmailFragment(),"Email");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        imageViewback=(ImageView)findViewById(R.id.imgback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        createTab();
    }

    private void createTab() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.forget_tab, null);
        tabOne.setText("sms");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.forget_tab, null);
        tabTwo.setText("Email");
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }


}
