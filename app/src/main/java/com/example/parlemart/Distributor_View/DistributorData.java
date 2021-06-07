package com.example.parlemart.Distributor_View;

public class DistributorData {

    String  d_name;
    String  d_Owner_name;
    String  d_gst_no;
    String  d_address;
    String  d_city ;
    String  d_pincord;
    String  d_email;
    String  d_contect;
    String  d_pass;

    String   d_Profile;
    String d_token;

    public DistributorData(String d_name, String d_Owner_name, String d_gst_no, String d_address, String d_city, String d_pincord, String d_email, String d_contect, String d_pass, String d_Profile,String d_token) {
        this.d_name = d_name;
        this.d_Owner_name = d_Owner_name;
        this.d_gst_no = d_gst_no;
        this.d_address = d_address;
        this.d_city = d_city;
        this.d_pincord = d_pincord;
        this.d_email = d_email;
        this.d_contect = d_contect;
        this.d_pass = d_pass;
        this.d_Profile = d_Profile;
        this.d_token=d_token;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_Owner_name() {
        return d_Owner_name;
    }

    public void setD_Owner_name(String d_Owner_name) {
        this.d_Owner_name = d_Owner_name;
    }

    public String getD_gst_no() {
        return d_gst_no;
    }

    public void setD_gst_no(String d_gst_no) {
        this.d_gst_no = d_gst_no;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }

    public String getD_city() {
        return d_city;
    }

    public void setD_city(String d_city) {
        this.d_city = d_city;
    }

    public String getD_pincord() {
        return d_pincord;
    }

    public void setD_pincord(String d_pincord) {
        this.d_pincord = d_pincord;
    }

    public String getD_email() {
        return d_email;
    }

    public void setD_email(String d_email) {
        this.d_email = d_email;
    }

    public String getD_contect() {
        return d_contect;
    }

    public void setD_contect(String d_contect) {
        this.d_contect = d_contect;
    }

    public String getD_pass() {
        return d_pass;
    }

    public void setD_pass(String d_pass) {
        this.d_pass = d_pass;
    }

    public String getD_Profile() {
        return d_Profile;
    }

    public void setD_Profile(String d_Profile) {
        this.d_Profile = d_Profile;
    }

    public String getD_token() {
        return d_token;
    }

    public void setD_token(String d_token) {
        this.d_token = d_token;
    }
}
