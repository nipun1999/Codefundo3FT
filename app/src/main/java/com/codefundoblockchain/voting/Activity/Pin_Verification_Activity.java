package com.codefundoblockchain.voting.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.VoteBodyModel;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;
import com.goodiebag.pinview.Pinview;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pin_Verification_Activity extends AppCompatActivity {

    public Pinview verifyPinView;
    public Button verifyBtn;
    public String pin = "";
    private SessionManager sessionManager;
    private DatabaseReference mdatabase;
    private List<Object> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin__verification_);

        verifyPinView = findViewById(R.id.verifyPinView);
        verifyBtn = findViewById(R.id.verifyBtn);
        sessionManager = new SessionManager(getApplicationContext());
        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile").child(sessionManager.getUSER_ID());

        verifyPinView.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                pin = verifyPinView.getValue();
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPin();

            }
        });



    }

    private void checkPin() {
        Log.e("vote",sessionManager.getCONTRACT_ID());
        if(pin.length()!=6){
            Toast.makeText(this, "Please enter all the digits", Toast.LENGTH_SHORT).show();
        }else{
            mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final HashCode hashCode = Hashing.sha256().hashString(pin,Charset.defaultCharset());
                    String hash = hashCode.toString();
                    if(hash.equals(dataSnapshot.child("pin").getValue().toString())){
//                        Map<String,String> map = new HashMap<>();
//                        map.put("workflowFunctionID","25");
//                        map.put("workflowActionParameters","");
                        VoteBodyModel body = new VoteBodyModel();
                        body.setWorkflowFunctionID(2);
                        body.setWorkflowActionParameters(list);

                        Call call = AzureApiClient.getClient().voteCandidate(sessionManager.getCONTRACT_ID(),body);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if(response.code()==200){
                                    mdatabase.child("electionsVoted").push().child("id").setValue(sessionManager.getAPPLICATION_ID());
                                    Intent intent = new Intent(Pin_Verification_Activity.this,Vote_Successful_Activity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Pin_Verification_Activity.this, "Cannot vote rn, Please try again later", Toast.LENGTH_SHORT).show();
                                    Log.e("vote",Integer.toString(response.code()));
                                    Log.e("vote",response.message().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Log.e("vote",t.toString());
                                Toast.makeText(Pin_Verification_Activity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(Pin_Verification_Activity.this, "Please enter the correct pin", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
