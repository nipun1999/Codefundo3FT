package com.codefundoblockchain.voting.Activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.codefundoblockchain.voting.R;

public class MainActivity extends AppCompatActivity {


    WebView web;
    Button auth;
    SharedPreferences pref;
    TextView Access;
    private Dialog auth_dialog;
    private WebView loginWebView;
    private String currentUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
        Access =(TextView)findViewById(R.id.Access);
        auth = (Button)findViewById(R.id.auth);
        loginWebView = findViewById(R.id.loginWebView);

        final android.webkit.CookieManager cookieManager = CookieManager.getInstance();

        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWebView.getSettings().setJavaScriptEnabled(true);
                loginWebView.loadUrl("https://login.microsoftonline.com/agarwalnipun12gmail.onmicrosoft.com/oauth2/authorize?resource=35c36543-8455-46a5-b470-25ff1d027cc7&client_id=35c36543-8455-46a5-b470-25ff1d027cc7&response_type=token&redirect_uri=https://electblockchain-2vifev.azurewebsites.net");

                loginWebView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
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
                            Log.e("token",token);
                            loginWebView.clearCache(true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                                    // a callback which is executed when the cookies have been removed
                                    @Override
                                    public void onReceiveValue(Boolean aBoolean) {
                                        Log.d("url", "Cookie removed: " + aBoolean);
                                    }
                                });
                            }
                            else cookieManager.removeAllCookie();
                        }else{
                            loginWebView.loadUrl(url);
                        }

                        return true;
                    }
                });





//                auth_dialog = new Dialog(MainActivity.this);
//                auth_dialog.setContentView(R.layout.auth_dialog);
//                web = (WebView)auth_dialog.findViewById(R.id.webv);
//                web.getSettings().setJavaScriptEnabled(true);
//                web.loadUrl("https://login.microsoftonline.com/agarwalnipun12gmail.onmicrosoft.com/oauth2/authorize?resource=35c36543-8455-46a5-b470-25ff1d027cc7&client_id=35c36543-8455-46a5-b470-25ff1d027cc7&response_type=token&redirect_uri=https://electblockchain-2vifev.azurewebsites.net");
//
//                web.setWebViewClient(new WebViewClient(){
//                    boolean authComplete = false;
//                    Intent resultIntent = new Intent();
//
//                    @Override
//                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                        super.onPageStarted(view, url, favicon);
//                    }
//                    String authCode;
//
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        super.onPageFinished(view, url);
//
//                        Log.e("url",url);
//
//                        if(url.contains("#access_token")&&authComplete!=true){
//                            Uri uri = Uri.parse(url);
//                            authCode = uri.getQueryParameter("access_token");
//                            Log.e("uri",uri.toString());
//                            Log.i("code",authCode);
//                            authComplete = true;
//                            resultIntent.putExtra("code", authCode);
//                            MainActivity.this.setResult(Activity.RESULT_OK, resultIntent);
//                            setResult(Activity.RESULT_CANCELED, resultIntent);
//                            Toast.makeText(getApplicationContext(), authCode, Toast.LENGTH_SHORT).show();
//                            auth_dialog.dismiss();
//                        }else if(url.contains("error=access_denied")){
//                            Log.i("", "ACCESS_DENIED_HERE");
//                            resultIntent.putExtra("code", authCode);
//                            authComplete = true;
//                            setResult(Activity.RESULT_CANCELED, resultIntent);
//                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
//
//                            auth_dialog.dismiss();
//                        }
//                    }
//                });
//
//                auth_dialog.show();
//                auth_dialog.setTitle("Authorize Codefundo");
//                auth_dialog.setCancelable(false);
            }
        });

    }
}
