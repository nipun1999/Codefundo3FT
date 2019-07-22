package com.codefundoblockchain.voting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.Utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText first,last,email,mobile,aadhar;
    private Button register;
    private DatabaseReference mdatabase;
    private int flag=0;
    private SessionManager sessionManager;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first = findViewById(R.id.firstNameRegisterEdtTxt);
        last = findViewById(R.id.lastNameRegisterEdtTxt);
        email = findViewById(R.id.emailRegisterEdtTxt);
        mobile = findViewById(R.id.mobileNumberEdtTxt);
        aadhar = findViewById(R.id.aadharNumberEdtTxt);
        sessionManager = new SessionManager(this);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile");

        register = findViewById(R.id.registerBtn);

        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
;            }
        });

    }

    private void registerUser() {
        flag=0;
        pd.show();
        String firstName = first.getText().toString();
        String lastName = last.getText().toString();
        String mobileNo = mobile.getText().toString();
        String emailAddress = email.getText().toString();
        String aadharNo = aadhar.getText().toString();

        if(StringUtils.isEmpty(firstName)||StringUtils.isEmpty(lastName)||StringUtils.isEmpty(mobileNo)||StringUtils.isEmpty(emailAddress)||StringUtils.isEmpty(aadharNo)){
            pd.dismiss();
            Toast.makeText(this, "Please Enter All the fields", Toast.LENGTH_SHORT).show();
        }else{
            mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.e("register","inside this");

                    for (DataSnapshot profile : dataSnapshot.getChildren()){

                        Log.e("register",profile.child("email").getValue().toString());

                        if(profile.child("email").getValue().toString().equals(emailAddress)){
                            Log.e("register","inside final");
                            flag = 1;
                            Log.e("register",Integer.toString(flag));
                        }

                    }


                    if(Integer.toString(flag).equals("1")){
                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this, "Email Address Already Exists", Toast.LENGTH_SHORT).show();
                    }else{
                        pd.dismiss();
                        sessionManager.setAADHAR_NO(aadharNo);
                        sessionManager.setEMAIL(emailAddress);
                        sessionManager.setFIRST_NAME(firstName);
                        sessionManager.setLAST_NAME(lastName);
                        sessionManager.setMOBILE_NO(mobileNo);

                        Toast.makeText(RegisterActivity.this, "Please enter OTP to verify your phone Number", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(RegisterActivity.this,mobileVerificationActivity.class);
                        intent.putExtra("activity","register");
                        startActivity(intent);
//                DatabaseReference database = mdatabase.push();
//                database.child("firstName").setValue(firstName);
//                database.child("lastName").setValue(lastName);
//                database.child("email").setValue(emailAddress);
//                database.child("aadharNo").setValue(aadharNo);
//                database.child("mobileNo").setValue(mobileNo);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
}
