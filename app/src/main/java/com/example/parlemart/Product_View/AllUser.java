package com.example.parlemart.Product_View;

public class AllUser {
    String email;
    String catagory;

    public AllUser() {
    }

    public AllUser(String email, String catagory) {
        this.email = email;
        this.catagory = catagory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}
