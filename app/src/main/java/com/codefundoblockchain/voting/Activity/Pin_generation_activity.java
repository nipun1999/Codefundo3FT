package com.codefundoblockchain.voting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.Utils.StringUtils;
import com.goodiebag.pinview.Pinview;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;

public class Pin_generation_activity extends AppCompatActivity {


    private Pinview pin,rePin;
    private Button registerBtn;
    String Pin,repin;
    private SessionManager sessionManager;
    private DatabaseReference mdatabase;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_generation_activity);

        pin = findViewById(R.id.verifyPinView);
        rePin = findViewById(R.id.reVerifyPinView);
        registerBtn = findViewById(R.id.pinRegisterBtn);

        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);

        sessionManager = new SessionManager(this);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile");

        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Pin = pinview.getValue();
            }
        });

        rePin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                repin = pinview.getValue();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePin();
            }
        });



    }

    private void generatePin() {
        pd.show();
        if(!StringUtils.isEmpty(Pin) && !StringUtils.isEmpty(repin)){
            if(Pin.equals(repin)){
                pd.dismiss();
                final HashCode hashCode = Hashing.sha256().hashString(Pin,Charset.defaultCharset());
                String hash = hashCode.toString();
                DatabaseReference database = mdatabase.push();
                database.child("email").setValue(sessionManager.getEMAIL());
                database.child("mobileNo").setValue(sessionManager.getMOBILE_NO());
                database.child("aadharNo").setValue(sessionManager.getAADHAR_NO());
                database.child("firstName").setValue(sessionManager.getFIRST_NAME());
                database.child("lastName").setValue(sessionManager.getLAST_NAME());
                database.child("newUser").setValue("true");
                database.child("pin").setValue(hash);
                sessionManager.setUSER_ID(database.getKey());

                Intent intent = new Intent(Pin_generation_activity.this,FaceUploadActivity.class);
                startActivity(intent);
            }else{
                pd.dismiss();
                Toast.makeText(this, "Both Pin doesnot match", Toast.LENGTH_SHORT).show();
            }
        }else{
            pd.dismiss();
            Toast.makeText(this, "Please enter all the values", Toast.LENGTH_SHORT).show();
        }
    }
}
