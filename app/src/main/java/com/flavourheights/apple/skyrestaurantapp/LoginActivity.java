package com.flavourheights.apple.skyrestaurantapp;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.flavourheights.apple.skyrestaurantapp.GoogleHelper.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    EditText editTextuserid, editTextpassword;
    TextView textViewregister, textViewforgetpass;
    LoginButton facebook;
    SignInButton buttongooglelogin;
    Button buttonlogin;
    String username, password;
    String path, email, mobile, pass;
    int Status;
    ServiceHandler shh;
    ProgressDialog progress;
    private FacebookHelper facebookHelper;
    private GoogleHelper googleHelper;
    private boolean isFbLogin = false;
    private static final String TAG = LoginActivity.class.getSimpleName();
    CallbackManager callbackManager;
    NavigationView navigationView;
    private SharedPreferences preferences;
    private DrawerLayout drawerLayout;
    View headerview;
    ImageView imageViewshow;
    Class fragmentClass;
    private static final String PREFS_NAME = "PrefsFile";
    Fragment fragment = null;
    private CheckBox mcheck;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        editTextuserid=(EditText)findViewById(R.id.etusername);
        editTextpassword=(EditText)findViewById(R.id.etpassword);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        username = editTextuserid.getText().toString();
        password = editTextpassword.getText().toString();
        globalVariable.setUsername(username);
        globalVariable.setloginPassword(password);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarlogin);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        buttongooglelogin = (SignInButton)findViewById(R.id.btgoogle);

        buttongooglelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        mcheck = (CheckBox)findViewById(R.id.chkrememberme);

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



        textViewforgetpass=(TextView)findViewById(R.id.tvforgetpass);
        textViewforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        textViewregister=(TextView)findViewById(R.id.tvregister);
        textViewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        buttonlogin=(Button)findViewById(R.id.btnlogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertLoginData();

                if (username.equals("PhoneNo") || username.equals("Email"))
                {
                    new LoginData().execute();
                }else {
                    Toast.makeText(LoginActivity.this, "Username Not Matched", Toast.LENGTH_LONG).show();
                }

                if (password.equals("Password"))
                {
                    new LoginData().execute();
                }else {
                    Toast.makeText(LoginActivity.this, "Password Not Matched", Toast.LENGTH_LONG).show();
                }

            }
        });


        facebook=(LoginButton)findViewById(R.id.btfacebook);
        callbackManager=CallbackManager.Factory.create();
        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

//        KeyHashGenerator.generateKey(this);
//        facebookHelper=new FacebookHelper(this, this);
//        textViewfacebook=(TextView)findViewById(R.id.tvfacebook);
//        textViewfacebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                facebookHelper.connect();
//                isFbLogin = true;
//            }
//        });
//
//        googleHelper=new GoogleHelper(this, this);
//        googleHelper.connect();
//        textViewgoogle=(TextView)findViewById(R.id.tvgoogle);
//        textViewgoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                googleHelper.signIn();
//                isFbLogin = false;
//            }
//        });

        getPreferencedata();


    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
           // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

//    private void updateUI(boolean signedIn) {
//        if (signedIn) {
//            buttongooglelogin.setVisibility(View.GONE);
//            signOutButton.setVisibility(View.VISIBLE);
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            Bitmap icon =                  BitmapFactory.decodeResource(getContext().getResources(),R.drawable.user_defaolt);
//            imgProfilePic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),icon, 200, 200, 200, false, false, false, false));
//            signInButton.setVisibility(View.VISIBLE);
//            signOutButton.setVisibility(View.GONE);
//        }
//    }

    private void getPreferencedata() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            String u = sp.getString("pref_name", "not found");
            editTextuserid.setText(u.toString());
        }
        if (sp.contains("pref_pass")) {
            String p = sp.getString("pref_pass", "not found");
            editTextpassword.setText(p.toString());
        }
        if (sp.contains("pref_check")) {
            Boolean c = sp.getBoolean("pref_check", false);
            mcheck.setChecked(c);
        }
    }

    public void insertLoginData()
    {
        username=editTextuserid.getText().toString();
        password=editTextpassword.getText().toString();

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        googleHelper.onStart();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        callbackManager.onActivityResult(requestCode, resultCode, data);
////        super.onActivityResult(requestCode, resultCode, data);
////        googleHelper.onActivityResult(requestCode, resultCode, data);
////        facebookHelper.onActivityResult(requestCode, resultCode, data);
////        if (isFbLogin) {
////            isFbLogin = false;
////        }
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
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
        return true;
    }
//
//    @Override
//    public void OnFbSignInComplete(GraphResponse graphResponse, String error) {
//
//        if(error==null)
//        {
//            try {
//                JSONObject jsonObject = graphResponse.getJSONObject();
//                String id = jsonObject.getString("id");
//                String profileImg = "http://graph.facebook.com/" + id + "/picture?type=large";
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    @Override
//    public void OnGSignInSuccess(GoogleSignInAccount googleSignInAccount) {
//
//    }
//
//    @Override
//    public void OnGSignInError(String error) {
//        Log.e(TAG, error);
//    }
//
//    @Override public void onAPICallStarted() {
//        Log.i(TAG, "onAPICallStarted");
//    }



    class LoginData extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            shh = new ServiceHandler();
            String url = path + "Registration/Logindata";
            Log.d("Url: ", "> " + url);

            try

            {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Email", username));
                params2.add(new BasicNameValuePair("Password", password));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    Status= jsonObject.getInt("Status");

                } else {
                    Toast.makeText(getBaseContext(), "Data not Found", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {

                e.printStackTrace();

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();

            if (Status == 1) {

                if (mcheck.isChecked())
                {
                    Boolean boolIscheck = mcheck.isChecked();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pref_name",editTextuserid.getText().toString());
                    editor.putString("pref_pass",editTextpassword.getText().toString());
                    editor.putBoolean("pref_check",boolIscheck);
                    editor.apply();
                }
                else
                {
                    preferences.edit().clear().apply();
                }

                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                globalVariable.setUsername(username);
                globalVariable.setloginPassword(password);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("User",username);
                intent.putExtra("Password",password);
                startActivity(intent);
//                editTextuserid.setText("");
//                editTextpassword.setText("");

            }else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                editTextuserid.setText("");
                editTextpassword.setText("");
            }
        }

    }

}
