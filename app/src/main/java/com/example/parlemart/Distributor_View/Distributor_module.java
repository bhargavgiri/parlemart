package com.example.parlemart.Distributor_View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.parlemart.Product_View.AllUser;
import com.example.parlemart.R;
import com.example.parlemart.User_Login_module;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Distributor_module extends AppCompatActivity {
EditText D_name,D_Owner_name,D_gst_no,D_address,D_city,D_pincord,D_email,D_contect,D_pass,D_co_pass;
Button D_submit ;
ImageView D_image1;
AwesomeValidation validation;
Uri D_resultUri;
public  static String   D_token;
    static String d_Profile;
    //database data
    DistributorData D_Data;
    // data base
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    String D_user_id;
    AllUser allUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_module);

        //EditText
        D_name=findViewById(R.id.D_Name);
        D_Owner_name=findViewById(R.id.D_Owner_name);
        D_gst_no=findViewById(R.id.D_GST_NO);
        D_address=findViewById(R.id.D_Address);
        D_city=findViewById(R.id.D_City);
        D_pincord=findViewById(R.id.D_Pincod);
        D_email=findViewById(R.id.D_Email);
        D_contect=findViewById(R.id.D_Contact_No);
        D_pass=findViewById(R.id.D_password);
        D_co_pass=findViewById(R.id.D_Confirme_password);

        //image
        D_image1=findViewById(R.id.D_image);
        // validation
        validation=new AwesomeValidation(ValidationStyle.BASIC);
        //Button
        D_submit=findViewById(R.id.D_submitdata);

        //Initialisation database
            auth=FirebaseAuth.getInstance();
            firebaseFirestore=FirebaseFirestore.getInstance();

            //Validation

        validation.addValidation(this,R.id.D_Name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        validation.addValidation(this,R.id.D_Owner_name,RegexTemplate.NOT_EMPTY,R.string.invalid_Owner_name);
        validation.addValidation(this,R.id.D_GST_NO, RegexTemplate.NOT_EMPTY,R.string.invalid_GST);
        validation.addValidation(this,R.id.D_Address,RegexTemplate.NOT_EMPTY,R.string.invalid_Shope_Address);
        validation.addValidation(this,R.id.D_City, RegexTemplate.NOT_EMPTY,R.string.invalid_City);
        validation.addValidation(this,R.id.D_Pincod, RegexTemplate.NOT_EMPTY,R.string.invalid_Pincord);
        validation.addValidation(this,R.id.D_Contact_No,"[5-9]{1}[0-9]{9}$",R.string.invalid_Contact);
        validation.addValidation(this,R.id.D_password,RegexTemplate.NOT_EMPTY,R.string.invalid_password);
        validation.addValidation(this,R.id.D_Confirme_password,R.id.D_password,R.string.invalid_Confirme_password);
        validation.addValidation(this,R.id.D_Email, Patterns.EMAIL_ADDRESS,R.string.invalid_Email);


        //Click ,Action
       D_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String  d_name=D_name.getText().toString();
               String  d_Owner_name=D_Owner_name.getText().toString();
               String  d_gst_no=D_gst_no.getText().toString();
               String  d_address=D_address.getText().toString();
               String  d_city=D_city.getText().toString();
               String  d_pincord=D_pincord.getText().toString();
               String  d_email=D_email.getText().toString();
               String  d_contect=D_contect.getText().toString();
               String  d_pass=D_pass.getText().toString();
               String  d_co_pass=D_co_pass.getText().toString();
               if (validation.validate())
               {
                   FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                       @Override
                       public void onComplete(@NonNull Task<InstanceIdResult> task) {
                           if (!task.isSuccessful()) {
                               D_token = task.getException().getMessage();
                               Log.w("FCM TOKEN Failed", task.getException());
                           } else {
                               D_token= task.getResult().getToken();
                               Log.i("FCM TOKEN",  D_token);
                               //  text1.setText(token);
                               Toast.makeText(Distributor_module.this, D_token, Toast.LENGTH_SHORT).show();
                           }
                       }
                   });


                   Toast.makeText(Distributor_module.this, "Okay", Toast.LENGTH_SHORT).show();
                   auth.createUserWithEmailAndPassword(d_email,d_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                 auth=FirebaseAuth.getInstance();
                                 D_user_id=auth.getCurrentUser().getUid();
                                 D_Data=new DistributorData(d_name,d_Owner_name,d_gst_no,d_address,d_city,d_pincord,d_email,d_contect,d_pass,d_Profile, D_token);
                                 firebaseFirestore.collection("Distributor").document(D_user_id).set(D_Data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         allUser=new AllUser(d_email,"Distributor");
                                         firebaseFirestore.collection("AllUser").document(D_user_id).set(allUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                             @Override
                                             public void onSuccess(Void aVoid) {
                                                 Toast.makeText(Distributor_module.this, "data is set ", Toast.LENGTH_SHORT).show();
                                                 Intent i=new Intent(Distributor_module.this, User_Login_module.class);
                                                 startActivity(i);
                                                 finish();
                                             }
                                         }).addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 Toast.makeText(Distributor_module.this, "data id not set task fail", Toast.LENGTH_SHORT).show();
                                             }
                                         });

                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(Distributor_module.this, "data id not set task fail", Toast.LENGTH_SHORT).show();
                                     }
                                 });
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(Distributor_module.this, "Email verification fail", Toast.LENGTH_SHORT).show();
                               }
                           });
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Distributor_module.this, "Task fail", Toast.LENGTH_SHORT).show();
                       }
                   });
               }else
               {
                   Toast.makeText(Distributor_module.this, "NOT Okay", Toast.LENGTH_SHORT).show();
               }
           }

       });


       //image view photo set
        D_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if (ContextCompat.checkSelfPermission(Distributor_module.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(Distributor_module.this,new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE},102);
                    }
                    else
                    {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(Distributor_module.this);
                    }
                }

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
               D_resultUri = result.getUri();
                d_Profile = D_resultUri.toString();
                D_image1.setImageURI(D_resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}