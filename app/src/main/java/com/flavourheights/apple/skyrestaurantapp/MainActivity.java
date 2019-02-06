package com.flavourheights.apple.skyrestaurantapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private static final String PREFS_NAME = "PrefsFile";
    ViewFlipper viewFlipper;
    Fragment fragment = null;
    ImageView imageViewshow;
    NavigationView navigationView;
    View headerview;
    Class fragmentClass;
    private SharedPreferences preferences;
    String user,pass,path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();
        pass = globalVariable.getloginPassword();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        headerview=navigationView.getHeaderView(0);
        imageViewshow=(ImageView)headerview.findViewById(R.id.imgshow);
        imageViewshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

//        Display();


        preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        int images[] = {R.drawable.rest_slide1, R.drawable.rest_slide2, R.drawable.rest_slide3, R.drawable.rest_slide4, R.drawable.rest_slide5};
        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
        for(int image: images)
        {
            Fipperimage(image);
        }


        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentClass = MenuFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

//    public void Display()
//    {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null)
//        {
//            user = (String)bundle.get("User");
//            pass = (String)bundle.get("Password");
//        }
//    }

    public  void Fipperimage(int image)
    {
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(MainActivity.this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(MainActivity.this,android.R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartlist:
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                intent.putExtra("Username",user);
                intent.putExtra("Password",pass);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        //Class fragmentClass = null;

        switch(item.getItemId()) {
            case R.id.nav_menu:
                fragmentClass = MenuFragment.class;
                break;

//            case R.id.imgshow:
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//                break;

            case R.id.nav_login:
                Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
//                try {
//                    fragment = (Fragment) fragmentClass.newInstance();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();


            case R.id.nav_about:
                Intent intent1 = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_offer:
                Intent intent2 = new Intent(getApplicationContext(),OfferActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_share:
                Intent intent3 = new Intent(Intent.ACTION_SEND);
                intent3.setType("text/plain");
                String shareBody = "https://drive.google.com/open?id=1zrD8iqWKn5YSqFTTZ4GnNcu5FbLMGH0N";
                String shareSub = "Your Sub Here";
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent3.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent3.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent3, "Share Using"));
                break;

            case R.id.nav_wallet:

                Intent intent4= new Intent(getApplicationContext(), MoneyWalletActivity.class);
                startActivity(intent4);
                break;

            case R.id.nav_refer:

                Intent intent5= new Intent(getApplicationContext(), ReferCodeActivity.class);
                startActivity(intent5);
                break;

            case R.id.nav_rateus:

//                AppRate.with(this).setInstallDays(0).setLaunchTimes(2).setRemindInterval(2).monitor();
//                AppRate.showRateDialogIfMeetsConditions(this);

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+"com.android.crome")));
                }catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
                }
                break;

            case R.id.nav_cartlist:

                Intent intent6= new Intent(getApplicationContext(), CartList2Activity.class);
                intent6.putExtra("Username",user);
                intent6.putExtra("Password",pass);
                startActivity(intent6);
                break;

            case R.id.nav_orderhis:

                Intent intent7= new Intent(getApplicationContext(), OrderHistoryActivity.class);
                startActivity(intent7);
                break;

            default:
                fragmentClass = MenuFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
        return true;

    }
}
