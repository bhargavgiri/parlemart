 package com.example.parlemart.Distributor_View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parlemart.MainActivity;
import com.example.parlemart.Product_View.Model;
import com.example.parlemart.Product_View.Product_Data;
import com.example.parlemart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Distributor_add_product extends AppCompatActivity {

    Uri  resultUri;
    ImageView product_image;
    static String P_path;
   // Uri R_uri;
    EditText P_name,Box,Price;
    Button add,submit;
    SwipeRefreshLayout refresh;
    FirebaseFirestore store;
    Product_Data P_data;
    StorageReference storageReference;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference("Product_detail");
    StorageReference reference=FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_add_product);
        //Edit Text
        P_name=findViewById(R.id.P_name);
        Box=findViewById(R.id.P_Box);
        Price=findViewById(R.id.P_price);
        storageReference= FirebaseStorage.getInstance().getReference();

        //Buttons
        add=findViewById(R.id.Add_product);
        submit=findViewById(R.id.P_Submit);

        // data base details
        store=FirebaseFirestore.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(resultUri!=null)
                {
                    uploadToFirebase(resultUri);
                }
                else
                {
                    Toast.makeText(Distributor_add_product.this, "please select the image", Toast.LENGTH_SHORT).show();
                }
                */
                
                StorageReference Ref=storageReference.child(System.currentTimeMillis() + "." +getExtension(resultUri));
                Ref.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String p_name=P_name.getText().toString();
                                String box=Box.getText().toString();
                                String  price=Price.getText().toString();
                               /* String s=taskSnapshot.toString();
                                Toast.makeText(Distributor_add_product.this,s, Toast.LENGTH_SHORT).show();*/
                                P_data=new Product_Data( p_name, box,price,uri.toString());
                                store.collection("Product_detail").document(p_name).set(P_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Intent i = new Intent(Distributor_add_product.this, Distributor_add_product.class);
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(i);
                                        overridePendingTransition(0, 0);
                                        P_name.setText("");
                                        Box.setText("");
                                        Price.setText("");


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Distributor_add_product.this, "Task Fail", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Distributor_add_product.this,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        //submit
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Distributor_add_product.this, MainActivity.class);
                startActivity(i);

            }
        });

        //product Image
        product_image=findViewById(R.id.P_image);
        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    //android.R.attr manifest;
                    if(ContextCompat.checkSelfPermission(Distributor_add_product.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(Distributor_add_product.this,new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE} ,101);
                    }
                    else
                    {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(Distributor_add_product.this);
                    }
                }
            }
        });
    }
    private String getExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
               P_path= resultUri.toString();
                product_image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
}
private void uploadToFirebase(Uri uri)
{
        StorageReference  fileRef=reference.child(System.currentTimeMillis() + "." + getExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model=new Model(uri.toString());
                        String modelId=root.push().getKey();
                        root.child(modelId).setValue(model);

                        Toast.makeText(Distributor_add_product.this, "uploading done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Distributor_add_product.this, "uploading failed...", Toast.LENGTH_SHORT).show();
            }
        });
}


}