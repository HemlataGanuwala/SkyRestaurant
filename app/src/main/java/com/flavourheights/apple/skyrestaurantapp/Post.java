package com.flavourheights.apple.skyrestaurantapp;

public class Post {

    String Fname;
    String Lname;
    String Email;
    String Mobileno;
    String Password;

    public String getFname(){
        return Fname;
    }

    public void setFname(String fname){
        this.Fname=fname;
    }

    public String getLname(){
        return Lname;
    }

    public void setLname(String lname)
    {
        this.Lname=lname;
    }

    public String getEmail(){
        return Email;
    }

    public void setEmail(String email){
        this.Email=email;
    }

    public String getMobileno(){
        return Mobileno;
    }

    public void setMobileno(String mobileno){
        this.Mobileno=mobileno;
    }

    public String getPassword(){
        return Password;
    }

    public void setPassword(String password){
        this.Password=password;
    }
}
