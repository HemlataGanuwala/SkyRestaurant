package com.flavourheights.apple.skyrestaurantapp.Model;

public class OfferPlanet {

    private String FestName;
    private String FromDt;
    private String Todt;
    private String Discount;

    public OfferPlanet(String festName, String fromDt, String todt, String discount)
    {
        this.FestName = festName;
        this.FromDt = fromDt;
        this.Todt = todt;
        this.Discount = discount;
    }

    public String getFestName() {
        return FestName;
    }

    public void setFestName(String festName) {
        FestName = festName;
    }

    public String getFromDt() {
        return FromDt;
    }

    public void setFromDt(String fromDt) {
        FromDt = fromDt;
    }

    public String getTodt() {
        return Todt;
    }

    public void setTodt(String todt) {
        Todt = todt;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}


