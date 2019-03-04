package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView textView=(TextView)findViewById(R.id.profilelogout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Login.class);
                startActivity(intent);
            }
        });

        ImageView imageView=(ImageView) findViewById(R.id.profilelogout1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Login.class);
                startActivity(intent);
            }
        });

        LinearLayout settings=(LinearLayout)findViewById(R.id.profilesettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Settings.class);
                startActivity(intent);
            }
        });
        LinearLayout wallet=(LinearLayout)findViewById(R.id.profilewallet);
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,MainActivity.class);
                startActivity(intent);


            }
        });
        LinearLayout events=(LinearLayout)findViewById(R.id.profilemyevents);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,MyEvents.class);
                startActivity(intent);
            }
        });
    }
}
