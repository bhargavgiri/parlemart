package com.example.parlemart.Product_View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parlemart.Distributor_View.Distributor_add_product;
import com.example.parlemart.MyObject;
import com.example.parlemart.R;
import com.example.parlemart.Room.OrderSumary;
import com.example.parlemart.Room.OrderSummaryDatabase;
import com.example.parlemart.Wholeseller_View.WholesellerData;
import com.example.parlemart.Wholeseller_View.Wholeseller_module;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_FinalSubmit extends AppCompatActivity {

    RecyclerView recyclerView;
    Button submit;
    FirebaseAuth auth, firebaseAuth;
    String uid;
    TextView ftotal;
    int totalprice = 0;
    int total;
    FirebaseFirestore firestore, firebaseFirestore;
    WholesellerData wholesellerData;
    ArrayList<WholesellerData> list;
    String userId;
    String shope_ownar_name, shope_contact, shope_address, shope_email, shope_name, total1;
    public static String sname;
    String mydata;

    public String getImage;
    public String getQuantity;
    public String getItemType1;
    public String gettotal;
    public String getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__final_submit);
        ftotal = findViewById(R.id.ftotal);
        recyclerView = findViewById(R.id.R_view2);
        submit = findViewById(R.id.Submit_order);
        wholesellerData = new WholesellerData();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Wholeseller").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    shope_ownar_name = documentSnapshot.getString("shope_Ownar_name");
                    shope_contact = documentSnapshot.getString("shope_Contact");
                    shope_address = documentSnapshot.getString("shope_address");
                    shope_email = documentSnapshot.getString("shope_email");
                    shope_name = documentSnapshot.getString("shope_name");
                    sname = shope_name;
                    total1 = shope_ownar_name + "\n" + shope_name + "\n" + shope_address + "\n" + shope_contact + "\n" + shope_email;
                    Toast.makeText(Product_FinalSubmit.this, total1, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Product_FinalSubmit.this, "Data not exists...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        list = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        //Date
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String C_Date = currentDate.format(todayDate);
        List<OrderSumary> orderSummaryList = OrderSummaryDatabase.getOrderSummaryDatabase(Product_FinalSubmit.this).orderSummaryDao().getOrderSummaryList();

        System.out.println(orderSummaryList.size());
        OrderSummaryAdapter orderSummaryAdapter = new OrderSummaryAdapter(Product_FinalSubmit.this, orderSummaryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(Product_FinalSubmit.this));
        recyclerView.setAdapter(orderSummaryAdapter);

        for (int i = 0; i < orderSummaryList.size(); i++) {
            total = Integer.parseInt(orderSummaryList.get(i).getGettotal());
            totalprice = totalprice + total;
        }
        System.out.println(totalprice);
        ftotal.setText(String.valueOf(totalprice));

        // data Set
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   String shopename = shope_name;
                FirebaseFirestore firestore1;
                firestore1 = FirebaseFirestore.getInstance();

                /*for (int i = 0; i < orderSummaryList.size(); i++) {
                    getImage = orderSummaryList.get(i).getGetImage();
                    getQuantity = orderSummaryList.get(i).getGetQuantity();
                    getItemType1 = orderSummaryList.get(i).getGetItemType();
                    gettotal = orderSummaryList.get(i).getGettotal();
                    getDate = orderSummaryList.get(i).getGetDate();
                   mydata=String.valueOf(i);
                    Toast.makeText(Product_FinalSubmit.this,mydata, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Product_FinalSubmit.this,sname, Toast.LENGTH_SHORT).show();*/

                OrderSumary orderSumary = new OrderSumary(getImage, getQuantity, getItemType1, gettotal, getDate);
                DocumentReference documentReference1 = firestore1.collection("OrderSummary").document(sname);
                documentReference1.set(orderSumary).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(Product_FinalSubmit.this, "data set bro", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Product_FinalSubmit.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}