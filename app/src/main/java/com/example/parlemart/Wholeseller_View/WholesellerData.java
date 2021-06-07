package com.example.parlemart.Wholeseller_View;

public class WholesellerData {

    String Shope_name;
    String Shope_Ownar_name;
    String Shope_address;
    String Shope_email;
    String Shope_Contact;
    String Shope_password;
    String profile_path;
    String token;


    public WholesellerData() {
    }

    public WholesellerData(String s_name, String s_owner_name, String s_address, String s_email, String s_contact_no, String s_password, String profilepath,String token) {
        this.Shope_name=s_name;
        this.Shope_Ownar_name=s_owner_name;
        this.Shope_address=s_address;
        this.Shope_email=s_email;
        this.Shope_Contact=s_contact_no;
        this.Shope_password=s_password;
        this.profile_path=profilepath;
        this.token=token;

    }

    public String getShope_name() {
        return Shope_name;
    }

    public void setShope_name(String shope_name) {
        Shope_name = shope_name;
    }

    public String getShope_Ownar_name() {
        return Shope_Ownar_name;
    }

    public void setShope_Ownar_name(String shope_Ownar_name) {
        Shope_Ownar_name = shope_Ownar_name;
    }

    public String getShope_address() {
        return Shope_address;
    }

    public void setShope_address(String shope_address) {
        Shope_address = shope_address;
    }

    public String getShope_email() {
        return Shope_email;
    }

    public void setShope_email(String shope_email) {
        Shope_email = shope_email;
    }

    public String getShope_Contact() {
        return Shope_Contact;
    }

    public void setShope_Contact(String shope_Contact) {
        Shope_Contact = shope_Contact;
    }

    public String getShope_password() {
        return Shope_password;
    }

    public void setShope_password(String shope_password) {
        Shope_password = shope_password;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
