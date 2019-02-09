package com.flavourheights.apple.skyrestaurantapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flavourheights.apple.skyrestaurantapp.Adapter.CartAdapter;
import com.flavourheights.apple.skyrestaurantapp.Model.CartListPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CartAdapter.OnItemClickListner{

    ImageView imageViewback,imageViewedit;
    NavigationView navigationView;
    private SharedPreferences preferences;
    private DrawerLayout drawerLayout;
    CartListPlanet mPlanet;
    View headerview;
    ImageView imageViewshow;
    Class fragmentClass;
    private static final String PREFS_NAME = "PrefsFile";
    Fragment fragment = null;
    RecyclerView recyclerView;
    String subitem,path,user,pass;
    ServiceHandler shh;
    List<CartListPlanet> mPlanetlist= new ArrayList<CartListPlanet>();
    CartAdapter adapter;
    int ammount, totalamount;
    TextView textViewtotlcost;
    int rate,totcount,totalcost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        user = globalVariable.getUsername();
        pass = globalVariable.getloginPassword();

        textViewtotlcost=(TextView)findViewById(R.id.tvtotal_cost);

//        Display();

        new addCartItem().execute();

        imageViewback=(ImageView)findViewById(R.id.img_back);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CartListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        imageViewedit = (ImageView)findViewById(R.id.img_edit_pencile);
//        imageViewedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(CartListActivity.this, EditCartActivity.class);
//                startActivity(intent);
//            }
//        });


        recyclerView=(RecyclerView)findViewById(R.id.recyclecartlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartListActivity.this));

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarcartlist);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Cart List");


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


        preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_cart_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(CartListActivity.this, MainDashActivity.class);
                startActivity(intent1);
                return true;


            case R.id.cartpencile:
                Intent intent = new Intent(CartListActivity.this, EditCartActivity.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
//        if (item.getItemId() == android.R.id.home) {
//            finish(); // close this activity and return to preview activity (if there is any)
//        }


    }

//    public void Display()
//    {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null)
//        {
////            subitem = (String)bundle.get("SubItem");
////            rate = (String)bundle.get("Rate");
//            user = (String)bundle.get("Username");
//            pass = (String)bundle.get("Password");
//        }
//
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_menu:
                Intent intent8= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent8);
                break;

            case R.id.nav_login:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;


            case R.id.nav_about:
                Intent intent1 = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_offer:
                Intent intent2 = new Intent(getApplicationContext(), OfferActivity.class);
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

                Intent intent4 = new Intent(getApplicationContext(), MoneyWalletActivity.class);
                startActivity(intent4);
                break;

            case R.id.nav_refer:

                Intent intent5 = new Intent(getApplicationContext(), ReferCodeActivity.class);
                startActivity(intent5);
                break;

            case R.id.nav_rateus:

//                AppRate.with(this).setInstallDays(0).setLaunchTimes(2).setRemindInterval(2).monitor();
//                AppRate.showRateDialogIfMeetsConditions(this);

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.android.crome")));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;

            case R.id.nav_cartlist:

                Intent intent6 = new Intent(getApplicationContext(), CartList2Activity.class);
                startActivity(intent6);
                break;

            case R.id.nav_orderhis:

                Intent intent7 = new Intent(getApplicationContext(), OrderHistoryActivity.class);
                startActivity(intent7);
                break;

            default:
                fragmentClass = MenuFragment.class;
                break;
        }

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
//
//        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
        return true;
    }


    @Override
    public void onItemClick(int position) {

    }

    public class addCartItem extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            shh= new ServiceHandler();
            String url= path + "Cart/GetCartItems";
            Log.d("Url",">"+url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Username", user));
                params2.add(new BasicNameValuePair("Password", pass));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i =0; i< classArray.length();i++){
                        JSONObject a1 = classArray.getJSONObject(i);
                        subitem=a1.getString("SubItemName");
                        rate=a1.getInt("ItemRate");
                        totcount = a1.getInt("TotalCount");
                        totalcost = rate * totcount;

                        CartListPlanet planet = new CartListPlanet(subitem, String.valueOf(rate),String.valueOf(totalcost),String.valueOf(totcount));
                        mPlanetlist.add(planet);

                    }
                }
            }catch (JSONException e){
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
                    adapter=new CartAdapter(mPlanetlist);
                   recyclerView.setAdapter(adapter);

                }
            });

            int t1 = recyclerView.getAdapter().getItemCount();
            for (int i=0; i<t1; i++)
            {
                ammount = Integer.parseInt(mPlanetlist.get(i).getTotalCost());
                totalamount = totalamount + ammount;
            }
            textViewtotlcost.setText(String.valueOf(totalamount));


        }
    }


}
