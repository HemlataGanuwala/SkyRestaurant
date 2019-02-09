package com.flavourheights.apple.skyrestaurantapp;

public class Post {

    private String firefname;
    private String firelname;
    private String fireemail;
    private String firemobileno;
    private String firepassword;
    private String user;
    private  String frreuserid;

    public Post(String username, String email) {

        this.user=username;
        this.fireemail=email;
    }

//    public Post(String firefname, String firelname, String fireemail, String firemobileno, String firepassword){
//        this.firefname=firefname;
//        this.firelname=firelname;
//        this.fireemail=fireemail;
//        this.firemobileno=firemobileno;
//        this.firepassword=firepassword;
//    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrreuserid() {
        return frreuserid;
    }

    public void setFrreuserid(String frreuserid) {
        this.frreuserid = frreuserid;
    }

    public String getFirefname() {
        return firefname;
    }

    public void setFirefname(String firefname) {
        this.firefname = firefname;
    }

    public String getFirelname() {
        return firelname;
    }

    public void setFirelname(String firelname) {
        this.firelname = firelname;
    }

    public String getFireemail() {
        return fireemail;
    }

    public void setFireemail(String fireemail) {
        this.fireemail = fireemail;
    }

    public String getFiremobileno() {
        return firemobileno;
    }

    public void setFiremobileno(String firemobileno) {
        this.firemobileno = firemobileno;
    }

    public String getFirepassword() {
        return firepassword;
    }

    public void setFirepassword(String firepassword) {
        this.firepassword = firepassword;
    }
}
