package com.flavourheights.apple.skyrestaurantapp.Model;

public class ItemPlanet {

    private String Itemname;
    private String SubItemname;
    private String Rate;
    private String Image;

    public ItemPlanet(String itemname, String subItemname, String rate,String image)
    {
        this.Itemname = itemname;
        this.SubItemname = subItemname;
        this.Rate = rate;
        this.Image = image;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getSubItemname() {
        return SubItemname;
    }

    public void setSubItemname(String subItemname) {
        SubItemname = subItemname;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
