package com.codefundoblockchain.voting.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codefundoblockchain.voting.R;

public class Registration_Successful_activity extends AppCompatActivity {

    private Button proceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__successful_activity);

        proceedBtn = findViewById(R.id.proceedBtn);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration_Successful_activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
