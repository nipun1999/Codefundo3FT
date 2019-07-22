package com.codefundoblockchain.voting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.GetUserInfo;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MicrosoftLoginActivity extends AppCompatActivity {

    public WebView loginWebView;
    private String currentUrl;
    private SessionManager sessionManager;
    private ProgressDialog pd;
    private DatabaseReference mdatabase;
    private int flag = 0;
    private String newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microsoft_login);

        loginWebView = findViewById(R.id.loginWebView);
        sessionManager = new SessionManager(this);

        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);


        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile");


        final android.webkit.CookieManager cookieManager = CookieManager.getInstance();

        loginWebView.getSettings().setJavaScriptEnabled(true);
        loginWebView.loadUrl("https://login.microsoftonline.com/agarwalnipun12gmail.onmicrosoft.com/oauth2/authorize?resource=35c36543-8455-46a5-b470-25ff1d027cc7&client_id=35c36543-8455-46a5-b470-25ff1d027cc7&response_type=token&redirect_uri=https://electblockchain-2vifev.azurewebsites.net");

        pd.show();

        loginWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
                Log.e("finishedUrl", url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                currentUrl=url;
                Log.e("currentUrl",currentUrl);
                if(currentUrl.contains("access_token")){
                    Integer start = url.lastIndexOf("access_token");
                    Integer end = url.lastIndexOf("&token_type");
                    String token = currentUrl.substring(start+13,end);
                    sessionManager.setBEARER_TOKEN(token);
                    Log.e("token",token);


                    pd.show();
                    checkEmail();

//                    loginWebView.clearCache(true);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
//                            // a callback which is executed when the cookies have been removed
//                            @Override
//                            public void onReceiveValue(Boolean aBoolean) {
//                                Log.d("url", "Cookie removed: " + aBoolean);
//                            }
//                        });
//                    }
//                    else cookieManager.removeAllCookie();

                }else{
                    loginWebView.loadUrl(url);
                }

                return true;
            }
        });

    }

    private void checkEmail() {
        Call<GetUserInfo> call = AzureApiClient.getClient().getUserInfo();
        call.enqueue(new Callback<GetUserInfo>() {
            @Override
            public void onResponse(Call<GetUserInfo> call, Response<GetUserInfo> response) {


                if(response.code()==200){
                    String email = response.body().getCurrentUser().getEmailAddress();

                    mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            pd.dismiss();
                            for (DataSnapshot profile : dataSnapshot.getChildren()){

                                Log.e("register",profile.child("email").getValue().toString());

                                if(profile.child("email").getValue().toString().equals(email)){

                                    sessionManager.setMOBILE_NO(profile.child("mobileNo").getValue().toString());
                                    sessionManager.setUSER_ID(profile.getKey());
                                    sessionManager.setFIRST_NAME(profile.child("firstName").getValue().toString());
                                    sessionManager.setLAST_NAME(profile.child("lastName").getValue().toString());
                                    newUser = profile.child("newUser").getValue().toString();
                                    Log.e("register","inside final");
                                    flag = 1;
                                    Log.e("register",Integer.toString(flag));
                                }

                            }

                            if(flag==1){
                                Log.e("login",newUser);
                                if(newUser.equals("true")){
                                    mdatabase.child(sessionManager.getUSER_ID()).child("newUser").setValue("false");
                                    Intent intent = new Intent(MicrosoftLoginActivity.this,Walkthrough.class);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(MicrosoftLoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                }

                            }else{
                                Toast.makeText(MicrosoftLoginActivity.this, "User is not registered", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            pd.dismiss();
                        }
                    });

                }else{
                    pd.dismiss();
                    Toast.makeText(MicrosoftLoginActivity.this, "Failed to check registration data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserInfo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(MicrosoftLoginActivity.this, "Some error occured, Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
