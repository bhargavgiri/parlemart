package com.example.parlemart.Wholeseller_View;

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
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.parlemart.Product_View.AllUser;
import com.example.parlemart.R;
import com.example.parlemart.SplashScreen;
import com.example.parlemart.User_Login_module;
import com.github.siyamed.shapeimageview.RoundedImageView;
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

public class Wholeseller_module extends AppCompatActivity {
    RoundedImageView W_image1;
    EditText S_name, S_Owner_name, S_Address, S_Contact_No, S_password, S_Confirme_password, S_email;
    Button S_submit;
    private String token;
    WholesellerData W_data;
    AllUser allUser;
    AwesomeValidation validation;
    FirebaseAuth auth;
  public static   String user_id;
    FirebaseFirestore firestore;
    Uri resultUri;
    String token2;
    static String profilepath;
  public   String shopename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholeseller_module);
        // image view
        W_image1 = findViewById(R.id.W_image);

        //Edit Text
        S_name = findViewById(R.id.Shope_Name);
        S_Owner_name = findViewById(R.id.Shope_Owner_name);
        S_Address = findViewById(R.id.Shope_Address);
        S_Contact_No = findViewById(R.id.Contact_No);
        S_password = findViewById(R.id.password);
        S_Confirme_password = findViewById(R.id.Confirme_password);
        S_email = findViewById(R.id.S_Email);
        S_submit = findViewById(R.id.submitdata);

        //database athuthenticate
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //intilalize
        validation = new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(this, R.id.Shope_Name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        validation.addValidation(this, R.id.Shope_Owner_name, RegexTemplate.NOT_EMPTY, R.string.invalid_Owner_name);
        validation.addValidation(this, R.id.Shope_Address, RegexTemplate.NOT_EMPTY, R.string.invalid_Shope_Address);
        validation.addValidation(this, R.id.Contact_No, "[5-9]{1}[0-9]{9}$", R.string.invalid_Contact);
        validation.addValidation(this, R.id.password, RegexTemplate.NOT_EMPTY, R.string.invalid_password);
        validation.addValidation(this, R.id.Confirme_password, R.id.password, R.string.invalid_Confirme_password);
        validation.addValidation(this, R.id.S_Email, Patterns.EMAIL_ADDRESS, R.string.invalid_Email);

        S_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String
                String s_name = S_name.getText().toString();
                shopename=s_name;
                String s_Owner_name = S_Owner_name.getText().toString();
                String s_Address = S_Address.getText().toString();
                String s_Contact_No = S_Contact_No.getText().toString();
                String s_password = S_password.getText().toString();
                // String s_Confirme_password=S_Confirme_password.getText().toString();
                String s_Email = S_email.getText().toString();
                if (validation.validate()) {
                    //token Create
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                token = task.getException().getMessage();
                                Log.w("FCM TOKEN Failed", task.getException());
                            } else {
                                token = task.getResult().getToken();
                                Log.i("FCM TOKEN", token);

                                Toast.makeText(Wholeseller_module.this, token, Toast.LENGTH_SHORT).show();
                                token2 = token;
                            }
                        }
                    });

                    auth.createUserWithEmailAndPassword(s_Email, s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        // data base store
                                        auth = FirebaseAuth.getInstance();
                                        user_id = auth.getCurrentUser().getUid();
                                        W_data = new WholesellerData(s_name, s_Owner_name, s_Address, s_Email, s_Contact_No, s_password, profilepath, token);
                                        firestore.collection("Wholeseller").document(user_id).set(W_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                allUser = new AllUser(s_Email, "wholeseller");
                                                firestore.collection("AllUser").document(user_id).set(allUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                       /* Toast.makeText(Wholeseller_module.this, "Data set Success...", Toast.LENGTH_SHORT).show();*/
                                                        Toast.makeText(Wholeseller_module.this, "please verify your email ID....", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(Wholeseller_module.this, User_Login_module.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Wholeseller_module.this, "Data not Uplod", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Wholeseller_module.this, "Data not Uplod", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Wholeseller_module.this, "Task not Success...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Wholeseller_module.this, " Accunot not creat", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        W_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //android.R.attr manifest;
                    if (ContextCompat.checkSelfPermission(Wholeseller_module.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Wholeseller_module.this, new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                    } else {

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(Wholeseller_module.this);
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
                resultUri = result.getUri();
                profilepath = resultUri.toString();
                W_image1.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
