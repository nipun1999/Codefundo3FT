package com.codefundoblockchain.voting.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codefundoblockchain.voting.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {


                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);

                // close this activity

                finish();

            }

        }, 3*1000);

    }
}