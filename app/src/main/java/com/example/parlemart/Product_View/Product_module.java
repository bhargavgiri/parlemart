 package com.example.parlemart.Product_View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.parlemart.R;
import com.example.parlemart.User_Login_module;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Product_module extends AppCompatActivity {
    Toolbar toolbar_p;
    RecyclerView recyclerView;
    ArrayList<Product_Data> list1;
    FirebaseAuth auth,firebaseAuth;
    FirebaseFirestore f_store,firebaseFirestore;
    String imagepath1, pname;
    Button P_D_submit;
    String userId;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_module);


        f_store = FirebaseFirestore.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();

        auth = FirebaseAuth.getInstance();


        P_D_submit = findViewById(R.id.Submit_product);
        P_D_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(Product_module.this, Product_FinalSubmit.class);
                startActivity(ii);
                finish();
            }
        });

        DocumentReference documentReference=firebaseFirestore.collection("Wholeseller").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    name=documentSnapshot.getString("shope_Ownar_name");
                    Toast.makeText(Product_module.this,name, Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(Product_module.this, "Data not exists...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        //recyclerviwe
        recyclerView = findViewById(R.id.R_view11);
        //Array list
        list1 = new ArrayList<>();
        //tool bar
        toolbar_p = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar_p);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                //txt.append(key + ": " + value + "\n\n");
                Toast.makeText(this, key + ":" + value + "\n", Toast.LENGTH_LONG).show();
            }
        }

        f_store.collection("Product_detail").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> D_list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d1 : D_list) {
                        Product_Data e = d1.toObject(Product_Data.class);
                        list1.add(e);
                    }
                    //RecyclerView
                    Product_Adapter product_adapter = new Product_Adapter(Product_module.this, list1);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(product_adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(Product_module.this, 2));
                } else {
                    Toast.makeText(Product_module.this, "data is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Product_module.this, "Task is fail", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(Product_module.this, User_Login_module.class);
            startActivity(intent);
            auth.signOut();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}