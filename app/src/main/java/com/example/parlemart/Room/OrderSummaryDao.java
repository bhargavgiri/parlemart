package com.example.parlemart.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderSummaryDao {








    @Insert
    public void inserOrderSummary(OrderSumary orderSumary);

    @Query("select * from OrderSumary")
    List<OrderSumary> getOrderSummaryList();
}
