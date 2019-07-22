package com.codefundoblockchain.voting.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codefundoblockchain.voting.R;

public class Vote_Successful_Activity extends AppCompatActivity {

    private Button goHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote__successful_);

        goHomeBtn = findViewById(R.id.homeBtn);

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vote_Successful_Activity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
