package com.example.parlemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_Login_module extends AppCompatActivity {
    EditText Login_email,Login_pass;
    Button login_user;





    TextView user_Registar,forgetpassword;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login_module);
        Login_email=findViewById(R.id.user_Email);
        Login_pass=findViewById(R.id.user_password);
        login_user=findViewById(R.id.login_user);
        user_Registar=findViewById(R.id.Registar1);
        forgetpassword=findViewById(R.id.forgetPassword);
        //intilalize
        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.user_Email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.user_password,RegexTemplate.NOT_EMPTY,R.string.invalid_password);
            firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.signOut();

        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (awesomeValidation.validate())
               {
                   String L_email=Login_email.getText().toString();
                   String L_password=Login_pass.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(L_email,L_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                if (firebaseAuth.getCurrentUser().isEmailVerified())
                                {
                                    Intent i=new Intent(User_Login_module.this,MiddleActivity.class);
                                    startActivity(i);
                                    finish();
                                }else
                                {
                                    Toast.makeText(User_Login_module.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                Toast.makeText(User_Login_module.this, "Check email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(User_Login_module.this, "Data not found ", Toast.LENGTH_SHORT).show();
                        }
                    });
               }

            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Login_email.getText().toString();
                if (email.isEmpty())
                {
                    Toast.makeText(User_Login_module.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(User_Login_module.this,"Check your Gmail",Toast.LENGTH_LONG);

                        }
                    });
                }
            }
        });
        user_Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(User_Login_module.this,Login_module.class);
                startActivity(i1);

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(User_Login_module.this, MiddleActivity.class);
            startActivity(i);
            finish();

        }
    }
}