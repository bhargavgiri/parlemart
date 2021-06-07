package com.example.parlemart.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OrderSumary.class},version = 1,exportSchema = false)
public abstract class OrderSummaryDatabase extends RoomDatabase {






    public abstract OrderSummaryDao orderSummaryDao();
    public static OrderSummaryDatabase orderSummaryDatabase;
    public static OrderSummaryDatabase getOrderSummaryDatabase(Context context)
    {
        if(orderSummaryDatabase==null)
        {
            orderSummaryDatabase= Room.databaseBuilder(context,OrderSummaryDatabase.class,"orderSummary").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return orderSummaryDatabase;
    }

}
