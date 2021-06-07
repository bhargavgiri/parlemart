package com.example.parlemart.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OrderSumary {


    public int getId;




    @PrimaryKey(autoGenerate = true)
    public int id;
    public String getImage;
    public String getQuantity;
    public String getItemType;
    public String  gettotal;
    public String getDate;



    public OrderSumary() {
    }

    public OrderSumary(String getImage, String getQuantity, String getItemType,String gettotal, String getDate) {
        this.getImage = getImage;
        this.getQuantity = getQuantity;
        this.getItemType = getItemType;
        this.gettotal=gettotal;
        this.getDate = getDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGetImage() {
        return getImage;
    }

    public void setGetImage(String getImage) {
        this.getImage = getImage;
    }

    public String getGetQuantity() {
        return getQuantity;
    }

    public void setGetQuantity(String getQuantity) {
        this.getQuantity = getQuantity;
    }

    public String getGetItemType() {
        return getItemType;
    }

    public void setGetItemType(String getItemType) {
        this.getItemType = getItemType;
    }

    public String getGettotal() {
        return gettotal;
    }

    public void setGettotal(String gettotal) {
        this.gettotal = gettotal;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }
}
