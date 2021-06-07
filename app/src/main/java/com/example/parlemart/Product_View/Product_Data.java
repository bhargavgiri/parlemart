package com.example.parlemart.Product_View;

import android.net.Uri;


public class Product_Data {
   String   p_name =null;
   String   box =null;
    String price=null;
    String  P_path=null;


    public Product_Data(String p_name, String box, String price,String  P_path) {
        this.p_name = p_name;
        this.box = box;
        this.price = price;
        this.P_path= P_path;
    }

    public Product_Data() {
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getP_path() {
        return P_path;
    }

    public void setP_path(String p_path) {
        P_path = p_path;
    }


}
