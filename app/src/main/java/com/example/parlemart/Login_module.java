package com.example.parlemart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.parlemart.Distributor_View.Distributor_module;
import com.example.parlemart.Wholeseller_View.Wholeseller_module;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Login_module extends AppCompatActivity {
    FirebaseAuth auth;
    Button Wholeseller1,Distributor1;
    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_module);
        imageSlider=findViewById(R.id.sider);
        Wholeseller1=findViewById(R.id.Wholeseller);
        Distributor1=findViewById(R.id.Distributor);
        auth = FirebaseAuth.getInstance();

        //imageSlider
        List<SlideModel> siledemodel=new ArrayList<>();
        siledemodel.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/parlemart-52150.appspot.com/o/1hbanner_banner.jpg?alt=media&token=26aa590c-b667-442f-a80d-5d608de4e6f9",""));
        siledemodel.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/parlemart-52150.appspot.com/o/1617597928243.null?alt=media&token=838666a6-754c-4258-8182-5b9512fb469c",""));
        siledemodel.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/parlemart-52150.appspot.com/o/1CategoryImg_biscuits.png?alt=media&token=e7918c02-7040-497a-87b5-818934e981a6",""));

        imageSlider.setImageList(siledemodel,true);

        Wholeseller1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_module.this, Wholeseller_module.class);
                startActivity(i);

            }
        });
        Distributor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_module.this, Distributor_module.class);
                startActivity(i);
            }
        });

    }


}