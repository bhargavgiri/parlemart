package com.example.parlemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.parlemart.Product_View.AllUser;
import com.example.parlemart.Product_View.Product_module;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.List;

public class MiddleActivity extends AppCompatActivity {

    FirebaseFirestore f_stor;
    AllUser allUser;
    FirebaseAuth auth;
    String user_id;
    String category;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
        f_stor=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();

        if(user==null) {
            Intent i=new Intent(MiddleActivity.this,User_Login_module.class);
            startActivity(i);
            finish();
        }
        else {
            user_id=auth.getCurrentUser().getUid();
            documentReference=f_stor.collection("AllUser").document(user_id);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists())
                    {
                        category=documentSnapshot.getString("catagory");
                        Toast.makeText(MiddleActivity.this,category, Toast.LENGTH_SHORT).show();
                        switch (category)
                        {
                            case "Distributor" :
                                Toast.makeText(MiddleActivity.this, category, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MiddleActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "wholeseller" :
                                Toast.makeText(MiddleActivity.this, category, Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(MiddleActivity.this, Product_module.class);
                                startActivity(i);
                                finish();
                                break;
                            default:
                                Toast.makeText(MiddleActivity.this,category, Toast.LENGTH_SHORT).show();
                                break;

                        }

                    }else {
                        Toast.makeText(MiddleActivity.this, "user does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }


    }



}