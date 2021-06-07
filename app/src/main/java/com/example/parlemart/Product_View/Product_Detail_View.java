package com.example.parlemart.Product_View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parlemart.MainActivity;
import com.example.parlemart.R;
import com.example.parlemart.Room.OrderSumary;
import com.example.parlemart.Room.OrderSummaryDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product_Detail_View extends AppCompatActivity {
    TextView P_D_total, count, P_D_nameof_product;
    ImageView P_D_image;
    ImageButton P_D_Add, P_D_Sub;
    Spinner P_D_Price1, P_D_quantity1;
    Button P_D_add_product, P_D_submit;
    String Price1[] = {"5", "10", "30",};
    String Quantity[] = {"Box", "KGS(Bandho)", "Peace",};
    FirebaseFirestore firestore;
    List<Product_Data> list1;
    int count1 = 0;
    int q = 0;
    int finalCount = 0;
    int price = 0, quantity = 0, total = 0;
    int p_price = 0, p_quantity = 0, p_quality = 0;
    String imagepath1;//={list1};
    String pname;
    String p;
    String type = null;
    //database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail__view);
        P_D_image = findViewById(R.id.p_d_view_image);
        P_D_total = findViewById(R.id.no);
        count = findViewById(R.id.Count);
        P_D_nameof_product = findViewById(R.id.p_d_view_txt);
        P_D_Price1 = findViewById(R.id.p_d_price_list_spinner);
        P_D_quantity1 = findViewById(R.id.p_d_view_quantity_spinner);
        P_D_Add = findViewById(R.id.add_one);
        P_D_Sub = findViewById(R.id.sub_one);
        P_D_add_product = findViewById(R.id.Add_product);
        P_D_submit = findViewById(R.id.Submit_product);
        firestore = FirebaseFirestore.getInstance();
        //recyclerviwe


        imagepath1 = getIntent().getStringExtra("Imagewpath");
        pname = getIntent().getStringExtra("Pname");
        p = getIntent().getStringExtra("Price");
        P_D_nameof_product.setText(pname);
        Glide.with(Product_Detail_View.this).load(imagepath1).into(P_D_image);


        // ArrayAdapter<String> adapter=new ArrayAdapter<String>(Product_Detail_View.this,R.layout.support_simple_spinner_dropdown_item, Integer.parseInt(String.valueOf(Price1)));
        // adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //P_D_Price1.setAdapter(adapter);
        // P_D_Price1.setSelection(0);
        P_D_Price1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i == 0) {
                    price = 5;
                    p_price = price;
                       /* int item =P_D_Price1.getSelectedItemPosition();
                        String name=Price1[item];
                       // Toast.makeText(Product_Detail_View.this,name,Toast.LENGTH_LONG).show();*/
                } else if (i == 1) {
                    price = 10;
                    p_price = price;
                } else if (i == 2) {
                    price = 30;
                    p_price = price;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Box Spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Product_Detail_View.this, R.layout.support_simple_spinner_dropdown_item, Quantity);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        P_D_quantity1.setAdapter(arrayAdapter);

        P_D_quantity1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    type = "Box";
                    quantity = 650;
                    p_quantity = quantity;
                } else if (position == 1) {
                    type = "KGS";
                    quantity = 54;
                    p_quantity = quantity;
                } else if (position == 2) {
                    type = "Pis";
                    quantity = 5;
                    p_quantity = quantity;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //image Button + Counting
        P_D_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCount = finalCount + count1;
                q++;
                count.setText(String.valueOf(q));
                total = p_quantity * q;
                P_D_total.setText(String.valueOf(total));
            }
        });
        P_D_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q > 1) {
                    finalCount = finalCount - count1;
                    q--;
                    count.setText(String.valueOf(q));
                    total = total - p_quantity;
                    P_D_total.setText(String.valueOf(total));
                }
            }
        });
        P_D_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Date
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);
                //insert Data
                OrderSumary orderSumary = new OrderSumary(imagepath1, count.getText().toString(), type, String.valueOf(total), thisDate);
                OrderSummaryDatabase.getOrderSummaryDatabase(Product_Detail_View.this).orderSummaryDao().inserOrderSummary(orderSumary);
                Intent intent = new Intent(Product_Detail_View.this, Product_module.class);
                startActivity(intent);
            }
        });
    }
}