package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class HostFinal extends AppCompatActivity {
    Button gotob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        gotob = findViewById(R.id.goto_myevents);

        gotob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostFinal.this, MyEvents.class);
                startActivity(intent);


            }
        });
    }
}
