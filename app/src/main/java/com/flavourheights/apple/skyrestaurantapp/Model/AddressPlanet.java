package com.flavourheights.apple.skyrestaurantapp.Model;

public class AddressPlanet {

    private String Housename;
    private String Landmark;
    private String Locality;
    private String City;
    private String Pincode;
    private String AddressType;
    private String CustName;
//    private String Check;

    public AddressPlanet(String custName, String addressType, String housename, String landmark, String locality, String city,String pincode)
    {
        this.CustName = custName;
        this.AddressType = addressType;
        this.Housename = housename;
        this.Landmark = landmark;
        this.Locality = locality;
        this.City = city;
        this.Pincode = pincode;
//        this.Check = check;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public String getHousename() {
        return Housename;
    }

    public void setHousename(String housename) {
        Housename = housename;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }


}
