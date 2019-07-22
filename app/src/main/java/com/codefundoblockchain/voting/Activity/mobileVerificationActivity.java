package com.codefundoblockchain.voting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.APIModels.CreateOTPModel;
import com.codefundoblockchain.voting.APIModels.VerifyOTPModel;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.Utils.StringUtils;
import com.codefundoblockchain.voting.retrofit.TwillioApiClient;
import com.goodiebag.pinview.Pinview;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mobileVerificationActivity extends AppCompatActivity {

    public Pinview otpPinView;
    private SessionManager sessionManager;
    private JSONObject jsonObject;
    private Button otpVerifyBtn;
    private String code;
    private ProgressDialog pd;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        otpPinView = findViewById(R.id.otpPinView);
        sessionManager = new SessionManager(this);
        otpVerifyBtn = findViewById(R.id.otpVerifyBtn);
        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);

        Intent intent = getIntent();
        status = intent.getStringExtra("activity");

        createOTP();



        otpPinView.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                code = pinview.getValue();
            }
        });
        otpVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });



    }

    private void verifyOtp() {
        pd.show();
        if(!StringUtils.isEmpty(code)&&code.length()==6){
            Call<VerifyOTPModel> call = TwillioApiClient.getClient().verifyOTP("+91"+sessionManager.getMOBILE_NO(),code);
            call.enqueue(new Callback<VerifyOTPModel>() {
                @Override
                public void onResponse(Call<VerifyOTPModel> call, Response<VerifyOTPModel> response) {
                    pd.dismiss();
                    if(response.code()==200){
                        if(response.body().getValid()){
                            Toast.makeText(mobileVerificationActivity.this, "Otp Verified Successfully", Toast.LENGTH_SHORT).show();
                            if(status.equals("vote")){
                                Intent intent = new Intent(mobileVerificationActivity.this,Pin_Verification_Activity.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(mobileVerificationActivity.this,Pin_generation_activity.class);
                                startActivity(intent);
                            }

                        }else{
                            Toast.makeText(mobileVerificationActivity.this, "OTP Entered is not correct", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mobileVerificationActivity.this, "Failed to verify OTP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VerifyOTPModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(mobileVerificationActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            pd.dismiss();
            Toast.makeText(this, "Please enter the correct OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void createOTP() {

        pd.show();
        Call<CreateOTPModel> call = TwillioApiClient.getClient().getOTP("+91"+sessionManager.getMOBILE_NO(),"sms");
        call.enqueue(new Callback<CreateOTPModel>() {
            @Override
            public void onResponse(Call<CreateOTPModel> call, Response<CreateOTPModel> response) {
                pd.dismiss();
                if(response.code()==201){
                    Toast.makeText(mobileVerificationActivity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                }else if(response.code()==400){

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(mobileVerificationActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(mobileVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

//
                }else{

                    Log.e("register",Integer.toString(response.code()));
                    Log.e("register",response.message().toString());
                    Log.e("register",response.errorBody().toString());
                    Toast.makeText(mobileVerificationActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CreateOTPModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(mobileVerificationActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
