package com.example.parlemart.Distributor_View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parlemart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Distributor_View_Oreder_module extends AppCompatActivity {
    private static final String AUTH_KEY = "key=AAAArGnNveY:APA91bEYqgEcwGSEmoIfXTjuFdz64pmKDUPL5P3Ssen7rOKDAt5rkodTpFs5kRz1J3L41m6u6-iEj43vJVlIB2T8KFIM2xQFfNCFOw5yN4iRYfA0M9UWms2C2lq6sUpq56dJHZpZL-d7";
    Button O_submit;
    private String W_token;
    String s_name,So_name,contact;
    TextView Shopename,Shopeownername,Contact;
    public  String token4=  "ctoJBDe8QZKpHQQRdpP7u8:APA91bGhRttJ16Oyh70KWVn16uLZTtFCQS5NPgTZHIMu5zo9lazHruTFaWTTDtWLREI_fRIygF3DEhktg3Dzy06nHbOiq8tcVWNfabCXaRsTorhUsv1TGgebdjgbALCQ2dFJdCQDTx_4";
    public  String token3="dRbPvpQxgZs:APA91bEAVr0atKS0Wizdf6rNbbI6y5nSJynBlOzoqpim4empS9qfYbg9PPrHAkXsjd-iwiFtkza4MpV6z8_0b-bBuM2ClahdpeonhHNScDUdIlnVTK4ijecKb1qa1Yy_marvkkb0awz-";
    //public String token2="c0p3iBwLAEc:APA91bFZefnKUoemRYF-edCMlC0IeSABShzKRAmAjYG8hwH3M8UIMEiyFNGuJ4kR9bSXstywcOLAWFBDQ10EOBfgj_HuJfa5-bj_ry2ZOTDMRrFR_yz1SpYCBtfLfS2ca6NI3hXXz7Qm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor__view__oreder_module);
        O_submit=findViewById(R.id.O_submit1);
        Shopename=findViewById(R.id.shopename);
        Shopeownername=findViewById(R.id.ownwrname);
        Contact=findViewById(R.id.contectno);

        //get method
        s_name=getIntent().getStringExtra("sname");
        So_name=getIntent().getStringExtra("soname");
        contact=getIntent().getStringExtra("contact");
        W_token=getIntent().getStringExtra("w_token");
        Toast.makeText(this, W_token, Toast.LENGTH_SHORT).show();
        //set text
        Shopename.setText(s_name);
        Shopeownername.setText(So_name);
        Contact.setText(contact);

        // Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tmp = "";
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                tmp += key + ": " + value + "\n\n";
            }
            //mTextView.setText(tmp);
            Toast.makeText(this, tmp, Toast.LENGTH_SHORT).show();
        }
        //Crate token



        O_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWithOtherThread("token");
            }

            private void sendWithOtherThread(final String token) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pushNotification(token);
                    }
                }).start();
            }
        });

    }

    private void pushNotification(String token) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jNotification.put("title", "Parle Mart ");
            jNotification.put("body", "Your Order is Ready (App)");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");
            jNotification.put("icon", "ic_notification");

            jData.put("picture", "https://goflamingo.co.in/static/img/clients/parle_logo.png");
/*
			switch(type) {
				*//*case "tokens":
					JSONArray ja = new JSONArray();
				//	ja.put("dZ4v2CwiTjuSk3DhVvcSw7:APA91bEUZFzfivQMXlHDzt8HMr2670XBd2WRaSsR1eXRlMRes0Tdx5gsvkqEW3vdUO1mmsm_Us-cQFGuKwwEy_J3efrxYnQ1GvCNDir0vdaJuUP1pP-a41VTQsBU7utp8ByqB0cxYzAd");
					ja.put(token);
					jPayload.put("registration_ids", ja);
					break;
				case "topic":
					jPayload.put("to", "/topics/news");
					break;*//*
             *//*	case "condition":
					jPayload.put("condition", "'sport' in topics || 'news' in topics");
					break;*//*
				default:*/
            jPayload.put("to", W_token);
            //	}

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
            jPayload.put("data", jData);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", AUTH_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                   // mTextView.setText(resp);
                    Toast.makeText(Distributor_View_Oreder_module.this, resp, Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}