package com.flavourheights.apple.skyrestaurantapp;

import android.app.Application;

public class GlobalClass extends Application {


    public String constr = "";
    public String Username = "";
    public String LoginPassword = "";
    public String MobileNo = "";

    public String getconstr() {

        return constr;
    }

    public void setconstr(String aconstr) {

        constr = aconstr;

    }

    public String getUsername() {

        return Username;
    }

    public void setUsername(String username) {

        Username = username;

    }

    public String getloginPassword() {

        return LoginPassword;
    }

    public void setloginPassword(String loginPassword) {

        LoginPassword = loginPassword;

    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
