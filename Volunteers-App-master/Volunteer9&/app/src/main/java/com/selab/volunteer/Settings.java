package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class Settings extends AppCompatActivity {

    LinearLayout account;
    LinearLayout terms,support1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        account=(LinearLayout)findViewById(R.id.account6);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,accountsettings.class));
            }
        });

        terms = (LinearLayout)findViewById(R.id.terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,Terms_conditions.class));
            }
        });

        support1 = (LinearLayout)findViewById(R.id.support5);
        support1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,profileSupport.class));
            }
        });
    }

}
