package com.example.parlemart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.parlemart.Distributor_View.Distributor_add_product;
import com.example.parlemart.Wholeseller_View.Wholeller_Oreder_Lilst;
import com.example.parlemart.Wholeseller_View.WholesellerData;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    com.google.android.material.floatingactionbutton.FloatingActionButton fb;
    Toolbar toolbar111;
    RecyclerView r_view;
    ArrayList<WholesellerData> list;
    FirebaseFirestore f_store;
    ArrayAdapter adapter;
    SpinKitView spinKitView;
    Wholeller_Oreder_Lilst W_ilst;
    List<ArrayList<WholesellerData>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ActionButton
        fb=findViewById(R.id.floatingActionButton11);
        f_store=FirebaseFirestore.getInstance();
        r_view=findViewById(R.id.R_view);
        auth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        spinKitView=findViewById(R.id.spin_kit);
          W_ilst=new Wholeller_Oreder_Lilst(MainActivity.this,list);
        //toolbar
        toolbar111=findViewById(R.id.toolbar11);
        setSupportActionBar( toolbar111);
        //Data ge
        f_store.collection("Wholeseller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> D_list=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : D_list)
                        {
                            spinKitView.setVisibility(r_view.GONE);
                            WholesellerData e=d.toObject(WholesellerData.class);
                                list.add(e);
                        }
                    Toast.makeText(MainActivity.this, "Data set..", Toast.LENGTH_SHORT).show();
                    //RecyclerView
                    r_view.setAdapter(W_ilst);
                    r_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                }else{
                    Toast.makeText(MainActivity.this, "Data is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed To Load", Toast.LENGTH_SHORT).show();
            }
        });

        //ActionButton Event
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, Distributor_add_product.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  MenuInflater mi=getMenuInflater();
        // mi.inflate(R.menu.mainlist,menu);

        //Search view

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainlist, menu);

        MenuItem Search = menu.findItem(R.id.S_view);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) Search.getActionView();
        searchView.setQueryHint("Search here");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<WholesellerData> newList = new ArrayList<>();
                for (WholesellerData parseItem : list) {
                    String title = parseItem.getShope_name().toLowerCase();

                    // you can specify as many conditions as you like
                    if (title.contains(newText)) {
                        newList.add(parseItem);
                    }
                }
                // create method in adapter
                W_ilst.setFilter(newList);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.addproduct)
        {
            Toast.makeText(this, "product add menu", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId()==R.id.D_list)
        {
            Toast.makeText(this, "display details", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId()==R.id.logout)
        {
            Intent intent=new Intent(MainActivity.this,User_Login_module.class);
            startActivity(intent);
            auth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}